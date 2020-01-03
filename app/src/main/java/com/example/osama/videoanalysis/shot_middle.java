package com.example.osama.videoanalysis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class shot_middle extends AppCompatActivity {

    Button shotType;

    Intent intent;
    Shot currShot;
    Rally currRally;

    public void onTypeSelect(View view){
        Button confirm = (Button) findViewById(R.id.confirm2);
        confirm.setAlpha((float) 1);



        if (view.getTag().toString().equals("errorShot")){
            shotType = (Button) findViewById(R.id.errorShot);


        } else if (view.getTag().toString().equals("volleyShot")){
            shotType = (Button) findViewById(R.id.volleyShot);



        } else if (view.getTag().toString().equals("floor")){
            shotType = (Button) findViewById(R.id.floorShot);


            //Cancel Button
        } else if (view.getTag().toString().equals("cancel")) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(RESULT_OK, intent);
            finish();


        } else{
            shotType = (Button) findViewById(R.id.winnerShot);


        }



        if (shotType.getTag().toString().equals("let/stroke")) {
            currShot.parentLetStroke();


        }

    }

    public void onConfirmClick(View view){
        if (shotType != null) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            currShot.setShotType(shotType.getText().toString());
            currRally.addShot(currShot);
            endActivity();
        }




    }

    public void endActivity(){

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("currShot", (Serializable) currShot);
        bundle.putSerializable("currRally", (Serializable) currRally);
        intent.putExtra("Updated information", bundle);
        setResult(RESULT_OK, intent);
        finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_middle_details);

        shotType = (Button) findViewById(R.id.winner);

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("information");
        currShot = (Shot)  bundle.get("shotObject");
        currRally = (Rally) bundle.get("rallyObject");
        if (currShot.isServe()){
            Button serveButton = (Button) findViewById(R.id.volleyShot);
            serveButton.setText("Volley Return");


        }

    }
}
