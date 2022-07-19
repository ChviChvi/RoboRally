/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.BoardTemplate;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.PlayerTemplate;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.SpaceTemplate;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.restfullapi.PlayerService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class LoadBoard {

    private  static final String BOARDSFOLDER = "boards";
    private  static final String DEFAULTBOARD = "defaultboard1";
    private  static final String JSON_EXT = "json";
    public static GameController gameController;
    public static RoboRally roboRally;


    public static Board loadBoard(String boardname) {

        Board board = new Board(8,8);


        gameController = new GameController(board);





        // XXX: V2
        // board.setCurrentPlayer(board.getPlayer(0));


        JSONParser parser = new JSONParser();
        try{

            Object obj = parser.parse(new FileReader("src/main/resources/boards/SavedGame1.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray PlayerList = (JSONArray) jsonObject.get("players");

            //i want to extract each player from the file and put them on the board

            for (int i = 0, size = PlayerList.size(); i < size; i++) {
                JSONObject objectInArray = (JSONObject) PlayerList.get(i);
                String player_name = objectInArray.get("name").toString();
                String player_color = objectInArray.get("color").toString();
                String player_x = objectInArray.get("x").toString();
                String player_y = objectInArray.get("y").toString();
                String player_heading = objectInArray.get("heading").toString();

//                System.out.println(player_name);
//                System.out.println(player_color);
//                System.out.println(player_x);
//                System.out.println(player_y);
                //System.out.println(player_heading);

                int x_parced = Integer.parseInt(player_x);
                int y_parced = Integer.parseInt(player_y);

                Player player = new Player(board, player_color, player_name);
                board.addPlayer(player);
                player.setSpace(board.getSpace(x_parced, y_parced));
                if(player_heading.equals("NORTH")){player.setHeading(Heading.NORTH);}
                if(player_heading.equals("WEST")){player.setHeading(Heading.WEST);}
                if(player_heading.equals("EAST")){player.setHeading(Heading.EAST);}
                if(player_heading.equals("SOUTH")){player.setHeading(Heading.SOUTH);}

            }

//                "name": "Player 1",
//                        "color": "green",
//                        "x": 0,
//                        "y": 0,
//                        "heading": "SOUTH"

                // "...and get thier component and thier value."
//                String[] elementNames = JSONObject.g(objectInArray);
//                System.out.printf("%d ELEMENTS IN CURRENT OBJECT:\n", elementNames.length);
//                for (String elementName : elementNames) {
//                    String value = objectInArray.getString(elementName);
//                    System.out.printf("name=%s, value=%s\n", elementName, value);
//                }
//                System.out.println();
//            }

//            System.out.println(PlayerList);
//            Iterator<JSONObject> iterator = PlayerList.iterator();
//            int player = 0;
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//        }
            return board;
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameController.startProgrammingPhase();

        roboRally.createBoardView(gameController);


///////////////////////////////////////////////////////
//        if (boardname == null) {
//            boardname = DEFAULTBOARD;
//        }
//
//        ClassLoader classLoader = LoadBoard.class.getClassLoader();
//        InputStream inputStream = classLoader.getResourceAsStream("src/main/resources/" + BOARDSFOLDER + "/" + boardname + "/SavedGame1/" + JSON_EXT);
//        System.out.println(inputStream);
//
////        if (inputStream == null) {
////            // TODO these constants should be defined somewhere
////            return new Board(8,8);
////        }
//        System.out.println("you got here3");
//        // In simple cases, we can create a Gson object with new Gson():
//        GsonBuilder simpleBuilder = new GsonBuilder().
//                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
//        Gson gson = simpleBuilder.create();
//
//        Board result;
//        FileReader fileReader = null;
//        JsonReader reader = null;
//
//        try {
//
//            fileReader = new FileReader("src/main/resources/boards/SavedGame1.json");
//            System.out.println(fileReader);
//            reader = gson.newJsonReader(new FileReader(String.valueOf(fileReader)));
//
//            BoardTemplate template = gson.fromJson(reader, BoardTemplate.class);
//
//            result = new Board(template.width, template.height);
//            for (SpaceTemplate spaceTemplate: template.spaces) {
//                Space space = result.getSpace(spaceTemplate.x, spaceTemplate.y);
//                if (space != null) {
//                    space.getActions().addAll(spaceTemplate.actions);
//                    space.getWalls().addAll(spaceTemplate.walls);
//                }
//            }
//            reader.close();
//            return result;
//        } catch (IOException e1) {
//            if (reader != null) {
//                try {
//                    reader.close();
//                    inputStream = null;
//                } catch (IOException e2) {}
//            }
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e2) {}
//            }
//        }
        return null;
    }

    public static final String POST_API_URL = "http://localhost:8080/upload";
    public static final String GET_API_URL = "http://localhost:8080/files";


    public static void httpGET() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GET_API_URL))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        URL url = new URL(GET_API_URL);

        System.out.println(response.body());
        //trying to download the file here
        downloadFile(url,"SavedGame"+filecounter());
    }

    public static void httpGETnames() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GET_API_URL))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        JSONArray bodyarray = new JSONArray();


        //JSONObject jsonObject = body;
        System.out.println(response.body());

    }

    public static String filecounter(){
        File directory=new File("src/main/resources/boards");
        int filecount = directory.list().length;
        String HEY =Integer.toString(filecount);
        filecount = filecount++;
        System.out.println("there is this many files: "+ filecount);
        return HEY;
    }

    public static void downloadFile(URL url, String outputFileName) throws IOException
    {
        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(outputFileName)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    //public static void saveBoard(Board board, String name) {
    public static void saveBoard(Board board, String name) throws IOException, InterruptedException {
        BoardTemplate template = new BoardTemplate();
        template.width = board.width;
        template.height = board.height;


        for (int i=0; i<board.width; i++) {
            for (int j=0; j<board.height; j++) {
                Space space = board.getSpace(i,j);
                if (!space.getWalls().isEmpty() || !space.getActions().isEmpty()) {
                    SpaceTemplate spaceTemplate = new SpaceTemplate();
                    spaceTemplate.x = space.x;
                    spaceTemplate.y = space.y;
                    spaceTemplate.actions.addAll(space.getActions());
                    spaceTemplate.walls.addAll(space.getWalls());
                    template.spaces.add(spaceTemplate);
                }
            }
        }
//
        for (Player player: board.getPlayers()) {
            template.players.add(new PlayerTemplate(player.getName(), player.getColor(), player.getSpace().x, player.getSpace().y, player.getHeading()));
        }

        ClassLoader classLoader = LoadBoard.class.getClassLoader();
        // TODO: this is not very defensive, and will result in a NullPointerException
        //       when the folder "resources" does not exist! But, it does not need
        //       the file "simpleCards.json" to exist!
        //String filename =
        //        classLoader.getResource(BOARDSFOLDER).getPath() + "/" + name + "." + JSON_EXT;

        // In simple cases, we can create a Gson object with new:
        //
        //   Gson gson = new Gson();
        //
        // But, if you need to configure it, it is better to create it from
        // a builder (here, we want to configure the JSON serialisation with
        // a pretty printer):
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).
                setPrettyPrinting();
        Gson gson = simpleBuilder.create();

        FileWriter fileWriter = null;
        JsonWriter writer = null;
        try {
            fileWriter = new FileWriter("src/main/resources/boards/SavedGame"+filecounter() +".json");
            writer = gson.newJsonWriter(fileWriter);
            gson.toJson(template, template.getClass(), writer);


            writer.close();
        } catch (IOException e1) {
            if (writer != null) {
                try {
                    writer.close();
                    fileWriter = null;
                } catch (IOException e2) {}
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e2) {}
            }
        }

        httpGET();






