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

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.CardFieldView;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.BoardTemplate;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.PlayerTemplate;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.SpaceTemplate;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static dk.dtu.compute.se.pisd.roborally.model.Command.*;
import static java.util.Objects.isNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class LoadBoard {

    private static final String BOARDSFOLDER = "boards";
    private static final String DEFAULTBOARD = "defaultboard1";
    //private static final String JSON_EXT = "json";
    public static GameController gameController;
    public static RoboRally roboRally;

    private Player player;
    private static CommandCard card;
    private Command command;
    private CommandCardField commandCardField;


    //CommandCardField cardField;
    String cardField;

    @Override
    public String toString() {
        return "LoadBoard{" +
                "cardField=" + cardField +
                '}';
    }
    public static String stringcommandcards(@NotNull Player player) {
        List<String> COMMANDCARDLIST = new ArrayList<String>();

        //System.out.println("Command Cards");
        for (int i = 0; i < 8; i++) {
            if(isNull(player.getCardField(i).getCard())){
                COMMANDCARDLIST.add("EMPTY");
            } else {
                String cardcards = player.getCardField(i).getCard().getName();
                COMMANDCARDLIST.add(cardcards);
            }
        }

        String String_program = String.join(",",COMMANDCARDLIST);

//        for ( String elem : COMMANDCARDLIST ) {
//            System.out.println("PROGRAMCARD : "+elem);
//        }

        return String_program;
    }

    public static String stringcheckpoints(@NotNull Player player){

        ArrayList<Integer> myNumbers = player.getCheckpoints();

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < myNumbers.size(); i++)
        {
            int myNumbersInt = myNumbers.get(i);
            str.append(myNumbersInt + ",");
        }
        return str.toString();
    }

    public static String stringprogramcards(@NotNull Player player) {
        //List<String> COMMANDCARDLIST = new ArrayList<String>();
        List<String> PROGRAMFIELDLIST = new ArrayList<String>();


        //System.out.println("Program Cards");
        for (int i = 0; i < 5; i++) {
            //System.out.println(Player.getProgramField(i).getCard());
            if(isNull(player.getProgramField(i).getCard())){
                PROGRAMFIELDLIST.add("EMPTY");
            } else {
                //String cardField = player.getProgramField(i).getCard().getName();
                String programcards = player.getProgramField(i).getCard().getName();
                PROGRAMFIELDLIST.add(programcards);
            }
        }
        //System.out.println("Command Cards");
//        for (int i = 0; i < 8; i++) {
//            if(isNull(player.getCardField(i).getCard())){
//                COMMANDCARDLIST.add("EMPTY");
//            } else {
//                String cardcards = player.getCardField(i).getCard().getName();
//                COMMANDCARDLIST.add(cardcards);
//            }
//        }

//        String String_program = String.join(",",COMMANDCARDLIST);
        String String_card = String.join(",",PROGRAMFIELDLIST);

//        for ( String elem : COMMANDCARDLIST ) {
//            System.out.println("PROGRAMCARD : "+elem);
//        }
//        for ( String elem : PROGRAMFIELDLIST ) {
//            System.out.println("COMMANDCARD : "+elem);
//        }
        return String_card;
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();


    public static List<Integer> getAllSaves(){
        //httpDEL(1030);
        List<Integer> result = new ArrayList<Integer>();

        /** this value might need to be changed on other pc.*/
        for(int i=999;i<1100; i++){
            String test = httpGETbyID(i);
            //System.out.println(test);
            if( !test.contains("null") && test.length() != 4){
                result.add(i);
            }
        }
        //System.out.println(result);
        return result;
    }

    @SneakyThrows
    public static Board loadBoard(Optional<Integer> boardname) {

        Board board = new Board(8,8);
        gameController = new GameController(board);

        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        BoardTemplate template = new BoardTemplate();
        //httpDEL(1034);

        int boardsave = boardname.get();
        //System.out.println(boardsave + " this was the value you chosE?");

        //String allSaves = httpGET();
        String allSaves = httpGETbyID(boardsave);
        //System.out.println(allSaves);

        allSaves = Decoding(allSaves);

        System.out.println(allSaves);



        System.out.println("--------- THE GAME "+ boardsave +" IS LOADED---------");


        try{
            JSONObject jsonObject = new JSONObject(allSaves);
            JSONArray PlayerList = jsonObject.getJSONObject("gamestate").getJSONArray("players");
            JSONArray CardList = jsonObject.getJSONObject("gamestate").getJSONArray("cards");


            for (int i = 0, size = PlayerList.length(); i < size; i++) {
                JSONObject objectInArray = (JSONObject) PlayerList.get(i);
                String player_name = objectInArray.get("name").toString();
                String player_color = objectInArray.get("color").toString();
                String player_x = objectInArray.get("x").toString();
                String player_y = objectInArray.get("y").toString();
                String player_heading = objectInArray.get("heading").toString();

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




            for (int i = 0, size = CardList.length(); i < size; i++) {
                JSONObject objectInArray = (JSONObject) CardList.get(i);
                String player_names = objectInArray.get("name").toString();
                String command_names = objectInArray.get("commandcards").toString();
                if (command_names.contains("U:T")) {
                    command_names = command_names.replace("U:T", "U-T");
                }
                String program_names = objectInArray.get("programcards").toString();
                if (program_names.contains("U:T")) {
                    program_names = program_names.replace("U:T", "U-T");
                }

                //if(!isNull(objectInArray.get("checkpoints").toString())){


                System.out.println("----------------");
                //System.out.println(Checkpoint_points);
                System.out.println("----------------");


                // put these two strings in two different arrays, and then through a forloop set cards :) DONE :D

                String strC[] = command_names.split(",");
                String strP[] = program_names.split(",");

                //String checkP[] = Checkpoint_points.split(",");

                List<String> listC = new ArrayList<String>();
                List<String> listP = new ArrayList<String>();
                //List<String> ListCP = new ArrayList<>();

                listC = Arrays.asList(strC);
                listP = Arrays.asList(strP);
                //ListCP = Arrays.asList(checkP);

                System.out.println("LISTC");
                for(String c: listC){
                    System.out.println(c);
                }
                System.out.println("LISTP");
                for(String c: listP){
                    System.out.println(c);
                }

                String Checkpoint_points = objectInArray.get("checkpoints").toString();
                System.out.println("///////////////////////////////////////////////////");
                System.out.println(Checkpoint_points);
                String checkP[] = Checkpoint_points.split(",");
                List<String> ListCP = new ArrayList<>();
                ListCP = Arrays.asList(checkP);
                System.out.println("LISTCP");
                for(String c: ListCP) {
                    System.out.println(c);
                }

                //board.setPhase(Phase.PROGRAMMING);
                //board.setCurrentPlayer(board.getPlayer(0));
                //board.setStep(0);

                Player player = board.getPlayer(i);
                board.setCurrentPlayer(player);
                if (player != null) {
                    for (int j = 0; j < Player.NO_CARDS; j++) {
                        //System.out.println("this happened(111111111)");
                        CommandCardField field = player.getCardField(j);
                        String cardfromlist = listC.get(j);
                        if(isNull(checkCard(cardfromlist))){
                            field.setCard(null);
                        } else{
                            field.setCard(new CommandCard(checkCard(cardfromlist)));
                        }
                        field.setVisible(true);
                        //System.out.println(player.getCardField(1).getCard().getName());
                        //if(isNull(player.getCardField(j).getCard())){
                        //    System.out.println("this is working!!!");}

                    }
                    for (int j = 0; j < Player.NO_REGISTERS; j++) {
                        CommandCardField field = player.getProgramField(j);
                        String cardfromlist = listP.get(j);
                        if(isNull(checkCard(cardfromlist))){
                            field.setCard(null);
                        } else {
                            field.setCard(new CommandCard(checkCard(cardfromlist)));
                        }
                        field.setVisible(true);
                        //if(isNull(player.getCardField(1))){
                        //    System.out.println("this is working!!!");
                        //}
                    }

                    //if(!isNull(objectInArray.get("checkpoints").toString())) {


                        //if (!isNull(ListCP.size())) {
                            for (int k = 0; k < ListCP.size(); k++) {
                                if (!isNull(ListCP.get(k)) || !ListCP.get(k).equals("")) {
                                    if(Integer.parseInt(ListCP.get(k)) == 55)
                                    {} else {
                                        player.setCheckpoints(player.getCheckpoints(), Integer.parseInt(ListCP.get(k)));
                                    }
                                }
                            }
                        //}
                    //}
                }



                ///////for(int j = 0; j < listC.size(); j++) {
                ///////    player.getCardField(j).setCard(null);
                ///////    player.getCardField(j).setVisible(true);
                    //CommandCardField field1 = new CommandCardField(player);
                    //field1.setCard(null);




                    //CommandCardField fields = new CommandCardField(player);
                    //CommandCardField field = player.getCardField(j);
                    //veltje.
                    //fields.setCard(card(FORWARD));

                    //field.setCard(null);
                    //field.setVisible(true);
//                    if (listC.get(j).contains("EMPTY")) {
//                        field.setCard(null);
//                        field.setVisible(true);
//                    } else {
//                        field.setCard(new CommandCard(Command.FORWARD));
//                    }
                ///////}
                //Player player = board.getPlayer(i);
                ///////for(int j = 0; j < listP.size(); j++) {
                ///////    player.getProgramField(j).setCard(null);
                ///////    player.getProgramField(j).setVisible(true);
                    //CommandCardField field = new CommandCardField(player);
                    //CommandCardField field = player.getProgramField(j);
                    //veltje.
                    //field.setCard(new CommandCard(FORWARD));
                    //field.setVisible(true);
//                    if (listC.get(j).contains("EMPTY")) {
//                        field.setCard(null);
//                        field.setVisible(true);
//                    } else {
//                        field.setCard(new CommandCard(Command.FORWARD));
//                    }
                ///////}


                System.out.println(toString(player));
                System.out.println(command_names);
                System.out.println(program_names);
                //Player player = board.getPlayer(i);


                //card_names = card_names.replace()

//                for (int j = 0; j < Player.NO_REGISTERS; j++) {
//                    CommandCardField field = player.getProgramField(j);
//                    field.setCard(null);
//                    field.setVisible(true);
//                }
//                for (int j = 0; j < Player.NO_CARDS; j++) {
//                    CommandCardField field = player.getCardField(j);
//                    field.setCard(generateRandomCommandCard());
//                    field.setVisible(true);
//                }
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




        //gameController.startProgrammingPhase();

        //roboRally.createBoardView(gameController);




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

    private static String toString(Player player) {
        return player.getName();
    }

    public static final String POST_API_URL = "http://localhost:8080/upload";
    public static final String GET_API_URL = "http://localhost:8080/api/v1/players/list";


    public static void httpDEL(int id) {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .DELETE()
                    .uri(URI.create("http://localhost:8080/api/v1/players/" + id))
                    //.setHeader("User-Agent", "Product Client")
                    .header("Content-Type", "application/json")
                    .build();
            CompletableFuture<HttpResponse<String>> response =
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            String result = response.thenApply((r)->r.body()).get(5, TimeUnit.SECONDS);
        } catch (Exception e) {

        }
    }
    @SneakyThrows
    public static String httpGETbyID(int id) {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/api/v1/players/" + id))
                    //.setHeader("User-Agent", "Product Client")
                    .header("Content-Type", "application/json")
                    .build();
            CompletableFuture<HttpResponse<String>> response =
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            String result = response.thenApply((r)->r.body()).get(5, TimeUnit.SECONDS);

            //System.out.println("did we get here?");
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @SneakyThrows
    public static String httpGET() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GET_API_URL))
                .header("Content-Type", "application/json")
                .build();
//        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
//
//        URL url = new URL(GET_API_URL);

        CompletableFuture<HttpResponse<String>> response =
                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply((r)->r.body()).get(5, TimeUnit.SECONDS);

        //System.out.println("----------------WE GOT HERE (2) --------------");

        return result;
        //trying to download the file here
        //downloadFile(url,"src/main/resources/boards/SavedGame"+filecounter()+".json");
    }

    //TODO >> this should get the names from the database to display the choices you have
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
        //System.out.println(response.body());

    }

    public static String filecounter(){
        File directory=new File("src/main/resources/boards");
        int filecount = 0;
        if (directory.list().length > 0) {
            filecount = directory.list().length;
        }
        String HEY =Integer.toString(filecount);
        filecount = filecount++;
        //System.out.println("there is this many files: "+ filecount);
        return HEY;
    }

    public static void downloadFile(URL url, String outputFileName) throws IOException {
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

        List<String> playerscards = new ArrayList<String>();
        List<Integer> playerscheckpoints = new ArrayList<>();

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            System.out.println("Cards for player "+ i);
            Player player = board.getPlayer(i);
            if (player != null) {
                String command_cards = stringcommandcards(player);

                player.setCheckpoints(player.getCheckpoints(),55);
                String checkpoints_string = stringcheckpoints(player);
                String program_cards = stringprogramcards(player);
                System.out.println(command_cards);
                System.out.println(program_cards);
                System.out.println(checkpoints_string);

                String jsoncards = "{\"name\":\"Player "+i+"\"," +
                        "\"commandcards\":\""+command_cards+"\"," +
                        "\"programcards\":\""+program_cards+"\"," +
                        "\"checkpoints\":\""+checkpoints_string+"\""+
                        "}";
                playerscards.add(jsoncards);
//
//                for (int j = 0; j < Player.NO_REGISTERS; j++) {
//
//                }
//                for (int j = 0; j < Player.NO_CARDS; j++) {
//                    CommandCardField field = player.getCardField(j);
//                    field.setCard(generateRandomCommandCard());
//                    field.setVisible(true);
//                }
            }
        }

        String playercardsjson = String.join(",",playerscards);
        System.out.println("check of dit goed is" + playercardsjson);
        //hell(gameController.board.getPlayer(i));

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
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
                //setPrettyPrinting();
        Gson gson = simpleBuilder.create();

        FileWriter fileWriter = null;
        JsonWriter writer = null;

        URLConnection urlconnection = null;

        String filepath = "src/main/resources/boards/Testupload" + filecounter() + ".json";
        fileWriter = new FileWriter(filepath);
        writer = gson.newJsonWriter(fileWriter);
        gson.toJson(template, template.getClass(), writer);
        writer.close();

        String json_save = gson.toJson(template, template.getClass());
        //String save = gson.toJson(template, template.getClass());
        System.out.println(json_save);


        if (json_save.contains("\"}]}")) {
            json_save = json_save.replace("\"}]}", "\"}],\"cards\":["+ playercardsjson +"]}");

        }
        System.out.println(json_save);
        json_save = Encoding(json_save);
        //System.out.println(json_save);
        //System.out.println("----------- WE GOT HERE (1) ----------------");


//////////////////////////////////////////////////////////////////////////////////////////
        try{
            String testest = "{\"gamestate\":\""+json_save+"\"}";
            //String testest = "{\"gamestate\":\""+json_save+","+ "\"}";
            //String productJSON = new Gson().toJson(testest);
            System.out.println(testest);
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(testest))
                    .uri(URI.create("http://localhost:8080/api/v1/players"))
                    //.setHeader("User-Agent", "Product Client")
                    .header("Content-Type", "application/json")
                    //.header("Content-Type", "application/json; charset=UTF-8")
                    .build();

            CompletableFuture<HttpResponse<String>> response =
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//                String result = response.thenApply((r)->r.body()).get(5, TimeUnit.SECONDS);

//                if(result.equals("added")){
//                    System.out.println("file got saved");
//                    System.out.println(result);
//                } else {
//                    System.out.println("file did NOT get saved");
//                    System.out.println(result);
//                }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("--------- THE GAME IS SAVED ---------");
        /////////////////////////////
//            try {
//                URL url = new URL("http://localhost:8080/players");
//                String postData = "gamestate"+":"+json_save;
//                //String postData = "foo1=bar1&foo2=bar2";
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setDoOutput(true);
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestProperty("Content-Length", Integer.toString(postData.length()));
//                conn.setUseCaches(false);
//
//                try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
//                    dos.writeBytes(postData);
//                }
//
//                try (BufferedReader br = new BufferedReader(new InputStreamReader(
//                        conn.getInputStream())))
//                {
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

///////////////////////////////////////////////////////////////////////////////////
//            String query_url = "http://localhost:8080/api/v1/players";
//            String json = gson.toJson(template, template.getClass());
//            try {
//                URL url = new URL(query_url);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setConnectTimeout(5000);
//                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                conn.setRequestMethod("POST");
//                OutputStream os = conn.getOutputStream();
//                os.write(json.getBytes("UTF-8"));
//                System.out.println(os);
//                os.close();
//
//                // read the response
//                InputStream in = new BufferedInputStream(conn.getInputStream());
//                String result = in.toString();
//                System.out.println(result);
//                //System.out.println("result after Reading JSON Response");
//                //JSONObject myResponse = new JSONObject(result);
//                //System.out.println("jsonrpc- "+myResponse.getString("jsonrpc"));
//                //System.out.println("id- "+myResponse.getInt("id"));
//                //System.out.println("result- "+myResponse.getString("result"));
//                //in.close();
//                conn.disconnect();
//            } catch (Exception e) {
//                System.out.println(e);
//            }


///////////////////////////////////
//            File file = new File(filepath);
//            URL url = new URL("http://localhost:8080/upload");
//            urlconnection = url.openConnection();
//            urlconnection.setDoOutput(true);
//            urlconnection.setDoInput(true);
//System.out.println("----------- WE GOT HERE (2) ----------------");
//            if (urlconnection instanceof HttpURLConnection) {
//                ((HttpURLConnection) urlconnection).setRequestMethod("POST");
//                ((HttpURLConnection) urlconnection).setRequestProperty("Content-type", "text/json");
//                ((HttpURLConnection) urlconnection).connect();
//            }
//            BufferedOutputStream bos = new BufferedOutputStream(urlconnection.getOutputStream());
//            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
//            int i;
//            // read byte by byte until end of stream
//            while ((i = bis.read()) > 0) {
//                bos.write(i);
//            }
//            bis.close();
//            bos.close();
//            System.out.println(((HttpURLConnection) urlconnection).getResponseMessage());
//            try {
//System.out.println("----------- WE GOT HERE (3) ----------------");
//                InputStream inputStream;
//                int responseCode = ((HttpURLConnection) urlconnection).getResponseCode();
//                if ((responseCode >= 200) && (responseCode <= 202)) {
//                    inputStream = ((HttpURLConnection) urlconnection).getInputStream();
//                    int j;
//                    while ((j = inputStream.read()) > 0) {
//                        System.out.println(j);
//                    }
//System.out.println("----------- WE GOT HERE (4) ----------------");
//                } else {
//                    inputStream = ((HttpURLConnection) urlconnection).getErrorStream();
//                }
//System.out.println("----------- WE GOT HERE (5) ----------------");
//                ((HttpURLConnection) urlconnection).disconnect();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

////////////////////////////////////


        //httpGET();






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

    public static String Encoding(String json_save){
        if(json_save.contains("\"")) {
            json_save = json_save.replace("\"", "&q;");
        }
        if(json_save.contains("{")){
            json_save = json_save.replace("{", "@");
        }
        if(json_save.contains("}")){
            json_save = json_save.replace("}", "$");
        }
        if(json_save.contains("[")){
            json_save = json_save.replace("[", "?");
        }
        if(json_save.contains("]")){
            json_save = json_save.replace("]", "!");
        }
        if(json_save.contains(":")){
            json_save = json_save.replace(":", "-");
        }
        return json_save;
    }

    public static String Decoding(String allSaves) {
        if (allSaves.contains("&q;")) {
            allSaves = allSaves.replace("&q;", "\"");
        }
        if (allSaves.contains("@")) {
            allSaves = allSaves.replace("@", "{");
        }
        if (allSaves.contains("$")) {
            allSaves = allSaves.replace("$", "}");
        }
        if (allSaves.contains("?")) {
            allSaves = allSaves.replace("?", "[");
        }
        if (allSaves.contains("!")) {
            allSaves = allSaves.replace("!", "]");
        }
        if (allSaves.contains("-")) {
            allSaves = allSaves.replace("-", ":");
        }
        if (allSaves.contains("\"{")) {
            allSaves = allSaves.replace("\"{", "{");
        }
        if (allSaves.contains("}\"}")) {
            allSaves = allSaves.replace("}\"}", "}}");
        }
        return allSaves;
    }

    public static Command checkCard(String fromlist){

        if( fromlist.equals("Fast Fwd")){
            return FAST_FORWARD;
        }
        if( fromlist.equals("Triple Fwd")){
            return TRIPLE_FORWARD;
        }
        if( fromlist.equals("U-Turn")){
            return U_TURN;
        }
        if( fromlist.equals("Fwd")){
            return FORWARD;
        }
        if( fromlist.equals("Move Back")){
            return BACKUP;
        }
        if( fromlist.equals("SANDBOX ROUTINE")){
            return SANDBOX_ROUTINE;
        }
        if( fromlist.equals("WEASEL ROUTINE")){
            return WEASEL_ROUTINE;
        }
        if( fromlist.equals("Turn Left")){
            return LEFT;
        }
        if( fromlist.equals("Turn Right")){
            return RIGHT;
        }
        return null;
    }
}

