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

    public void addNewPlayer(Board player) {
        Optional<Board> playerOptional = playerRepository
                .findPlayersById(player.getId());
        if(playerOptional.isPresent()){
            throw new IllegalStateException("id taken");
        }
        playerRepository.save(player);
        //System.out.println(player);
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

//    public Player find(String id) {
//        return playerRepository.findPlayer(id);
//    }





//    public PlayerService(PlayerRepository playerService){
//        //super();
//        this.playerService = playerService;
//    }
//
//    public PlayerRepository getPlayers() {
//        return playerService;
//    }


//    public void addNewPlayer(Player player) {
//        Optional<Player> playerOptional = playerRepository
//                .findPlayersById(player.getId());
//        if(playerOptional.isPresent()){
//            throw new IllegalStateException("id taken");
//        }
//        playerRepository.save(player);
//        //System.out.println(player);
//    }






//    //public Iterable<Player> list(){
//    //        return playerRepository.findAll();
//    //    }
//
//    public Player save(Player player){
//        return playerRepository.save(player);
//    }
//
//    //list method
//    public void save(List<Player> players){
//        playerRepository.saveAll(players);
//    }

//    @Override
//    public Player savePlayer(Player player) {
//        return playerRepository.save(player);
//    }
//
//    @Override
//    public List<Player> getAllPlayers() {
//        return playerRepository.findAll();
//    }
//
//    @Override
//    public Player getPlayerById(long id) {
//        Optional<Player> player = playerRepository.findById(id);
//
//        //this is without LAMBDA
//        if(player.isPresent()){
//            return player.get();
//        }else {
//            throw new ResourceNotFoundException("Player","Id",id);
//        }
//    }
//
//    @Override
//    public Player updatePlayer(Player player, long id) {
//        //check if the player exists in the database
//        Player existingPlayer = playerRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Player","Id",id));
//
//        existingPlayer.setPlayer(player.getPlayer());
//        existingPlayer.setColor(player.getColor());
//        //save the existingplayer to the databse
//        playerRepository.save(existingPlayer);
//
//        return existingPlayer;
//    }
//
//    @Override
//    public void deletePlayer(long id) {
//        // check if player exists
//        playerRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Player","Id",id));
//
//        playerRepository.deleteById(id);
//    }


}
