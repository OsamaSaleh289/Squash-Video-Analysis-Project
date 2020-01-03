package com.example.osama.videoanalysis;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    public ArrayList<Rally> rallies = new ArrayList<Rally>();
    private int numberOfRallies = 0;
    private double totalGameShots = 0;
    private float shotsPerSecond = 0;
    private float avgRallyLength = 0;
    private long rallyDuration = 0;

    public long getRallyDuration() {

        return rallyDuration;
    }

    public double calculatePercentages(String area) {
        double sum = 0;
        Log.i("Number of rallies", String.valueOf(rallies.size()));
        if (rallies.size() > 0) {
            for (Rally rally : rallies) {
                sum += (double) rally.shotAreaCount(area);
                Log.i("sum", String.valueOf(sum));

            }
            return Math.round((sum / totalGameShots) * 100);

        }
        return 0;


    }


    public void setCurrentDuration(long time) {
        //The time on the clock when we end the game
        rallyDuration += time;

    }

    public void addRally(Rally rally) {
        rallies.add(rally);
        totalGameShots += rally.getRallyLength();
        numberOfRallies += 1;

    }

    public int getSumVolleys() {

        int total = 0;
        for (Rally rally : rallies) {
            total += rally.getVolleyCount();


        }
        return total;
    }

    public float calculateAvgRallyLength() {
        float sum = 0;
        float count = 0;
        if (numberOfRallies != 0) {

            for (int i = 0; i < rallies.size(); i++) {
                sum += (rallies.get(i).getRallyDuration());
                count += 1;


            }
            avgRallyLength = sum / count;
        }
        Log.i("time", String.valueOf(sum));
        return avgRallyLength;


    }

    public int calculateTotalShots() {
        int total = 0;
        if (rallies.size() > 0) {
            for (int i = 0; i < rallies.size(); i++) {
                total += rallies.get(i).getRallyLength();

            }
            return total;
        } else {
            return 0;
        }


    }

    public float calculateWinnerToError() {
        int totalWinners = 0;
        int totalErrors = 0;

        if (rallies.size() > 0) {
            for (int i = 0; i < rallies.size(); i++) {
                totalWinners += rallies.get(i).getWinnersTotal();
                totalErrors += rallies.get(i).getErrorsTotal();

            }
            if (totalErrors > 0) {
                return (totalWinners / totalErrors);
            } else {
                return 10000;
            }
        }
        return 10000;

    }
}