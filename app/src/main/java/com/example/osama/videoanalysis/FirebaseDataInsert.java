package com.example.osama.videoanalysis;

import android.app.AppComponentFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDataInsert extends AppCompatActivity {
    DatabaseReference reff;
    Match match;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //After extracting all the attribiutes you have, use reff.push in
        //order to store all your stuff as a nested tree

    }


}
