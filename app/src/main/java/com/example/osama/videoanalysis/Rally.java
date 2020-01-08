package com.example.osama.videoanalysis;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Rally implements Serializable {
    public Boolean letstroke = false;
    private Boolean volleyedReturn = false;
    private int totalVolleys = 0;
    private ArrayList<Shot> shots = new ArrayList<Shot>();
    private int rallyLength = 0;
    private float duration = 0;
    private Boolean firstPoint = true;
    private int numWinners = 0;
    private int numErrors = 0;

    public int shotAreaCount(String areaDesired){
        int count = 0;

        for (Shot shot: shots){
            String areaHit = shot.getShotArea().substring(0,2);
            if (areaHit.equals(areaDesired.substring(0,2))){
                count += 1;



            }

        }
        Log.i("Count for type ", areaDesired +" =" + count + " while the number of shots is "+shots.size());
        return count;

    }

    public void setRallyDuration(float duration){
        this.duration = duration;

    }

    public float getRallyDuration(){
        return duration;

    }


    public int getVolleyCount(){

        return totalVolleys;

    }

    public ArrayList<String> getWinnersErrors(){
        ArrayList<String> winnersErrors = new ArrayList<String>();
        for (Shot shot :shots){
            if (shot.getShotType().substring(0,3).equals("Win") || shot.getShotType().substring(0,3).equals("Err")){
                winnersErrors.add(shot.getShotType());

            }

        }
        return winnersErrors;


    }

    public void addShot(Shot shot){
        shots.add(shot);
        Log.i("shotType", shot.getShotType());

        if (shot.getShotType().equals("Volley") || shot.getShotType().equals("volley")){
            totalVolleys += 1;

        } else if (shot.getShotType().substring(0,3).equals("Win")) {
            numWinners += 1;

        }else if (shot.getShotType().substring(0,3).equals("Err")) {
            numErrors += 1;
        }

        rallyLength += 1;


    }

    public void setRallyLength(int length){
        rallyLength = length;

    }

    public void setServeFalse(){
        firstPoint = false;


    }

    public Boolean isFirstShot(){
        return firstPoint;

    }

    public int getRallyLength(){
        return shots.size();

    }

    public int getWinnersTotal(){
        return numWinners;

    }

    public int getErrorsTotal(){
        return numErrors;

    }


}



