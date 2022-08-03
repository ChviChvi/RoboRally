package com.roborally.restroborally.player;

public class Players {
    private int Player;
    private String Color;

    public Players(int player, String color) {
        Player = player;
        Color = color;
    }

    public int getPlayer() {
        return Player;
    }

    public void setPlayer(int player) {
        Player = player;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
