package com.example.yaryna.hunt;

/**
 * Created by Yaryna on 01/11/2015.
 */
public class HuntInstance {
    private String  name;
    private String  creator;
    private boolean my_hunt = false;

    public HuntInstance(String name, String creator){
        this.name    = name;
        this.creator = creator;
    }

    public void    setCreator(String creator){this.creator = creator;}
    public void    setMyHunt(boolean i){this.my_hunt = i;}
    public void    setName(String s){this.name = s;}
    public String  getCreator(){return creator;}
    public boolean isMyHunt(){return my_hunt;}
    public String  getName(){return name;}

    public String toString(){
        return new String (
                "Hunt: "+ this.name + " " +" by creator: " + this.creator);
    }
}
