package com.roborally.restroborally.player;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayersController {

    // http:/localhost:8080/player
    @GetMapping("/player")
    public Players getPlayers(){
        return new Players(1,"Blue");
    }
    @PutMapping("/playerz")
    public Players putPlayers(){
        return new Players(22,"ORANGE");
    }
    @GetMapping("/players")
    public List<Players> getPlayerss(){
        List<Players> players = new ArrayList<>();
        players.add(new Players(918,"omegalol"));
        players.add(new Players(918,"omegalol"));
        players.add(new Players(918,"omegalol"));
        players.add(new Players(918,"omegalol"));
        return players;
    }

    //http://localhost:8080/student/1
    //@PathVariable annotation
    @GetMapping("/player/{player}/{color}")
    public Players playerPathVariable(@PathVariable("player") int player,@PathVariable("color") String color){
        return new Players(player,color);
    }

    //build rest API to handle query parameters
    // http://localhost:8080/playerquery?player=2&color=orange
    @GetMapping("/playerquery")
    public Players playerQueryParam(
            @RequestParam(name = "player") int player,
            @RequestParam(name = "color") String color){
        return new Players(player,color);
    }

}
