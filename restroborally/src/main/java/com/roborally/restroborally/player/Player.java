//package com.roborally.restroborally.player;
//
//import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
//import dk.dtu.compute.se.pisd.roborally.model.Heading;
//import dk.dtu.compute.se.pisd.roborally.model.Space;
//
//import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
//
//public class Player {
//
//    final public static int NO_REGISTERS = 5;
//    final public static int NO_CARDS = 8;
//
//    //final public Board board;
//
//    private String name;
//    private String color;
//    private Long id;
//
//    private Space space;
//    private Heading heading = SOUTH;
//    private int progress = 0;
//
//    private CommandCardField[] program;
//    private CommandCardField[] cards;
//
//
//    public Player() {
//    }
//
//    public Player(Long id,
//                  String name,
//                  String color,
//                  //Space space,
//                  Heading heading
//                  //int progress,
//                  //CommandCardField[] program,
//                  //CommandCardField[] cards
//    )
//    {
//        this.id = id;
//        this.name = name;
//        //this.color = color;
//        //this.space = space;
//        this.heading = heading;
//        //this.progress = progress;
//        //this.program = program;
//        //this.cards = cards;
//    }
//
//    public Player(String name,
//                  String color,
//                  //Space space,
//                  Heading heading
//                  //int progress,
//                  //CommandCardField[] program,
//                  //CommandCardField[] cards
//    ) {
//        this.name = name;
//        this.color = color;
//        this.space = space;
//        this.heading = heading;
//        this.progress = progress;
//        this.program = program;
//        this.cards = cards;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Space getSpace() {
//        return space;
//    }
//
//    public void setSpace(Space space) {
//        this.space = space;
//    }
//
//    public Heading getHeading() {
//        return heading;
//    }
//
//    public void setHeading(Heading heading) {
//        this.heading = heading;
//    }
//
//    public int getProgress() {
//        return progress;
//    }
//
//    public void setProgress(int progress) {
//        this.progress = progress;
//    }
//
//    public CommandCardField[] getProgram() {
//        return program;
//    }
//
//    public void setProgram(CommandCardField[] program) {
//        this.program = program;
//    }
//
//    public CommandCardField[] getCards() {
//        return cards;
//    }
//
//    public void setCards(CommandCardField[] cards) {
//        this.cards = cards;
//    }
//
//    @Override
//    public String toString() {
//        return "player{" +
//                "name='" + name + '\'' +
//                ", color='" + color + '\'' +
//                ", id=" + id +
//                //", space=" + space +
//                ", heading=" + heading +
//                //", progress=" + progress +
//                //", program=" + Arrays.toString(program) +
//                //", cards=" + Arrays.toString(cards) +
//                '}';
//    }
//}
