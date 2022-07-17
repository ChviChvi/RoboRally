package dk.dtu.compute.se.pisd.roborally.restfullapi.model;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Spaces {

    private Heading walls;
    private int x2;
    private int y2;

    public Heading getWalls() {
        return walls;
    }

    public void setWalls(Heading walls) {
        this.walls = walls;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x) {
        this.x2 = x;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y) {
        this.y2 = y;
    }

    public Spaces() {
    }
}
