package com.example.yaryna.hunt;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by 15038588
 */
public class PostSubmitReachLocationRequest extends AsyncTask {

    GameDialog gameDialog;
    String huntName;

    String urlPost;
    String BASE_URL = "http://sots.brookes.ac.uk/~p0073862/services/hunt";
    //info to post
    String locationName;
    String dateTime;

    public PostSubmitReachLocationRequest(GameDialog g,String huntName,String locationName){
        this.gameDialog = g;
        this.huntName = huntName;
        this.locationName = locationName;
        dateTime  = DateFormat.getDateTimeInstance().format(new Date());

        urlPost  = BASE_URL+"/reachlocation";

    }

    private byte[] makePostVariables(){
        String urlParameters  = "huntname="+huntName
                +"&username="+Username.getInstance().getUsername()
                +"&locationname="
                +locationName
                +"&date="+dateTime;
        return urlParameters.getBytes(StandardCharsets.UTF_8);
    }


    @Override
    protected void onPostExecute(Object o) {
        gameDialog.postRequestComplete(o);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return request(makePostVariables());
    }

    //Request post
    protected Object request(byte[] data ){
        boolean success = false;

        HttpURLConnection connection = null;

        int    dataLength = data.length;

        try {
            URL url = new URL(urlPost); // Create URL object from endpoint
            connection= (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(dataLength));
            connection.setUseCaches(false);
            DataOutputStream write = new DataOutputStream(connection.getOutputStream());
            write.write(data);
            int i = connection.getResponseCode();
            if(i == 200)
                success = true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.disconnect();
        }

        if(success == false)
            System.out.print("GOT  ERROR ON POST REQUEST - USER REACHED LOCATION");


        return success;
    }
}
