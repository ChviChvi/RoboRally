package com.roborally.restroborally.controller;

import com.roborally.restroborally.model.Player;
import com.roborally.restroborally.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.roborally.restroborally.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping()
    public ResponseEntity<Player> savePlayer(@RequestBody Player player){
        return new ResponseEntity<Player>((Player) playerService.savePlayer(player), HttpStatus.CREATED);
    }

    // build get all players RESTAPI
    @GetMapping
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    // build get employee by id REST API
    // http://localhost:8080/api/players/1
    @GetMapping("{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") long playerId){
        return new ResponseEntity<Player>(playerService.getPlayerById(playerId), HttpStatus.OK);
    }

    //build update player REST API
    // http://localhost:8080/api/players/1
    @PutMapping("{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id
                                              ,@RequestBody Player player){
        return new ResponseEntity<Player>(playerService.updatePlayer(player, id), HttpStatus.OK);
    }

    //build delete player REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable("id")long id){

        //Deletes the player
        playerService.deletePlayer(id);

        return new ResponseEntity<String>("Player deleltion succes!", HttpStatus.OK);

    }
}
