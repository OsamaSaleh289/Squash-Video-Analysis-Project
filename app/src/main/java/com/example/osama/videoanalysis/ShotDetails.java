package com.example.osama.videoanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

public class ShotDetails extends AppCompatActivity implements Serializable{
    Button shotType;

    Intent intent;
    MainActivity.Shot currShot;
    MainActivity.Rally currRally;

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

    public void onConfirmClick(View view){
        //We need to only allow confirm click if we have a shot value
        if (shotType != null) {

            if (shotType.getTag().toString().equals("winner")) {
                //Change view to get more detailed info about shots
                MainActivity.Winner currShot = new MainActivity.Winner();

                Intent detailed_winner = new Intent(getApplicationContext(), winner_error.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Winner/Error", (Serializable) shotType.getTag());
                bundle.putSerializable("shotObject", (Serializable) currShot);
                intent.putExtras(bundle);
                startActivityForResult(detailed_winner, 3);


            } else if(shotType.getTag().toString().equals("error")){
                MainActivity.Error currShot = new MainActivity.Error();

                Intent detailed_winner = new Intent(getApplicationContext(), winner_error.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Winner/Error", (Serializable) shotType.getTag());
                bundle.putSerializable("shotObject", (Serializable) currShot);
                intent.putExtras(bundle);
                startActivityForResult(detailed_winner, 3);



            } else{
                currShot.setShotType(shotType.getText().toString());
                currRally.addShot(currShot);
                //Toast.makeText(getApplicationContext(), String.valueOf(currRally.getRallyLength()), Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                Log.i("id 2", String.valueOf(currRally.id));
                finish();

            }


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_details);
        shotType = (Button) findViewById(R.id.winner);

        intent = getIntent();
        currShot = (MainActivity.Shot)  intent.getSerializableExtra("shotObject");
        currRally = (MainActivity.Rally) intent.getSerializableExtra("rallyObject");
        if (currShot.isServe()){
            Button serveButton = (Button) findViewById(R.id.let_stroke);
            serveButton.setText("Volley Return");


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        currRally.addShot(currShot);
        currShot.setParentRally(currRally);
        setResult(RESULT_OK, intent);
        finish();


    }
}
