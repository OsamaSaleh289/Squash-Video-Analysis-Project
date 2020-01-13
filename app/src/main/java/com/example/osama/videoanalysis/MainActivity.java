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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    GameData firebaseData;
    DatabaseReference matchReff;
    Button rallyButton;
    long currentValue;
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
    int gameCount;
    int volleys = 0;
    int matchCount;
    Button saveButton;
    Button endButton;

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
        if (rallyButton.getText().toString().equals("Rally Start")) {
            currRally = new Rally();
            timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            rallyButton.setText("Rally End");
            //timer.setBase((long)0);
            timer.start();
            saveButton.setAlpha((float)0.5);
            endButton.setAlpha((float)0.5);
            endButton.setClickable(false);
            saveButton.setClickable(false);



        } else {
            timer.stop();
            saveButton.setAlpha((float)1);
            endButton.setAlpha((float)1);
            endButton.setClickable(true);
            saveButton.setClickable(true);
            rallyTime = timer.getText().toString();
            Log.i("Timer value", timer.getText().toString());
            rallyButton.setText("Rally Start");
            pauseButton.setText("Rally Pause");
            timer.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
            //Ensure all the rallies are longer than one shot
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
                shotArea = (TextView) findViewById(R.id.frontLeft);

            } else if (viewTag.equals("midLeft")){
                shotArea = (TextView) findViewById(R.id.midLeft);

            } else if (viewTag.equals("midRight")){
                shotArea = (TextView) findViewById(R.id.midRight);

            } else if (viewTag.equals("backLeft")){
                shotArea = (TextView) findViewById(R.id.backLeft);

            } else {
                shotArea = (TextView) findViewById(R.id.backRight);
            }
            shotArea.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.i("Long click is", "detected");
                    currShot.setVolleyOpportunity();
                    onShot(v);
                    return true;
                }
            });
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




    public void onShot(View view){
        Intent intent;
        Log.i("this", String.valueOf((SystemClock.elapsedRealtime() - timer.getBase())/4000) + " after /4000");
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
            Log.i("currShot", String.valueOf(currShot.equals(null)));
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
            //currGame = new Game();
            Intent stats_intent = new Intent(getApplicationContext(), statistics_tracker.class);
            Bundle bundle = new Bundle();


            bundle.putSerializable("rallyLength",(Serializable) currGame.calculateTotalShots());
            bundle.putSerializable("numRallies",(Serializable) currGame.rallies.size());
            bundle.putSerializable("Total vollies", (Serializable) currGame.getVolleysSum());
            bundle.putSerializable("avgRallyLength", (Serializable) currGame.calculateAvgRallyLength());
            bundle.putSerializable("winnerError", (Serializable) currGame.calculateWinnerToError());
            bundle.putSerializable("frontPercentage", currGame.calculatePercentages("front"));
            bundle.putSerializable("middlePercentage", currGame.calculatePercentages("middle"));
            bundle.putSerializable("backPercentage", currGame.calculatePercentages("back"));
            bundle.putSerializable("matchObject", (Serializable) currMatch);
            bundle.putSerializable("gameObject", (Serializable) currGame);
       //     Toast.makeText(getApplicationContext(), String.valueOf(currentValue), Toast.LENGTH_SHORT).show();
            bundle.putSerializable("shotsPerSecond", (Serializable)currGame.calculateShotsPerSecond(currentValue));
            bundle.putSerializable("winnersErrors", (Serializable) currGame.calculateWinnersErrors());
            //bundle.putSerializable("Avg Game Length", (Serializable) (currGame.getGameDuration()/currMatch.getTotalGames()));
            stats_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            gameCount += 1;
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

    public void onSaveClick(View view){
        //Make a list of gameData and change this accordingly
        for (Game game : currMatch.games) {
            if (!game.saved) {
                matchReff.child("Games").push().setValue(firebaseData);
                game.saved = true;
            }
        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matchReff = FirebaseDatabase.getInstance().getReference().child("Match");
        saveButton = (Button) findViewById(R.id.saveToFirebase);
        endButton = (Button) findViewById(R.id.endGame);


        timer = (Chronometer)findViewById(R.id.rallyTimer);

        //This chunk of code ensures our timer runs at .25 speed
        Chronometer.OnChronometerTickListener listener = new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                currentValue = (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;
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
        TextView mLeft = (TextView) findViewById(R.id.midLeft);
        TextView mRight = (TextView) findViewById(R.id.midRight);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        courtLine.bringToFront();
        mLeft.bringToFront();
        mRight.bringToFront();







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);
            // Make sure the request was successful

            if (resultCode == 4){
                Toast.makeText(getApplicationContext(), "Shot cancelled", Toast.LENGTH_SHORT).show();

            }else if (requestCode == 1) {
                Bundle bundle = intent.getBundleExtra("Updated information");
                currShot = (Shot) bundle.getSerializable("currShot");
                currRally = (Rally) bundle.getSerializable("currRally");
                currRally.setServeFalse();
                Toast.makeText(MainActivity.this, String.valueOf(currRally.getRallyLength()), Toast.LENGTH_SHORT).show();
                resumeTimer();

            } else if (requestCode == 2) {
                Toast.makeText(MainActivity.this, "New Game", Toast.LENGTH_SHORT).show();
                Bundle bundle = intent.getBundleExtra("Updated Match");
                currGame = (Game) bundle.getSerializable("gameObject");
                firebaseData = (GameData) bundle.getSerializable("firebaseObject");
                currMatch.addGame(currGame);
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
