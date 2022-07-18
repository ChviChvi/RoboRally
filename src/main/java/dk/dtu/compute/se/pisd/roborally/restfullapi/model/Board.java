package dk.dtu.compute.se.pisd.roborally.restfullapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Board")
public class Board {

    @Id
//    @SequenceGenerator(
//            name = "player_sequence",
//            sequenceName = "player_sequence",
//            allocationSize = 1
//    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            //generator = "player_sequence"
            //strategy = GenerationType.AUTO
    )
    private Long id;
    private int width;
    private int height;

    //@Embedded
    //@Transient
    @OneToMany
    private List<Spaces> spaces;
    @OneToMany
    private List<Players> players;

    //private List<Spaces> spaces;
    //@Embedded
    //@ElementCollection


    public Board(Long id, int width, int height, List<Players> players, List<Spaces> spaces) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.players = players;
        this.spaces = spaces;
    }

    public Board() {
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", players=" + players +
                ", spaces=" + spaces +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Players> getPlayers() {
        return players;
    }

    public void setPlayers(List<Players> players) {
        this.players = players;
    }

    public List<Spaces> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Spaces> spaces) {
        this.spaces = spaces;
    }

    public Board(int width, int height, List<Players> players, List<Spaces> spaces) {
        this.width = width;
        this.height = height;
        this.players = players;
        this.spaces = spaces;
    }
}
