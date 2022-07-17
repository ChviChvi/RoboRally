package dk.dtu.compute.se.pisd.roborally.restfullapi.model;

import javax.persistence.*;

//@Data
//@AllArgsConstructor
@Entity
@Table
public class Player {

    @Id
    @SequenceGenerator(
            name = "player_sequence",
            sequenceName = "player_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_sequence"
    )
    private Long id;
    private int width;
    private int height;

    @Embedded
    private Spaces spaces;
    @Embedded
    private Players players;

    public Spaces getSpaces() {
        return spaces;
    }

    public void setSpaces(Spaces spaces) {
        this.spaces = spaces;
    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public Player(
            //Spaces spaces, Players players
    ) {
//        this.spaces = spaces;
//        this.players = players;
    }

    public Player(Long id, int width, int height, Spaces spaces, Players players) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.spaces = spaces;
        this.players = players;
    }

    public Player(int width, int height, Spaces spaces, Players players) {
        this.width = width;
        this.height = height;
        this.spaces = spaces;
        this.players = players;
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

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", spaces=" + spaces +
                ", players=" + players +
                '}';
    }


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column(name = "player_number")
//    private int player;
//    @Column(name="player_color")
//    private String color;
//
//    @Override
//    public PlayerService savePlayer(Player player) {
//        return null;
//    }
//
//    @Override
//    public List<Player> getAllPlayers() {
//        return null;
//    }
//
//    @Override
//    public Player getPlayerById(long id) {
//        return null;
//    }
//
//    @Override
//    public Player updatePlayer(Player player, long id) {
//        return null;
//    }
//
//    @Override
//    public void deletePlayer(long id) {
//
//    }



}
