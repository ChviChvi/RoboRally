package com.roborally.restroborally.service.impl;


import com.roborally.restroborally.exception.ResourceNotFoundException;
import com.roborally.restroborally.model.Player;
import com.roborally.restroborally.repository.PlayerRepository;

import com.roborally.restroborally.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository){
        super();
        this.playerRepository = playerRepository;
    }

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayerById(long id) {
        Optional<Player> player = playerRepository.findById(id);

        //this is without LAMBDA
        if(player.isPresent()){
            return player.get();
        }else {
            throw new ResourceNotFoundException("Player","Id",id);
        }
    }

    @Override
    public Player updatePlayer(Player player, long id) {
        //check if the player exists in the database
        Player existingPlayer = playerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Player","Id",id));

        existingPlayer.setPlayer(player.getPlayer());
        existingPlayer.setColor(player.getColor());
        //save the existingplayer to the databse
        playerRepository.save(existingPlayer);

        return existingPlayer;
    }

    @Override
    public void deletePlayer(long id) {
        // check if player exists
        playerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Player","Id",id));

        playerRepository.deleteById(id);
    }


}
