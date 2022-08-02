package dk.dtu.compute.se.pisd.roborally.restfullapi;

import dk.dtu.compute.se.pisd.roborally.restfullapi.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    public List<Board> getPlayers(){
        return playerRepository.findAll();
    }

    public List<Board> getBoards(){
        return playerRepository.findAll();
    }



    public void addNewPlayer(Board player) {
        Optional<Board> playerOptional = playerRepository
                .findPlayersById(player.getId());
        if(playerOptional.isPresent()){
            throw new IllegalStateException("id taken");
        }
        playerRepository.save(player);
        //System.out.println(player);
    }

    public Optional<Board> findById(Long id) {
        return playerRepository.findById(id);
    }

    public void deletePlayer(Long playerId) {
        boolean exists = playerRepository.existsById(playerId);
        if(!exists){
            throw new IllegalStateException("player with id " + playerId + "does not exist");
        }
        playerRepository.deleteById(playerId);
    }
// ----------------------------------------
    public Board save(Board players) {
        return playerRepository.save(players);
    }

    public Iterable<Board> list() {
        return playerRepository.findAll();
    }

    public void save(List<Board> players) {
        playerRepository.saveAll(players);
    }

    //@Override
    public Board savePlayer(Board player) {
        return playerRepository.save(player);
    }


}
