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
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.BoardTemplate;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.PlayerTemplate;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.SpaceTemplate;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.restfullapi.PlayerService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
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
    private String SaveJson;
    private  Player player;

    private PlayerService playerService;


    public static Board loadBoard(String boardname) {

        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("src/main/resources/boards/SavedGame1.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray companyList = (JSONArray) jsonObject.get("players");

            Iterator<JSONObject> iterator = companyList.iterator();
            int player = 0;
            while (iterator.hasNext()) {

                System.out.println(iterator.next());
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (boardname == null) {
            boardname = DEFAULTBOARD;
        }

        ClassLoader classLoader = LoadBoard.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("src/main/resources/" + BOARDSFOLDER + "/" + boardname + "/SavedGame1/" + JSON_EXT);
        System.out.println(inputStream);
        if (inputStream == null) {
            // TODO these constants should be defined somewhere
            return new Board(8,8);
        }

        // In simple cases, we can create a Gson object with new Gson():
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();

        Board result;
        FileReader fileReader = null;
        JsonReader reader = null;
        try {
            fileReader = new FileReader("src/main/resources/boards/SavedGame1.json");
            reader = gson.newJsonReader(new InputStreamReader(inputStream));
            BoardTemplate template = gson.fromJson(reader, BoardTemplate.class);

            result = new Board(template.width, template.height);
            for (SpaceTemplate spaceTemplate: template.spaces) {
                Space space = result.getSpace(spaceTemplate.x, spaceTemplate.y);
                if (space != null) {
                    space.getActions().addAll(spaceTemplate.actions);
                    space.getWalls().addAll(spaceTemplate.walls);
                }
            }
            reader.close();
            return result;
        } catch (IOException e1) {
            if (reader != null) {
                try {
                    reader.close();
                    inputStream = null;
                } catch (IOException e2) {}
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {}
            }
        }
        return null;
    }

    public static final String POST_API_URL = "http://localhost:8080/upload";
    public static final String GET_API_URL = "http://localhost:8080/files";

    public static void httpPOST() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GET_API_URL))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
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
            fileWriter = new FileWriter("src/main/resources/boards/SavedGame1.json");
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

        httpPOST();






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

