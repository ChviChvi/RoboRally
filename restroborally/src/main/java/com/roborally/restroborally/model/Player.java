package com.roborally.restroborally.model;

import com.roborally.restroborally.service.PlayerService;
import lombok.Data;



import javax.persistence.*;
import java.util.List;

@Data
@Entity //now it is a JP entity
@Table(name="players")
public class Player implements PlayerService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "player_number")
    private int player;
    @Column(name="player_color")
    private String color;

    @Override
    public PlayerService savePlayer(Player player) {
        return null;
    }

    @Override
    public List<Player> getAllPlayers() {
        return null;
    }

    @Override
    public Player getPlayerById(long id) {
        return null;
    }

    @Override
    public Player updatePlayer(Player player, long id) {
        return null;
    }

    @Override
    public void deletePlayer(long id) {

    }



}
