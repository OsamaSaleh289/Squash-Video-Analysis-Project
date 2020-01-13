package com.example.osama.videoanalysis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class statistics_tracker extends AppCompatActivity {
    GameData firebaseData;
    Intent intent;
    Button backButton;
    int rallyLength;
    int totalRallies;
    double avgRallyLength;
    double shotsPerSecond;
    float winnerError;
    Match currMatch;
    long sps;
    Game currGame;
    HashMap<String, Integer>[] winnerErrorData;
    int totalWinners;
    int totalErrors;
    HashMap<String, Integer> winnerData;
    HashMap<String, Integer> errorData;
    int volleysHit;
    int volleyChances;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_tracker);

        firebaseData = new GameData();

        //Assign variable references to view
        backButton = (Button) findViewById(R.id.backButton);
        TextView totalShotsValue = (TextView) findViewById(R.id.totalShotsValue);
        TextView totalRalliesValue = (TextView) findViewById(R.id.totalRalliesValue);
        TextView avgGameLength = (TextView) findViewById(R.id.avgGameLengthValue);
        TextView avgRallyLengthValue = (TextView) findViewById(R.id.avgRallyLengthValue);
        TextView shotsPerSecondValue = (TextView) findViewById(R.id.avgShotSecValue);
        TextView winnerErrorValue = (TextView) findViewById(R.id.winnersErrorsRatioValue);
        TextView frontPercValue = (TextView) findViewById(R.id.frontShotsPercentageValue);
        TextView midPercValue = (TextView) findViewById(R.id.middleShotsPercentageValue);
        TextView backPercValue = (TextView) findViewById(R.id.backShotsPercentageValue);
        TextView boastPercValue = (TextView) findViewById(R.id.boastPercentageValue);
        TextView straightPercValue = (TextView) findViewById(R.id.straightPercentageValue);
        TextView lobPercValue = (TextView) findViewById(R.id.lobPercentageValue);
        TextView killPercValue = (TextView) findViewById(R.id.killPercentageValue);
        TextView dropPercValue = (TextView) findViewById(R.id.dropPercentageValue);

        TextView errorBoastPercValue = (TextView) findViewById(R.id.errorBoastPercentageValue);
        TextView errorStraightPercValue = (TextView) findViewById(R.id.errorStraightPercentageValue);
        TextView errorLobPercValue = (TextView) findViewById(R.id.errorLobPercentageValue);
        TextView errorKillPercValue = (TextView) findViewById(R.id.errorKillPercentageValue);
        TextView errorDropPercValue = (TextView) findViewById(R.id.errorDropPercentageValue);
        TextView volleysData = (TextView) findViewById(R.id.volleyOppsText);


        //Recieve all calculations necessary
        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("information");
        rallyLength = (int) bundle.getSerializable("rallyLength");
        totalRallies = (int) bundle.getSerializable("numRallies");
        avgRallyLength = (double) bundle.getSerializable("avgRallyLength");
        //shotsPerSecond = (float) bundle.getSerializable("shotsPerSecond");
        winnerError = (float) bundle.getSerializable("winnerError");
        currMatch = (Match) bundle.getSerializable("matchObject");
        currGame = (Game) bundle.getSerializable("gameObject");
        sps = (long) bundle.getSerializable("shotsPerSecond");
        winnerErrorData = (HashMap[]) bundle.getSerializable("winnersErrors");
        //volleyChances = (int) bundle.getSerializable("volleyChances");
        //volleysHit = (int) bundle.getSerializable("volleysHit");

        winnerData = winnerErrorData[0];
        errorData = winnerErrorData[1];

        totalWinners = currGame.getTotalWinners();
        totalErrors = currGame.getTotalErrors();

        if (totalWinners == 0) {
            boastPercValue.setText("----");
            straightPercValue.setText("----");
            dropPercValue.setText("----");
            killPercValue.setText("----");
            lobPercValue.setText("----");
        } else {

            boastPercValue.setText(String.valueOf(Math.round((((double) winnerData.get("boast")) * 100 / totalWinners)) + "%"));
            straightPercValue.setText(String.valueOf(Math.round((((double) winnerData.get("straight")) * 100 / totalWinners)) + "%"));
            dropPercValue.setText(String.valueOf(Math.round(((double) winnerData.get("drop")) * 100 / totalWinners)) + "%");
            killPercValue.setText(String.valueOf(Math.round(((double) winnerData.get("kill")) * 100 / totalWinners)) + "%");
            lobPercValue.setText(String.valueOf(Math.round(((double) winnerData.get("lob")) * 100 / totalWinners)) + "%");
        }
        if (totalErrors == 0) {
            errorBoastPercValue.setText("----");
            errorStraightPercValue.setText("----");
            errorLobPercValue.setText("----");
            errorKillPercValue.setText("----");
            errorDropPercValue.setText("----");
        } else {
            errorBoastPercValue.setText(String.valueOf(Math.round(((double) errorData.get("boast")) * 100 / totalErrors)) + "%");
            errorStraightPercValue.setText(String.valueOf(Math.round(((double) errorData.get("straight")) * 100 / totalErrors)) + "%");
            errorLobPercValue.setText(String.valueOf(Math.round(((double) errorData.get("lob")) * 100 / totalErrors)) + "%");
            errorKillPercValue.setText(String.valueOf(Math.round(((double) errorData.get("kill")) * 100 / totalErrors)) + "%");
            errorDropPercValue.setText(String.valueOf(Math.round(((double) errorData.get("drop")) * 100 / totalErrors)) + "%");
        }


        //Update view with new values
        shotsPerSecondValue.setText(String.valueOf(sps));
        totalShotsValue.setText(String.valueOf(rallyLength));
        totalRalliesValue.setText(String.valueOf(totalRallies));
        avgRallyLengthValue.setText(String.valueOf(avgRallyLength) + " Seconds");
        if (winnerError == 10000){
            winnerErrorValue.setText("-----");

        } else {
            winnerErrorValue.setText(String.valueOf(winnerError));

        }
        frontPercValue.setText(String.valueOf((double)bundle.getSerializable("frontPercentage")) + "%");
        midPercValue.setText(String.valueOf((double)bundle.getSerializable("middlePercentage")) + "%");
        backPercValue.setText(String.valueOf((double)bundle.getSerializable("backPercentage")) + "%");
        volleysData.setText(currGame.calculateGameVolleys()[1]+"/"+currGame.calculateGameVolleys()[0]);



        //Update the model with the new values to send to firebase
        firebaseData.setTotalShots(totalShotsValue.getText().toString());
        firebaseData.setAverageRallyLength(avgRallyLengthValue.getText().toString());
        firebaseData.setTotalRallies(totalRalliesValue.getText().toString());

        firebaseData.setPercentageOfBackShots(backPercValue.getText().toString());
        firebaseData.setPercentageOfMiddleShots(midPercValue.getText().toString());
        firebaseData.setPercentageOfFrontShots(frontPercValue.getText().toString());
        firebaseData.setTotalShots(String.valueOf(currGame.calculateTotalShots()));

        firebaseData.setPercentageOfBoastWinners(boastPercValue.getText().toString());
        firebaseData.setPercentageOfKillWinners(killPercValue.getText().toString());
        firebaseData.setPercentageOfLobWinners(lobPercValue.getText().toString());
        firebaseData.setPercentageOfStraightWinners(straightPercValue.getText().toString());
        firebaseData.setPercentageOfDropWinners(dropPercValue.getText().toString());

        firebaseData.setPercentageOfBoastWinners(boastPercValue.getText().toString());
        firebaseData.setPercentageOfKillWinners(killPercValue.getText().toString());
        firebaseData.setPercentageOfLobWinners(lobPercValue.getText().toString());
        firebaseData.setPercentageOfStraightWinners(straightPercValue.getText().toString());
        firebaseData.setPercentageOfDropWinners(dropPercValue.getText().toString());

        firebaseData.setPercentageOfBoastErrors(errorBoastPercValue.getText().toString());
        firebaseData.setPercentageOfKillErrors(errorKillPercValue.getText().toString());
        firebaseData.setPercentageOfLobErrors(errorLobPercValue.getText().toString());
        firebaseData.setPercentageOfStraightErrors(errorStraightPercValue.getText().toString());
        firebaseData.setPercentageOfDropErrors(errorDropPercValue.getText().toString());

        firebaseData.setWinnersToErrors(winnerErrorValue.getText().toString());


        //currGame.set

        /*currMatch.setAverageRallyLength(avgRallyLength);
        currMatch.setBackShotsPercentage((double)bundle.getSerializable("backPercentage"));
        currMatch.setMiddleShotsPercentage((double)bundle.getSerializable("middlePercentage"));
        currMatch.setBackShotsPercentage((double)bundle.getSerializable("frontPercentage"));
        currMatch.setTotalShots(rallyLength);
        currMatch.setTotalRallies(totalRallies);*/

    }

    public void returnBack(View view) {
        intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("gameObject", (Serializable)currGame);
        bundle.putSerializable("firebaseObject", (Serializable) firebaseData);
        intent.putExtra("Updated Match", bundle);
        setResult(2, intent);
        finish();


    }


}
