package com.roborally.restroborally.service;
import com.roborally.restroborally.service.PlayerService;
import com.roborally.restroborally.model.Player;

import java.util.List;

public interface PlayerService {
    PlayerService savePlayer(Player player);
    List<Player> getAllPlayers();
    Player getPlayerById(long id);
    Player updatePlayer(Player player, long id);
    void deletePlayer(long id);
}
