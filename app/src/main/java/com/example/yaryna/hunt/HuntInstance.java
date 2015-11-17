package com.example.yaryna.hunt;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 15038588
 */
public class HuntInstance implements Serializable {
    private String  name;
    private String  creator;
    /**my_hunt specifies if current user is the creator of the hunt*/
    private boolean my_hunt = false;
    private ArrayList<Location> allHuntLocations = null;

    public HuntInstance(String name, String creator){
        this.name    = name;
        this.creator = creator;
    }
    public void    setMyHunt(boolean i){this.my_hunt = i;}
    public void    setName(String s){this.name = s;}
    public String  getCreator(){return creator;}
    public boolean isMyHunt(){return my_hunt;}
    public String  getName(){return name;}

    public void setAllHuntLocations(ArrayList<Location> locations){this.allHuntLocations = locations;}
    public ArrayList<Location> getAllHuntLocations(){return this.allHuntLocations;}
    public String toString(){
        String result =" ";
        if(Username.getInstance().getUsername().equalsIgnoreCase(creator))
            result = "Hunt: "+ this.name + " " +"  ---> MY HUNT!";
        else
         result = "Hunt: "+ this.name + " " +" by creator: " + this.creator;
        return result;
    }
}
