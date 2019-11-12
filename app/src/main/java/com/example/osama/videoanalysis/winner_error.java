package com.example.osama.videoanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.Serializable;

public class winner_error extends AppCompatActivity {
    MainActivity.Shot currWinnerError;
    String shotChoice;
    Intent intent;
    public void onChoice(View view){

        if (view.getTag().toString().equals("boast")){
            shotChoice = "boast";

        } else if (view.getTag().toString().equals("kill")){
            shotChoice = "kill";

        } else if (view.getTag().toString().equals("lob")){
            shotChoice = "lob";

        } else if (view.getTag().toString().equals("straight")){
            shotChoice = "straight";

        } else if (view.getTag().toString().equals("drop")){
            shotChoice = "drop";

        }





    }

    public void onConfirmation(View view){
        currWinnerError.setShotType(shotChoice);
        setResult(RESULT_OK, intent);
        finish();



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_error);
        intent = getIntent();
        String winner_error = (String)intent.getSerializableExtra("Winner/Error");
        //Do this or just concatenate winner type with shotType in class
        if (winner_error == "winner"){
            currWinnerError = (MainActivity.Winner)intent.getSerializableExtra("shotObject");


        } else {
            currWinnerError = (MainActivity.Error)intent.getSerializableExtra("shotObject");

        }


    }
}
