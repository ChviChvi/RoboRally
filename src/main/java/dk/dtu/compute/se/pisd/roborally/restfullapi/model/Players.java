package dk.dtu.compute.se.pisd.roborally.restfullapi.model;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

//Data
//@Embeddable
//@AllArgsConstructor
//@Entity
//@Table(name = "players")
public class Players {

//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    //@Id
//    @ManyToOne
//    @JoinColumn(name="players_id",nullable = false)
//    private Player player;

    //@Column(name = "PlayersC")

    @Id
    private String name;
    private String color;
    private int x;
    private int y;
    private String heading;

    public Players(String name, String color, int x, int y, String heading) {
        this.name = name;
        this.color = color;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public Players() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}