//
//        ClassLoader classLoader = LoadBoard.class.getClassLoader();
//        // TODO: this is not very defensive, and will result in a NullPointerException
//        //       when the folder "resources" does not exist! But, it does not need
//        //       the file "simpleCards.json" to exist!
//        String filename =
//                classLoader.getResource(BOARDSFOLDER).getPath() + "/" + name + "." + JSON_EXT;
//
//
//        // In simple cases, we can create a Gson object with new:
//        //
//        // Gson gson = new Gson();
//        //
//        // But, if you need to configure it, it is better to create it from
//        // a builder (here, we want to configure the JSON serialisation with
//        // a pretty printer):
//        GsonBuilder simpleBuilder = new GsonBuilder().
//                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).
//                registerTypeAdapter(PlayerTemplate.class, new Adapter<PlayerTemplate>()).
//                setPrettyPrinting();
//        Gson gson = simpleBuilder.create();
//
//
//        try {
//            System.out.println("you got here");
//            String thisJson = gson.toJson(template, template.getClass());
//
//            System.out.println(thisJson);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//
//
//
//        FileWriter fileWriter = null;
//        JsonWriter writer = null;
//        try {
//            File file = new File("C:\\Users\\Christiaan Vink\\IdeaProjects\\ADV.Prog_G06new\\src\\main\\resources\\boards\\thisisatest1.json");
//
//            //file.createNewFile();
//            //Path path = Paths.get("C:\\Users\\Christiaan Vink\\IdeaProjects\\ADV.Prog_G06new\\src\\main\\resources\\boards\\thisisatest1.json");
////            String thisJson = gson.toJson(template, template.getClass());
////            System.out.println(thisJson);
//            //Files.writeString(path,thisJson, StandardCharsets.UTF_8);
//
//            fileWriter = new FileWriter(file);
//            writer = gson.newJsonWriter(fileWriter);
//            String thisJson = gson.toJson(template, template.getClass());
//            System.out.println(thisJson);
//            System.out.println("we got here 1");
//            //writer.close();
//            System.out.println("we got here 2");


//        } catch (IOException e1) {
//            e1.printStackTrace();
//            if (writer != null) {
//                try {
//                    writer.close();
//                    fileWriter = null;
//                } catch (IOException e2) {}
//            }
//            if (fileWriter != null) {
//                try {
//                    fileWriter.close();
//                } catch (IOException e2) {}
//            }
//        }
    }

//


}

