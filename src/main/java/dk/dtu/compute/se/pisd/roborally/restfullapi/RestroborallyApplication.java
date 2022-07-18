package dk.dtu.compute.se.pisd.roborally.restfullapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RestroborallyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestroborallyApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(PlayerService playerService){
//        return args -> {
//            //read json and write it to sql
//            ObjectMapper mapper = new ObjectMapper();
//            //mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);
//            TypeReference<List<Player>> typeReference = new TypeReference<List<Player>>() {};
//            InputStream inputStream = TypeReference.class.getResourceAsStream("/boards/SavedGame1.json");
//            try {
//                System.out.println("you got here 1");
//                List<Player> players = mapper.readValue(inputStream,typeReference);
//                System.out.println("you got here 2");
//                playerService.save(players);
//
//                System.out.println("Users Saved!");
//            } catch (IOException e){
//                System.out.println("Unable to save players: " + e.getMessage());
//            }
//
//        };
//    }
}
