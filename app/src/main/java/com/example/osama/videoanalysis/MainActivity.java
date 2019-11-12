package com.example.osama.videoanalysis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.w3c.dom.Text;

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
        private Boolean firstPoint = true;
        public String id = "MainActivity";


        public int getVolleyCount(){

            return totalVolleys;

        }
        public void addShot(Shot shot){
            shots.add(shot);
            if (shot.getShotType().equals("Volley")){
                totalVolleys += 1;

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


    }
    public class Game implements Serializable{
        private ArrayList<String>restDurations = new ArrayList<String>();
        private ArrayList<Rally> rallies = new ArrayList<Rally>();
        private int numberOfRallies = 0;
        private int totalGameShots = 0;
        private float shotsPerSecond = 0;
        private float avgRallyLength = 0;
        private long rallyDuration = 0;

        public long getRallyDuration(){

            return rallyDuration;
        }

        public void setRallyDuration(long time){
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
            if (numberOfRallies != 0) {
                float sum = 0;
                float count = 0;
                for (int i = 0; i < rallies.size(); i++) {
                    sum += (rallies.get(i).getRallyLength());
                    count += 1;

                    avgRallyLength = sum / count;


                }
            }
            return avgRallyLength;




        }

        public int calculateTotalShots(){
            int total = 0;
            if (rallies.size() > 0) {
                for (int i = 0; i < rallies.size(); i++) {
                    total += rallies.get(i).getRallyLength();

                }
            } else{
                return 0;
            }
            return total;


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
        intent.putExtras(bundle);
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
        if (rallyButton.getText().toString() == "Rally Start") {
            currRally = new Rally();
            timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            rallyButton.setText("Rally End");
            //timer.setBase((long)0);
            timer.start();


        } else {
            timer.stop();
            rallyTime = timer.getText().toString();
            rallyButton.setText("Rally Start");
            pauseButton.setText("Rally Pause");
            timer.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
            //Ensure all the rallies are longer than one shot
            //Toast.makeText(MainActivity.this, "Rally Successfully Added", Toast.LENGTH_SHORT).show();
            if (currRally != null && currRally.getRallyLength() != 0) {
                currRally.setRallyLength(Integer.parseInt(rallyTime));
                currGame.addRally(currRally);
            }
            Log.i("id 1", currRally.id);

            //Update our total shots statistic
            /*
            Intent stats_intent = new Intent(getApplicationContext(), statistics_tracker.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("rallyLength",(Serializable) currRally.getRallyLength());
            stats_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            loadActivity(stats_intent, bundle, 2);*/



        }

    }

    public void recordShot(View view){
        currShot = new Shot();
        Object viewTag = view.getTag();
        shotArea = (TextView) findViewById(R.id.frontLeft);


        //Color indicator on the view clicked by user
        if (viewTag != shotArea.getTag()) {
            shotArea = (TextView) findViewById(R.id.frontRight);

        }

        animateBox(shotArea);


        //Assuming we get all the details about that shot, we add it to our list of shots for the rally
        currShot.setParentRally(currRally);
        //Stop timer for user to record shot data
        saveTime = timer.getBase();
        currShot.shotAreaRecord(viewTag.toString());
        timer.stop();


    }


    //Handle shots at the front of the court
    public void onShotFront(View view){
        if (currRally != null) {
            //We want to not record our shot instantly after creating it but instead create the shot in one of the other
            //activities depending on if its a winner, an error, or neither. We will then have an array of shots that are
            //polymorphic
            recordShot(view);

            //Switch to next activity
            Intent intent = new Intent(getApplicationContext(), ShotDetails.class);
            if (currRally.isFirstShot()) {
                currShot.setServeTrue();
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("shotObject",(Serializable) currShot);
            bundle.putSerializable("rallyObject",(Serializable) currRally);
            loadActivity(intent, bundle, 1);
            //Since we would have played one point already
            currRally.setServeFalse();



        }




    }

    public void onShotSide(View view){
        //We want to make sure our rally has started
        if (currRally != null){
            recordShot(view);


        }



    }



    public void onShotMiddle(View view){
        //Determine which side
        if (currRally != null) {
            currShot = new Shot();
            Object viewTag = view.getTag();
            shotArea = (TextView) findViewById(R.id.frontLeft);


            //Color indicator on the view clicked by user
            animateBox(shotArea);





        }

    }

    public void endCurrentGame(View view) {
        if (currGame.calculateTotalShots() > 0){
            currMatch.addGame(currGame);
            currGame = new Game();
            Intent stats_intent = new Intent(getApplicationContext(), statistics_tracker.class);
            Bundle bundle = new Bundle();


            bundle.putSerializable("rallyLength",(Serializable) currGame.calculateTotalShots());
            bundle.putSerializable("Number of rallies",(Serializable) currGame.rallies.size());
            bundle.putSerializable("Total vollies", (Serializable) currGame.getSumVolleys());
            //bundle.putSerializable("Avg Game Length", (Serializable) (currGame.getRallyDuration()/currMatch.getTotalGames()));

            loadActivity(stats_intent, bundle, 4);




            stats_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            loadActivity(stats_intent, bundle, 2);



        }



    }

    public void pauseTimerOnClick(View view){
        if (rallyButton.getText().toString() == "Rally End") {
            if (pauseButton.getText().toString() == "Rally Pause") {
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
        currGame.setRallyDuration((int) pauseOffset);


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

        Log.i("timer base", String.valueOf(timer.getBase()));


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
                Toast.makeText(MainActivity.this, String.valueOf(currRally.id), Toast.LENGTH_SHORT).show();

            }
            resumeTimer();


    }
}




//Winner : Drop - Lob - Kill - Drive - Boast
//Error : Same thing as winner
//Middle : Winner/ Error - Volley/Floor - Same as options above
//Back same as middle
