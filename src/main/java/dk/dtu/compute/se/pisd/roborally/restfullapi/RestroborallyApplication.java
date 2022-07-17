package dk.dtu.compute.se.pisd.roborally.restfullapi;

import dk.dtu.compute.se.pisd.roborally.restfullapi.model.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
//            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);
//            TypeReference<List<dk.dtu.compute.se.pisd.roborally.restfullapi.model.Player>> typeReference = new TypeReference<List<dk.dtu.compute.se.pisd.roborally.restfullapi.model.Player>>(){};
//            InputStream inputStream = TypeReference.class.getResourceAsStream("/boards/SavedGame1.json");
//            try {
//                System.out.println("you got here 1");
//                List<dk.dtu.compute.se.pisd.roborally.restfullapi.model.Player> players = mapper.readValue(inputStream,typeReference);
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
