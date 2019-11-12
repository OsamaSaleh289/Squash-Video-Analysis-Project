package com.example.osama.videoanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import static java.lang.Integer.parseInt;

public class statistics_tracker extends AppCompatActivity {
    Intent intent;
    Button backButton;
    int rallyLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_tracker);

        backButton = (Button) findViewById(R.id.backButton);
        TextView totalShotsView = (TextView) findViewById(R.id.totalShotsValue);

        intent = getIntent();
        rallyLength = (int) intent.getSerializableExtra("rallyLength");
        totalShotsView.setText(rallyLength);



        /*TextView totalShots = (TextView) findViewById(R.id.totalShotsValue);
        intent = getIntent();
        int newRallyLength = (int) intent.getSerializableExtra("rallyLength") + parseInt(totalShots.getText().toString());
        totalShots.setText((newRallyLength + " shots"));*/

    }

    public void returnBack(View view) {
        setResult(RESULT_OK, intent);
        //Problem lies here that activity is destroyed
        finish();


    }


}
