package com.example.osama.videoanalysis;

import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable {
    public int numberOfGames = 0;
    public ArrayList<Game> games = new ArrayList<Game>();
    private int avgGameLength = 0;
    private float avgRallyLength = 0;
    private float shotsPerSecond = 0;
    private float winnersToErrors = 0;
    private double perFrontShots = 0;
    private double perMidShots = 0;
    private double perBackShots = 0;
    private int totalShots = 0;
    private int totalRallies = 0;

    public void addGame(Game game){
        games.add(game);
        numberOfGames += 1;

        totalRallies += game.getNumberOfRallies();
        totalShots += game.calculateTotalShots();

    }

    /*public double calcShotsPerSecond(int time){
        int shots = 0;
        for (Game game : games){
            shots += game.calculateTotalShots();

        }



    }*/

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public void setAvgGameLength(int avgGameLength) {
        this.avgGameLength = avgGameLength;
    }

    public void setAvgRallyLength(float avgRallyLength) {
        this.avgRallyLength = avgRallyLength;
    }

    public void setShotsPerSecond(float shotsPerSecond) {
        this.shotsPerSecond = shotsPerSecond;
    }

    public void setWinnersToErrors(float winnersToErrors) {
        this.winnersToErrors = winnersToErrors;
    }

    public void setPerFrontShots(double perFrontShots) {
        this.perFrontShots = perFrontShots;
    }

    public void setPerMidShots(double perMidShots) {
        this.perMidShots = perMidShots;
    }

    public void setPerBackShots(double perBackShots) {
        this.perBackShots = perBackShots;
    }

    public void setTotalShots(int totalShots) {
        this.totalShots += totalShots;
    }

    public void setTotalRallies(int totalRallies) {
        this.totalRallies += totalRallies;
    }


    public int getAvgGameLength() {
        return avgGameLength;
    }

    public float getAvgRallyLength() {
        return avgRallyLength;
    }

    public float getShotsPerSecond() {
        return shotsPerSecond;
    }

    public float getWinnersToErrors() {
        return winnersToErrors;
    }

    public double getPerFrontShots() {
        return perFrontShots;
    }

    public double getPerMidShots() {
        return perMidShots;
    }

    public double getPerBackShots() {
        return perBackShots;
    }

    public int getTotalShots() {
        return totalShots;
    }

    public int getTotalRallies() {
        return totalRallies;
    }
}
