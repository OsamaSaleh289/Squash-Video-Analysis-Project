package com.example.osama.videoanalysis;

import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable {
    public int numberOfGames = 0;
    ArrayList<Game> games = new ArrayList<Game>();
    private int avgGameLength = 0;
    public static int avgMatchLength = 0;
    public static int totalMatchShots = 0;
    private int totalShots = 0;
    private int totalRallies = 0;

    public void addGame(Game game){
        games.add(game);
        numberOfGames += 1;

    }
    public int getTotalGames(){

        return numberOfGames;

    }



}
