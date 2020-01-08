package com.example.osama.videoanalysis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class statistics_tracker extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_tracker);

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
        winnerData = winnerErrorData[0];
        errorData = winnerErrorData[1];

        totalWinners = currGame.getTotalWinners();
        totalErrors = currGame.getTotalErrors();

        boastPercValue.setText(String.valueOf(((double)winnerData.get("boast"))*100/totalWinners)+"%");
        straightPercValue.setText(String.valueOf(((double)winnerData.get("straight"))*100/totalWinners)+"%");
        dropPercValue.setText(String.valueOf(((double)winnerData.get("drop"))*100/totalWinners)+"%");
        killPercValue.setText(String.valueOf(((double)winnerData.get("kill"))*100/totalWinners)+"%");
        lobPercValue.setText(String.valueOf(((double)winnerData.get("lob"))*100/totalWinners)+"%");

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



        //Update the model with the new values to send to firebase
        currGame.setAverageRallyLength(avgRallyLength);
        //Issue : All of those are set to 0
        currGame.setBackShotsPercentage((double)bundle.getSerializable("backPercentage"));
        currGame.setMiddleShotsPercentage((double)bundle.getSerializable("middlePercentage"));
        currGame.setBackShotsPercentage((double)bundle.getSerializable("frontPercentage"));
        currGame.calculateTotalShots();
        currGame.setTotalRallies(totalRallies);
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
        bundle.putSerializable("gameObject", currGame);
        intent.putExtra("Updated Match", bundle);
        setResult(2, intent);
        finish();


    }


}
