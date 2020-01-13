package com.example.osama.videoanalysis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.Serializable;

public class shot_middle extends AppCompatActivity {

    Button shotType;
    MaterialBetterSpinner winner_middle_spinner;
    MaterialBetterSpinner error_middle_spinner;
    Intent intent;
    Shot currShot;
    Rally currRally;
    Button confirm;
    String saveColor = "@android:color/holo_orange_dark";


    public void onTypeSelect(View view){

        Button confirm = (Button) findViewById(R.id.confirm2);
        if (!view.getTag().toString().equals("cancel")) {
            confirm.setClickable(true);
            confirm.setAlpha((float) 1);
        }


        if (view.getTag().toString().equals("volleyShot")){
            //shotType.setBackground(Drawable.createFromPath("@android:color/holo_orange_dark"));
            shotType = (Button) findViewById(R.id.volleyShot);
            //shotType.setBackground(Drawable.createFromPath("@android:color/pink"));
            currShot.setShotType(shotType.getText().toString());
            currShot.setVolley();



        } else if (view.getTag().toString().equals("floorShot")){
            //shotType.setBackground(Drawable.createFromPath("@android:color/holo_orange_dark"));
            shotType = (Button) findViewById(R.id.floorShot);
            //shotType.setBackground(Drawable.createFromPath("@android:color/pink"));
            currShot.setShotType(shotType.getText().toString());


            //Cancel Button
        } else if (view.getTag().toString().equals("cancel")) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(4, intent);
            finish();


        }



    }

    public void onConfirmClick(View view){
            intent = new Intent(getApplicationContext(), MainActivity.class);
            Log.i("currShot", String.valueOf(currShot.equals(null)));
            currRally.addShot(currShot);
            endActivity();





    }

    public void endActivity(){

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("currShot", (Serializable) currShot);
        bundle.putSerializable("currRally", (Serializable) currRally);
        intent.putExtra("Updated information", bundle);
        setResult(1, intent);
        finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_middle_details);

        confirm = (Button) findViewById(R.id.confirm2);

        String [] winnerErrorDetails = {"lob", "straight", "drop", "kill", "boast"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, winnerErrorDetails);
        winner_middle_spinner = (MaterialBetterSpinner) findViewById(R.id.winner_middle_spinner);
        winner_middle_spinner.setAdapter(arrayAdapter);
        winner_middle_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                confirm.setAlpha((float) 1);
                currShot.setShotType("Winner/"+parent.getItemAtPosition(position).toString());
                error_middle_spinner.setText("");
                confirm.setClickable(true);
            }
        });

        error_middle_spinner = (MaterialBetterSpinner) findViewById(R.id.error_middle_spinner);
        error_middle_spinner.setAdapter(arrayAdapter);
        error_middle_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                confirm.setAlpha((float) 1);
                currShot.setShotType("Error/"+parent.getItemAtPosition(position).toString());
                winner_middle_spinner.setText("");
                confirm.setClickable(true);
            }

        });


        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("information");
        currShot = (Shot)  bundle.getSerializable("shotObject");
        currRally = (Rally) bundle.getSerializable("rallyObject");
        if (currShot.isServe()){
            Button serveButton = (Button) findViewById(R.id.volleyShot);
            serveButton.setText("Volley Return");


        }

    }
}
