package com.example.osama.videoanalysis;

import java.io.Serializable;

public class Shot implements Serializable{
        private Boolean volleyOpportunity;
        private Boolean volley;
        public String shotType;
        private Boolean serve = false;
        private String shotArea = "";
        private Rally parentRally;


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



