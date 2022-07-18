package dk.dtu.compute.se.pisd.roborally.restfullapi;

import dk.dtu.compute.se.pisd.roborally.restfullapi.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {


    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

//    @PostMapping
//    public void registernewPlayer(@RequestBody Player player){
//        playerService.addNewPlayer(player);
//    }

    @PostMapping()
    public ResponseEntity<Board> savePlayer(@RequestBody Board player){
        return new ResponseEntity<Board>((Board) playerService.savePlayer(player), HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public Player read(@PathVariable String id){
//        return playerService.find(id);
//    }

    @DeleteMapping(path = "{playerId}")
    public void deletePlayer(@PathVariable("playerId") Long playerId) {
        playerService.deletePlayer(playerId);
    }


//    @PostMapping
//    public void registernewPlayers(@RequestBody List<Player> player){
//        playerService.save(player);
//    }
//
//    @GetMapping("/list")
//    public Iterable<Player> list() {
//        return playerService.list();
//    }

// LIST YOU NEED LISTS

//    @GetMapping
//    public PlayerRepository getPlayers() {
//        return playerService.getPlayers();
//    }

//    @PostMapping
//    public void registerNewBoard(@RequestBody Player player){
//        playerService.addNewPlayer(player);
//    }


    //    @GetMapping("/list")
//    public Iterable<Player> list(){
//        return playerService.list();
//    }

//    @PostMapping()
//    public ResponseEntity<Player> savePlayer(@RequestBody Player player){
//        return new ResponseEntity<Player>((Player) playerService.savePlayer(player), HttpStatus.CREATED);
//    }
//
//    // build get all players RESTAPI
//    @GetMapping
//    public List<Player> getAllPlayers(){
//        return playerService.getAllPlayers();
//    }
//
//    // build get employee by id REST API
//    // http://localhost:8080/api/players/1
//    @GetMapping("{id}")
//    public ResponseEntity<Player> getPlayerById(@PathVariable("id") long playerId){
//        return new ResponseEntity<Player>(playerService.getPlayerById(playerId), HttpStatus.OK);
//    }
//
//    //build update player REST API
//    // http://localhost:8080/api/players/1
//    @PutMapping("{id}")
//    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id
//                                              , @RequestBody Player player){
//        return new ResponseEntity<Player>(playerService.updatePlayer(player, id), HttpStatus.OK);
//    }
//
//    //build delete player REST API
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deletePlayer(@PathVariable("id")long id){
//
//        //Deletes the player
//        playerService.deletePlayer(id);
//
//        return new ResponseEntity<String>("Player deleltion succes!", HttpStatus.OK);
//
//    }
}
