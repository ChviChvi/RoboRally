package dk.dtu.compute.se.pisd.roborally.restfullapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            PlayerRepository repository) {
        return args -> {
        };
    }
}
