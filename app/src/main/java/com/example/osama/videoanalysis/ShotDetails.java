package com.example.osama.videoanalysis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class ShotDetails extends AppCompatActivity implements Serializable{
    Button shotType;

    Intent intent;
    Shot currShot;
    Rally currRally;

    public void onTypeSelect(View view){
        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setAlpha((float) 1);



        if (view.getTag().toString().equals("error")){
            shotType = (Button) findViewById(R.id.error);
            Log.i("tag", "true");


        } else if (view.getTag().toString().equals("let/stroke")){
            shotType = (Button) findViewById(R.id.let_stroke);



        } else if (view.getTag().toString().equals("normalShot")){
            shotType = (Button) findViewById(R.id.normalShot);


        //Cancel Button
        } else if (view.getTag().toString().equals("cancel")) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(RESULT_OK, intent);
            finish();


        } else{
            shotType = (Button) findViewById(R.id.winner);


        }



        if (shotType.getTag().toString().equals("let/stroke")){
            currShot.parentLetStroke();


        }

        Log.i("tag", shotType.getTag().toString());






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

    public void onConfirmClick(View view){
        //We need to only allow confirm click if we have a shot value
        /*if (shotType != null) {

            if (shotType.getTag().toString().equals("winner")) {
                //Change view to get more detailed info about shots
                Winner currShot = new Winner();

                Intent detailed_winner = new Intent(getApplicationContext(), winner_error.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Winner/Error", (Serializable) shotType.getTag());
                bundle.putSerializable("shotObject", (Serializable) currShot);
                intent.putExtras(bundle);
                startActivityForResult(detailed_winner, 3);


            } else if(shotType.getTag().toString().equals("error")){
                Error currShot = new Error();

                Intent detailed_winner = new Intent(getApplicationContext(), winner_error.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Winner/Error", (Serializable) shotType.getTag());
                //bundle.putSerializable("shotObject", (Serializable) currShot);
                intent.putExtras(bundle);
                startActivityForResult(detailed_winner, 3);



            } else*/
                currShot.setShotType(shotType.getText().toString());
                currRally.addShot(currShot);
                endActivity();


            }


        //}




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_details);
        shotType = (Button) findViewById(R.id.winner);

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("information");
        currShot = (Shot)  bundle.get("shotObject");
        currRally = (Rally) bundle.get("rallyObject");
        if (currShot.isServe()){
            Button serveButton = (Button) findViewById(R.id.let_stroke);
            serveButton.setText("Volley Return");


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Shot winnerErrorType = (Shot) intent.getSerializableExtra("type");


        currShot = winnerErrorType;
        currRally.addShot(currShot);
        currShot.setParentRally(currRally);
        endActivity();


    }
}
