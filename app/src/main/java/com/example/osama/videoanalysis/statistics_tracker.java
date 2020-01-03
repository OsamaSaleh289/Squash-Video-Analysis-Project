package com.example.osama.videoanalysis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

public class statistics_tracker extends AppCompatActivity {
    Intent intent;
    Button backButton;
    int rallyLength;
    int totalRallies;
    float avgRallyLength;
    float shotsPerSecond;
    float winnerError;
    public int testValue = 0;
    float frontShotsPerc = 0;
    float middleShotsPerc = 0;
    float backShotsPerc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_tracker);

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

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("information");
        rallyLength = (int) bundle.getSerializable("rallyLength");
        totalRallies = (int) bundle.getSerializable("numRallies");
        totalRallies = (int) bundle.getSerializable("numRallies");
        avgRallyLength = (float) bundle.getSerializable("avgRallyLength");
        //shotsPerSecond = (float) bundle.getSerializable("shotsPerSecond");
        winnerError = (float) bundle.getSerializable("winnerError");


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







        /*TextView totalShots = (TextView) findViewById(R.id.totalShotsValue);
        intent = getIntent();
        int newRallyLength = (int) intent.getSerializableExtra("rallyLength") + parseInt(totalShots.getText().toString());
        totalShots.setText((newRallyLength + " shots"));*/

    }

    public void returnBack(View view) {
        intent = new Intent(getApplicationContext(), MainActivity.class);
        setResult(2, intent);
        finish();


    }


}
