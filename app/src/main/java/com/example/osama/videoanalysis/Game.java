package com.example.osama.videoanalysis;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class Game implements Serializable {
    public boolean saved = false;
    public ArrayList<Rally> rallies = new ArrayList<Rally>();
    private int numberOfRallies = 0;
    private long totalGameShots = 0;
    private float shotsPerSecond = 0;
    private double averageRallyLength = 0;
    private long gameDuration = 0;

    private float winnersToErrors = 0;
    private double FrontShotsPercentage = 0;
    private double MiddleShotsPercentage = 0;
    private double BackShotsPercentage = 0;
    private int totalRallies = 0;

    private HashMap<String, Integer> errorData = new HashMap<String, Integer>();
    private HashMap<String, Integer> winnerData = new HashMap<String, Integer>();

    public Game(){
        errorData.put("boast", 0);
        errorData.put("kill", 0);
        errorData.put("straight", 0);
        errorData.put("drop", 0);
        errorData.put("lob", 0);

        winnerData.put("boast", 0);
        winnerData.put("kill", 0);
        winnerData.put("straight", 0);
        winnerData.put("drop", 0);
        winnerData.put("lob", 0);

    }

    public int[] calculateGameVolleys(){
        int[] returnValue = new int[]{0,0};
        for (Rally rally : rallies){
            int[] rallyList = rally.calculateVolleys();
            returnValue[0] += rallyList[0];
            returnValue[1] += rallyList[1];

        }
        return returnValue;


    }

    public int getTotalWinners(){
        int sum = 0;
        for (int winner : winnerData.values()){
            sum += winner;

        }
        return sum;
    }

    public int getTotalErrors(){
        int sum = 0;
        for (int error : errorData.values()){
            sum += error;

        }
        return sum;
    }

    public  HashMap[] calculateWinnersErrors(){
        for (Rally rally : rallies) {
            ArrayList<String> winnersErrors = rally.getWinnersErrors();
            for (String type : winnersErrors) {
                String winnerErrorType = type.split("/")[1];
                if (type.substring(0, 3).equals("Win")) {
                    winnerData.put(winnerErrorType, winnerData.get(winnerErrorType) + 1);
                } else if (type.substring(0, 3).equals("Err")) {
                    errorData.put(winnerErrorType, errorData.get(winnerErrorType) + 1);
                }

            }
        }
        return new HashMap[]{winnerData, errorData};

    }

    public int getNumberOfRallies(){
        return rallies.size();

    }

    public float getShotsPerSecond() {
        return shotsPerSecond;
    }

    public long calculateShotsPerSecond(long totalGameTime) {
        Log.i("new time is ", String.valueOf(totalGameTime));
        long currentValue = totalGameTime / 4;
        if (currentValue > 0){
            return totalGameShots / currentValue;
        }
        return totalGameShots;



    }

    public String getAverageRallyLength() {
        return averageRallyLength + " seconds";
    }

    public void setAverageRallyLength(double averageRallyLength) {
        this.averageRallyLength = averageRallyLength;
    }

    public float getWinnersToErrors() {
        return winnersToErrors;
    }

    public void setWinnersToErrors(float winnersToErrors) {
        this.winnersToErrors = winnersToErrors;
    }

    public double getFrontShotsPercentage() {
        return FrontShotsPercentage;
    }

    public void setFrontShotsPercentage(double frontShotsPercentage) {
        this.FrontShotsPercentage = frontShotsPercentage;
    }

    public double getMiddleShotsPercentage() {
        return MiddleShotsPercentage;
    }

    public void setMiddleShotsPercentage(double middleShotsPercentage) {
        this.MiddleShotsPercentage = middleShotsPercentage;
    }

    public double getBackShotsPercentage() {
        return BackShotsPercentage;
    }

    public void setBackShotsPercentage(double backShotsPercentage) {
        this.BackShotsPercentage = backShotsPercentage;
    }

    public int getTotalRallies() {
        return totalRallies;
    }

    public void setTotalRallies(int totalRallies) {
        this.totalRallies = totalRallies;
    }

    public long getGameDuration() {

        return (gameDuration/4000);
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
        gameDuration += time;

    }

    public void addRally(Rally rally) {
        rallies.add(rally);
        totalGameShots += rally.getRallyLength();
        numberOfRallies += 1;

    }

    public int getVolleysSum() {

        int total = 0;
        for (Rally rally : rallies) {
            total += rally.getVolleyCount();


        }
        return total;
    }

    public double calculateAvgRallyLength() {
        double sum = 0;
        double count = 0;
        if (numberOfRallies != 0) {

            for (int i = 0; i < rallies.size(); i++) {
                sum += (rallies.get(i).getRallyDuration());
                count += 1;


            }
        }
        return sum / count;


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