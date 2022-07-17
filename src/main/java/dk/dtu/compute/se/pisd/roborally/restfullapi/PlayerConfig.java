package dk.dtu.compute.se.pisd.roborally.restfullapi;

import dk.dtu.compute.se.pisd.roborally.restfullapi.model.Player;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PlayerConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            PlayerRepository repository) {
        return args -> {
        };
    }
}
