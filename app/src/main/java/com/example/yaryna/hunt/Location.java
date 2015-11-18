package com.example.yaryna.hunt;


/**
 * Created 15038588
 */
public class Location {
    private String name = "Default name";
    private String description = "Default description";
    private String position= "Default position";
    private String clue= "Default clue";
    private String time= "Default time";
    private String question= "Default question";
    private String answer= "Default answer";
    private String latitude= "Default latitude";
    private String longitude= "Default longitude";
    private String date= "Default date";

    public void setAnswer(String l) {
        if(l==null)
            l = "NULL";
        this.answer = l;
    }
    public void setClue(String l) {
        if(l==null)
            l = "NULL";
        this.clue = l;
    }
    public void setDate(String l){
        if(l == null)
            l = "NULL";
        this.date = l;
    }

    public void setDescription(String l){
        if(l == null)
            l = "creator named it NULL";
        this.description = l;}

    public void setLatitude(String l){
        if(l == null)
            l = "creator named it NULL";
        this.latitude = l;}

    public void setLongitude(String l){
        if(l == null)
            l = "creator named it NULL";
        this.longitude = l;}

    public void setName(String n){
        if(n == null)
            n = "creator named it NULL";
        this.name = n;}

    public void setQuestion(String q) {
        if(q == null)
            q = "creator named it NULL";
        this.question = q;}

    public void setTime(String t) {
        if(t == null)
            t = "creator named it NULL";
        this.time = t;}


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

    public void setPosition(String l) {
        if(l == null)
            l = "creator named it NULL";
        this.position = l;
    }

    public String toString(){
        return new String(
                "Location name:"   + name
         + " , Description: "     + description
         + " ,location latitude:" + latitude
         + " ,location longitude" + longitude );
    }
}
