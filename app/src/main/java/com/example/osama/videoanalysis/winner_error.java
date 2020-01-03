package com.example.osama.videoanalysis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.Serializable;

public class winner_error extends AppCompatActivity {
    Shot currWinnerError;
    String shotChoice;
    Intent intent;
    String winner_error;
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

        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        if (winner_error.equals("Winner")) {
            currWinnerError.setShotType("Winner/"+shotChoice);
        } else {
            currWinnerError.setShotType("Error/"+shotChoice);

        }
        bundle.putSerializable("type", currWinnerError);
        intent.putExtra("Updated information", bundle);
        setResult(RESULT_OK, intent);
        finish();

        /*currWinnerError.setShotType(shotChoice);
        setResult(RESULT_OK, intent);
        finish();*/



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_error);
        intent = getIntent();
        Log.i("reached", "vfibhegwrjf");
        winner_error = (String)intent.getSerializableExtra("Winner/Error");
        //Do this or just concatenate winner type with shotType in class
        if (winner_error.equals("winner")){
            currWinnerError = (Winner)intent.getSerializableExtra("shotObject");


        } else {
            currWinnerError = (Error)intent.getSerializableExtra("shotObject");

        }


    }
}
