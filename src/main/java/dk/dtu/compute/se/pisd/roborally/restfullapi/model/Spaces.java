package dk.dtu.compute.se.pisd.roborally.restfullapi.model;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

//@Data
//@Embeddable
//@AllArgsConstructor
//@Entity
//@Table(name="Spaces")
public class Spaces {

//    @Column(name = "id", nullable = false)
//    private Long id;

//    //@Id
//    @ManyToOne
//    //@GeneratedValue
//    @JoinColumn(name="spaces_id",nullable = false)
//    private Player player;
//    //@GeneratedValue

    //@Column(name = "SpacesC")

    ///////////////////////////////////

    @Id
    private String walls;
    private int x2;
    private int y2;

    public Spaces(String walls, int x2, int y2) {
        this.walls = walls;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Spaces() {

    }

    public String getWalls() {
        return walls;
    }

    public void setWalls(String walls) {
        this.walls = walls;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
}
