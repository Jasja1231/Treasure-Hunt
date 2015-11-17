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
    private Date date = new Date();

    public void setAnswer(String answer) {this.answer = answer;}
    public void setClue(String clue) {this.clue = clue;}
    public void setDate(Date date){this.date = date;}
    public void setDescription(String description){this.description = description;}
    public void setLatitude(String latitude){this.latitude = latitude;}
    public void setLongitude(String longitude){this.longitude = longitude;}
    public void setName(String name){this.name = name;}
    public void setQuestion(String question) {this.question = question;}
    public void setTime(String time) {this.time = time;}


    public String getAnswer(){return answer;}
    public String getClue(){return clue;}
    public Date getDate(){return date;}
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
