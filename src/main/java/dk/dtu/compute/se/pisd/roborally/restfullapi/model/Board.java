package dk.dtu.compute.se.pisd.roborally.restfullapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String gamestate;

    public Board(Long id, String gamestate) {
        this.id = id;
        this.gamestate = gamestate;
    }

    public Board(String gamestate) {
        this.gamestate = gamestate;
    }

    public Board() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGamestate() {
        return gamestate;
    }

    public void setGamestate(String gamestate) {
        this.gamestate = gamestate;
    }
}


