package com.example.yaryna.hunt;

/**
 * Created by Yaryna on 11/11/2015.
 */
public class Username {
    //In the beginning user is Guest
    String username = "Guest";

    public void changeUsername(String newUsername){this.username = newUsername;}
    public String getUsername(){return this.username;}

    static private final Username INSTANCE = new Username();
    public static Username getInstance(){
        return INSTANCE;
    }

    public Boolean IsValidUsername(String username){
        if(username.length() == 0) { return false; }    // Username is too short
        if(username.length() > 50) { return false; }    // Username is too long
        return true;
    }
}
