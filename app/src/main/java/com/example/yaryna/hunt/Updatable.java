package com.example.yaryna.hunt;

/**
 * Created by Yaryna on 01/11/2015.
 */
public interface Updatable {
   /**
    * Every function implementing this interface will have an aility to update
    * UI with new values.
    */
   void newResultsToUpdate(Object new_results);
}
