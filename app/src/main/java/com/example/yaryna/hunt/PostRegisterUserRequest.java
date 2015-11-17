package com.example.yaryna.hunt;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Created by Y15038588
 */
public class PostRegisterUserRequest extends AsyncTask {

    //Updatable to_update; //fragment to update after registering

    HuntInstance hunt;   //hunt on which user will be registered
    String username;     //user to be registered

    String BASE_URL = "http://sots.brookes.ac.uk/~p0073862/services/hunt";
    String URLForRegister;
    byte[] dataToPost;
    ViewHuntsDetails to_update;


    /**Constructor to get all values for user register
     * @param to_update specofies fragment which will get updated
     *                  when post method finish
     * @param huntName  current hunt(HuntInstance object) user will be registered on
     * @param username  user to be registered */
    public  PostRegisterUserRequest(byte[] b,ViewHuntsDetails to_update,HuntInstance huntName,String username){
        this.to_update = to_update;
        this.hunt = huntName;
        this.username = username;
        this.dataToPost = b;

        URLForRegister = BASE_URL+"/starthunt";
    }

    @Override
    protected void onPostExecute(Object o) {
        to_update.UpdateAfterRegister(o);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return  PostRegisterRequest(URLForRegister,dataToPost);
    }



    /**Returns the response message returned by the remote HTTP server.
     Returns the response message. null if no such response exists.
     Throws IOException	if there is an error during the retrieval.
     */
    private Object PostRegisterRequest(String urlForRegister,byte[] dataToSend ) {
        HttpURLConnection connection = null;
        String result = null;
        int dataToSendSize = dataToSend.length;
        try {
            URL url = new URL(urlForRegister);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(dataToSendSize));
            connection.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream( connection.getOutputStream());
            wr.write(dataToSend);
            result = connection.getResponseMessage();
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.disconnect();
        }

        //TODO: FOR TESTING PURPOSE _ REMOVE LATED
        if(result == null)
            System.out.print("GOT NUULL ERROR MESSAGE FROM POST");

        return result;
    }
}
