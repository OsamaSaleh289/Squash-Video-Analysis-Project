package com.example.osama.videoanalysis;

import java.io.Serializable;

public class Winner extends Shot implements Serializable {
    public String specificID = "Winner";

    public String getSpecificID() {
        return specificID;
    }
}