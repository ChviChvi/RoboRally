package dk.dtu.compute.se.pisd.roborally.restfullapi.model;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Data
@Embeddable
public class Players {

    private String name;
    private String color;
    private int x;
    private int y;
    private Heading heading;

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

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public Players(String name, String color, int x, int y, Heading heading) {
        this.name = name;
        this.color = color;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

}

