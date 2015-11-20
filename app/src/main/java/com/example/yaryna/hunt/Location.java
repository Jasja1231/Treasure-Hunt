package com.example.yaryna.hunt;

import java.util.Date;

/**
 * Created 15038588
 */
public class Location {
    private String name;
    private String description;
    private String position;
    private String clue;
    private String time;
    private String question;
    private String answer;
    private String latitude;
    private String longitude;
    private String date;

    public void setAnswer(String answer) {
        if(answer==null)
            answer = "NULL";
        this.answer = answer;
    }
    public void setClue(String clue) {
        if(clue==null)
            clue = "NULL";
        this.clue = clue;
    }
    public void setDate(String date){
        if(date == null)
            date = "NULL";
        this.date = date;
    }

    public void setDescription(String description){
        if(description == null)
            description = "creator named it NULL";
        this.description = description;}

    public void setLatitude(String latitude){
        if(latitude == null)
            latitude = "creator named it NULL";
        this.latitude = latitude;}

    public void setLongitude(String longitude){
        if(longitude == null)
            longitude = "creator named it NULL";
        this.longitude = longitude;}

    public void setName(String name){
        if(name == null)
            name = "creator named it NULL";
        this.name = name;}

    public void setQuestion(String question) {
        if(question == null)
            question = "creator named it NULL";
        this.question = question;}

    public void setTime(String time) {
        if(time == null)
            time = "creator named it NULL";
        this.time = time;}


    public String getAnswer(){return answer;}
    public String getClue(){return clue;}
    public String getDate(){return date;}
    public String getDescription(){return description;}
    public String getLatitude(){return latitude;}
    public String getLongitude(){return longitude;}
    public String getName(){return name;}
    public String getQuestion(){return question;}
    public String getTime(){return time;}

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if(position == null)
            position = "creator named it NULL";
        this.position = position;
    }

    public String toString(){
        return new String(
                "Location name:"   + name
         + " , Description: "     + description
         + " ,location latitude:" + latitude
         + " ,location longitude" + longitude );
    }
}
