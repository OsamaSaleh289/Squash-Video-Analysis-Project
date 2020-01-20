package com.example.osama.videoanalysis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.Serializable;

public class ShotDetails extends AppCompatActivity implements Serializable{
    Button shotType;

    Intent intent;
    Shot currShot;
    Rally currRally;
    MaterialBetterSpinner winnerSpinner;
    MaterialBetterSpinner errorSpinner;
    String winnerErrorChoice = "";
    Button confirm;



    public void onTypeSelect(View view){
        confirm = (Button) findViewById(R.id.confirm);
        if (!view.getTag().toString().equals("cancel")) {
            confirm.setClickable(true);
            confirm.setAlpha((float) 1);
        }




        if (view.getTag().toString().equals("let/stroke")){
            shotType.setBackground(Drawable.createFromPath("@android:color/holo_orange_dark"));
            shotType = (Button) findViewById(R.id.let_stroke);
            shotType.setBackground(Drawable.createFromPath("@android:color/pink"));
            currShot.setShotType(shotType.getText().toString());



        } else if (view.getTag().toString().equals("normalShot")){
            //shotType.setBackground(Drawable.createFromPath("@android:color/holo_orange_dark"));
            shotType = (Button) findViewById(R.id.normalShot);
            //shotType.setBackground(Drawable.createFromPath("@android:color/pink"));
            currShot.setShotType("Normal Shot");


        //Cancel Button
        } else if (view.getTag().toString().equals("cancel")) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(4, intent);
            finish();


        }



        if (shotType.getTag().toString().equals("let/stroke")){
            currShot.parentLetStroke();


        }
        winnerSpinner.setText("");
        errorSpinner.setText("");









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

    public void onConfirmClick(View view) {
        //We need to only allow confirm click if we have a shot value

            /*if (shotType.getTag().toString().equals("winner")) {
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


            Log.i("Shot choice ", currShot.getShotType());
            currRally.addShot(currShot);
            endActivity();



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_details);
        confirm = (Button) findViewById(R.id.confirm);

        String [] winnerErrorDetails = {"lob", "straight", "drop", "kill", "boast"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, winnerErrorDetails);
        winnerSpinner = (MaterialBetterSpinner) findViewById(R.id.winner_spinner);
        winnerSpinner.setAdapter(arrayAdapter);
        winnerSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                confirm.setAlpha((float) 1);
                currShot.setShotType("Winner/"+parent.getItemAtPosition(position).toString());
                errorSpinner.setText("");
                confirm.setClickable(true);
            }
        });

        errorSpinner = (MaterialBetterSpinner) findViewById(R.id.error_spinner);
        errorSpinner.setAdapter(arrayAdapter);
        errorSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                confirm.setAlpha((float) 1);
                currShot.setShotType("Error/"+parent.getItemAtPosition(position).toString());
                winnerSpinner.setText("");
                confirm.setClickable(true);
            }

        });

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("information");
        currShot = (Shot)  bundle.get("shotObject");
        currRally = (Rally) bundle.get("rallyObject");
        if (currShot.isServe()){
            Button serveButton = (Button) findViewById(R.id.let_stroke);
            serveButton.setText("Volley Return");


        }



    }



    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i("End activity", "reached");
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle bundle = intent.getBundleExtra("choiceBundle");
        String choice = (String) bundle.getSerializable("choice");
        String id = currShot.getSpecificID();

        if (id.equals("Winner")) {
            currShot.setShotType("Winner/"+choice);
        } else{
            currShot.setShotType("Error/"+choice);

        }
        currRally.addShot(currShot);
        currShot.setParentRally(currRally);
        endActivity();


    }*/
}
