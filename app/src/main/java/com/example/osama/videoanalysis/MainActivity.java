package com.example.osama.videoanalysis;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {

    Button rallyButton;
    TextView shotArea;
    String rallyTime;
    Match currMatch;
    Game currGame;
    Rally currRally;
    Shot currShot;
    Button pauseButton;
    long saveTime;
    Chronometer timer;
    long pauseOffset;
    String saveTimeString;


    public static class Shot implements Serializable{
        private Boolean volleyOpportunity;
        private Boolean volley;
        public String shotType;
        private Boolean serve = false;
        private String shotArea = "";
        private Rally parentRally;


        public String getShotType(){

            return shotType;
        }

        public void setShotType(String type){
            shotType = type;


        }

        public Boolean isServe(){
            return serve;


        }

        public void setServeTrue(){
            serve = true;

        }

        public void shotAreaRecord(String s){
            shotArea = s;


        }

        public void setParentRally(Rally r){
            parentRally = r;


        }

        public void parentLetStroke(){
            parentRally.letstroke = true;


        }

        public String getShotArea(){
            return shotArea;


        }


    }

    public static class Winner extends Shot implements Serializable{
        public String id = "winner";


    }

    public static class Error extends Shot implements Serializable{
        public String id = "error";


    }


    public static class Rally implements Serializable{
        public Boolean letstroke = false;
        private Boolean volleyedReturn = false;
        private int totalVolleys = 0;
        private ArrayList<Shot> shots = new ArrayList<Shot>();
        private int rallyLength = 0;
        private float duration = 0;
        private Boolean firstPoint = true;
        private int numWinners = 0;
        private int numErrors = 1;

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
        public void addShot(Shot shot){
            shots.add(shot);
            Log.i("shotType", shot.getShotType());

            if (shot.getShotType().equals("Volley") || shot.getShotType().equals("volley")){
                totalVolleys += 1;

            } else if (shot.getShotType().equals("Winner")) {
                numWinners += 1;
            }else if (shot.getShotType().equals("Error")) {
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
    public class Game implements Serializable{
        private ArrayList<Rally> rallies = new ArrayList<Rally>();
        private int numberOfRallies = 0;
        private int totalGameShots = 0;
        private float shotsPerSecond = 0;
        private float avgRallyLength = 0;
        private long rallyDuration = 0;

        public long getRallyDuration(){

            return rallyDuration;
        }

        public float calculatePercentages(String area){
            int sum = 0;
            if (rallies.size() != 0) {
                for (Rally rally : rallies) {
                    sum += rally.shotAreaCount(area);

                }
                return sum/totalGameShots;

            }
            return 0;


        }



        public void setCurrentDuration(long time){
            //The time on the clock when we end the game
            rallyDuration += time;

        }

        public void addRally(Rally rally) {
            rallies.add(rally);
            totalGameShots += rally.getRallyLength();
            numberOfRallies += 1;

        }

        public int getSumVolleys(){

            int total = 0;
            for (Rally rally: rallies){
                total += rally.getVolleyCount();


            }
            return total;
        }

        public float calculateAvgRallyLength(){
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

        public int calculateTotalShots(){
            int total = 0;
            if (rallies.size() > 0) {
                for (int i = 0; i < rallies.size(); i++) {
                    total += rallies.get(i).getRallyLength();

                }
                return total;
            } else{
                return 0;
            }


        }

        public float calculateWinnerToError(){
            int totalWinners = 0;
            int totalErrors = 0;

            for (int i=0; i<rallies.size();i++){
                totalWinners += rallies.get(i).getWinnersTotal();


            }

            for (int i=0; i<rallies.size();i++){
                totalErrors += rallies.get(i).getErrorsTotal();


            }

            return (totalWinners/totalErrors);

        }
    }
    public static class Match implements Serializable{
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


    //Load a new activity and send appropriate data
    public void loadActivity(Intent intent, Bundle bundle, int requestCode){
        intent.putExtra("information", bundle);
        pauseTimer();
        startActivityForResult(intent, requestCode);


    }

    public void animateBox(final TextView shotArea){
        final Drawable saveColour = shotArea.getBackground();


        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                // When timer is finished
                // Execute your code here
                //How to retrieve original color ?
                shotArea.setBackground(saveColour);

            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
                shotArea.setBackgroundColor(Color.BLUE);
            }
        }.start();


    }


    public void startRally(View view) {
        //Fix the problem that you need to click twice to start the rally !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (rallyButton.getText().toString().equals("Rally Start")) {
            currRally = new Rally();
            timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            rallyButton.setText("Rally End");
            //timer.setBase((long)0);
            timer.start();


        } else {
            timer.stop();
            rallyTime = timer.getText().toString();
            Log.i("Timer value", timer.getText().toString());
            rallyButton.setText("Rally Start");
            pauseButton.setText("Rally Pause");
            timer.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
            //Ensure all the rallies are longer than one shot
            //Toast.makeText(MainActivity.this, "Rally Successfully Added", Toast.LENGTH_SHORT).show();
            if (currRally != null && currRally.getRallyLength() != 0) {
                //Currently only updates when length of rally is less than a minute
                currRally.setRallyDuration(Integer.parseInt(rallyTime.substring(3,5)));
                currGame.addRally(currRally);
            }

            //Update our total shots statistic
            /*
            Intent stats_intent = new Intent(getApplicationContext(), statistics_tracker.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("rallyLength",(Serializable) currRally.getRallyLength());
            stats_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            loadActivity(stats_intent, bundle, 2);*/



        }

    }

    public void recordShot(View view, String area){
        if (rallyButton.getText().toString().equals("Rally End")) {


            currShot = new Shot();
            String viewTag = view.getTag().toString();


            //Color indicator on the view clicked by user
            if (viewTag.equals("frontRight")) {
                shotArea = (TextView) findViewById(R.id.frontRight);

            } else if (viewTag.equals("frontLeft")){
                shotArea = (TextView) findViewById(R.id.frontRight);

            } else if (viewTag.equals("midLeft")){
                shotArea = (TextView) findViewById(R.id.midLeft);

            } else if (viewTag.equals("midRight")){
                shotArea = (TextView) findViewById(R.id.midRight);

            } else if (viewTag.equals("backLeft")){
                shotArea = (TextView) findViewById(R.id.backLeft);

            } else {
                shotArea = (TextView) findViewById(R.id.backRight);
            }
            currShot.shotAreaRecord(area);
            animateBox(shotArea);


            //Assuming we get all the details about that shot, we add it to our list of shots for the rally
            currShot.setParentRally(currRally);
            //Stop timer for user to record shot data
            saveTime = timer.getBase();
            currShot.shotAreaRecord(viewTag.toString());
            timer.stop();
        }


    }


    //Handle shots at the front of the court
    public void onShot(View view){
        Intent intent;
        if (currRally != null && rallyButton.getText().toString().equals("Rally End")) {
            //We want to not record our shot instantly after creating it but instead create the shot in one of the other
            //activities depending on if its a winner, an error, or neither. We will then have an array of shots that are
            //polymorphic
            if (view.getTag().toString().equals("frontLeft") || view.getTag().toString().equals("frontRight")) {
                recordShot(view, "front");
                intent = new Intent(getApplicationContext(), ShotDetails.class);
            } else if (view.getTag().toString().equals("midLeft") || view.getTag().toString().equals("midRight")) {
                recordShot(view, "middle");
                intent = new Intent(getApplicationContext(), shot_middle.class);
            } else {
                recordShot(view, "back");
                intent = new Intent(getApplicationContext(), shot_middle.class);

            }

            //Switch to next activity
            if (currRally.isFirstShot()) {
                currShot.setServeTrue();
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("shotObject", (Serializable) currShot);
            bundle.putSerializable("rallyObject", (Serializable) currRally);
            loadActivity(intent, bundle, 1);

        }
    }




    /*public void onShotMiddle(View view){
        //Determine which side
        if (currRally != null && rallyButton.getText().toString().equals("Rally End")) {
            currShot = new Shot();
            Object viewTag = view.getTag();
            recordShot(view, "middle");
            Intent intent = new Intent(getApplicationContext(), shot_middle.class);
            //Color indicator on the view clicked by user
            animateBox(shotArea);

            Bundle bundle = new Bundle();
            bundle.putSerializable("shotObject",(Serializable) currShot);
            bundle.putSerializable("rallyObject",(Serializable) currRally);
            loadActivity(intent, bundle, 1);






        }

    }*/

    public void endCurrentGame(View view) {
        if (currGame.calculateTotalShots() > 0){
            currMatch.addGame(currGame);
            //currGame = new Game();
            Intent stats_intent = new Intent(getApplicationContext(), statistics_tracker.class);
            Bundle bundle = new Bundle();

            Log.i("currGame", String.valueOf(currGame.calculateTotalShots()));
            bundle.putSerializable("rallyLength",(Serializable) currGame.calculateTotalShots());
            bundle.putSerializable("numRallies",(Serializable) currGame.rallies.size());
            bundle.putSerializable("Total vollies", (Serializable) currGame.getSumVolleys());
            bundle.putSerializable("avgRallyLength", (Serializable) currGame.calculateAvgRallyLength());
            bundle.putSerializable("winnerError", (Serializable) currGame.calculateWinnerToError());
            bundle.putSerializable("frontPercentage", currGame.calculatePercentages("front"));
            bundle.putSerializable("middlePercentage", currGame.calculatePercentages("middle"));
            bundle.putSerializable("backPercentage", currGame.calculatePercentages("back"));
            //bundle.putSerializable("Avg Game Length", (Serializable) (currGame.getRallyDuration()/currMatch.getTotalGames()));
            stats_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            loadActivity(stats_intent, bundle, 2);

        }



    }

    public void pauseTimerOnClick(View view){
        if (rallyButton.getText().toString().equals("Rally End")) {
            if (pauseButton.getText().toString().equals("Rally Pause")) {
                pauseTimer();
                pauseButton.setText("Rally Resume");


            } else {
                resumeTimer();
                pauseButton.setText("Rally Pause");


            }

        }

    }

    public void pauseTimer(){
        timer.stop();
        //Time difference that our chronometer currently displays
        pauseOffset = SystemClock.elapsedRealtime() - timer.getBase();
        currGame.setCurrentDuration((int) pauseOffset);


    }

    public void resumeTimer(){
        timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        timer.start();


    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (Chronometer)findViewById(R.id.rallyTimer);

        //This chunk of code ensures our timer runs at .25 speed
        Chronometer.OnChronometerTickListener listener = new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long currentValue = (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;
                Log.i("currentValue = ", String.valueOf(currentValue));
                if (currentValue % 4 == 0){
                    int minutes = (int)(currentValue / 60 / 4);
                    Log.i("minutes ", String.valueOf(minutes));
                    if (minutes > 0){
                        if (minutes >= 9){
                            saveTimeString = "0"+minutes+":"+((currentValue / 4) % 60);

                        } else{
                            saveTimeString = minutes+":"+((currentValue / 4) % 60);

                        }

                    } else {
                        if (currentValue / 4 < 10) {
                            saveTimeString = "00:0" + currentValue / 4;
                        } else{
                            saveTimeString = "00:" + currentValue / 4;
                        }
                        timer.setText(saveTimeString);
                    }
                } else{
                    timer.setText(saveTimeString);
                }


            }
        };

        timer.setOnChronometerTickListener(listener);

        //Log.i("timer base", String.valueOf(timer.getBase()));


        currMatch = new Match();
        currGame = new Game();



        rallyButton = (Button) findViewById(R.id.rallyButton);
        TextView courtLine = (TextView) findViewById(R.id.courtLine);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        courtLine.bringToFront();





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);
            // Make sure the request was successful
            if (requestCode == 1) {
                Bundle bundle = intent.getBundleExtra("Updated information");
                currShot = (Shot) bundle.get("currShot");
                currRally = (Rally) bundle.get("currRally");
                currRally.setServeFalse();
                Toast.makeText(MainActivity.this, String.valueOf(currRally.getRallyLength()), Toast.LENGTH_SHORT).show();
                resumeTimer();

            } else if (requestCode == 2){
                Toast.makeText(MainActivity.this, "New Game", Toast.LENGTH_SHORT).show();
                currGame = new Game();
                timer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;


            }



    }
}




//Winner : Drop - Lob - Kill - Drive - Boast
//Error : Same thing as winner
//Middle : Winner/ Error - Volley/Floor - Same as options above
//Back same as middle
//Percentage of shots in which areas of the court
//Percentage of winners/errors that were hit as type x, y, z, .....
