package com.example.osama.videoanalysis;

import java.io.Serializable;

public class Shot implements Serializable{
        public String specificID = "";
        private Boolean volleyOpportunity = false;
        private Boolean volley = false;
        public String shotType;
        private Boolean serve = false;
        private String shotArea = "";
        private Rally parentRally;

        public boolean getVolleyOpportunity(){
            return volleyOpportunity;

        }

        public boolean getVolley(){
            return volley;


        }

        public void setVolleyOpportunity(){
            volleyOpportunity = true;

        }

        public void setVolley(){
            volley = true;

        }

        public String getSpecificID() {
            return specificID;
        }

        public String getShotType(){

            return shotType;
        }

        public void setShotType(String type){
            shotType = type;


        }

        public Boolean isServe(){
            return serve;


        }

        public void setServeTrue(){
            serve = true;

        }

        public void shotAreaRecord(String s){
            shotArea = s;


        }

        public void setParentRally(Rally r){
            parentRally = r;


        }

        public void parentLetStroke(){
            parentRally.letstroke = true;


        }

        public String getShotArea(){
            return shotArea;


        }


}



