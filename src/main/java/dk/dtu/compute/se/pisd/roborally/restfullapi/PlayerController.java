package dk.dtu.compute.se.pisd.roborally.restfullapi;

import dk.dtu.compute.se.pisd.roborally.restfullapi.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {


    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping()
    public ResponseEntity<Board> savePlayer(@RequestBody Board player){

        return new ResponseEntity<Board>((Board) playerService.savePlayer(player), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{boardid}")
    public void deletePlayer(@PathVariable("boardid") Long playerId) {
        playerService.deletePlayer(playerId);
    }

    @GetMapping(value = "{boardid}")
    public Optional<Board> findById(@PathVariable("boardid") Long id) {
        return playerService.findById(id);
    }


    @GetMapping("/list")
    public List<Board> getAllBoards(){
        return playerService.getBoards();
    }
}
