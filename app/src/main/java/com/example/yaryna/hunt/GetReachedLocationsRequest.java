package com.example.yaryna.hunt;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 15038588
 */
public class GetReachedLocationsRequest extends AsyncTask {

    //Base Uniform Resource Locator for request
    String BASE_URL ="http://sots.brookes.ac.uk/~p0073862/services/hunt";
    //Uniform Resource Locator for list of reached locations by user
    String urlReachedLoc;
    //Current hunt to  get reached  locatios by user for
    HuntInstance hunt;
    ViewHuntsDetails to_update;

    /**
     * Constructor
     */
    public GetReachedLocationsRequest(ViewHuntsDetails to_update, HuntInstance hunt){
        this.hunt = hunt;
        this.to_update = to_update;
        this.urlReachedLoc = BASE_URL+"/reached/" + hunt.getName() +"/" + Username.getInstance().getUsername();
    }


    /**Background thread for making a request*/
    @Override
    protected Object doInBackground(Object[] params) {
        request("location",urlReachedLoc);
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        //after request end update the list of current locations
        //reached by a user
        to_update.updateReachedLocations(o);
    }

    /**The actual request*/
    protected Object request(String tagName, String urlReached){
        ArrayList<Object> result = new ArrayList<>();
        try {

            URL url = new URL(urlReached);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), null);
            while (!isEndDoc(parser)) {
                if (isStartTag(parser, tagName)) {
                    String newResult = getItem(parser);
                    if(!newResult.isEmpty() && newResult!=null)
                        result.add(getItem(parser));
                }
                parser.next();
            }
        }
        catch (XmlPullParserException e) {
            System.out.println(" CUSTOM ERROR MESSAGE :: Parser failure");
        }
        catch (IOException e) {
            System.out.println(" CUSTOM ERROR MESSAGE :: IOException while getting hunt reached locations\"");
        }

        //TODO: Delete test system out println after method is tested
        /**TESTING IF LOCATIONS WERE GOT***/
        //Returns array of strings with location names
        System.out.println("TEST FOR GOT REACHED BY USER LOCATIONS:");
        if(result.isEmpty() == false)
            for (Object o : result) {
                String s = (String) o;
                System.out.println(s);
            }
        else
            System.out.println("END OF TEST FOR GOT LOCATIONS: NO LOCATIONS FOUND");

        return result;
    }


    /**Get locatiomn details from XML tag*/
    private String getItem(XmlPullParser parser)throws XmlPullParserException, IOException {
        String locationName = "";
        while (!isEndTag(parser, "location") && !isEndDoc(parser)) {
            if (isStartTag(parser, "name")) {
                System.out.print("TEST : locationName from getItem:: " +locationName);
                locationName = getTagText(parser, "name");
            }
            parser.next();
        }
        if (isEndTag(parser, "location")) {
            return locationName;
        } else {
            return null;
        }

    }

    protected boolean isEndDoc(XmlPullParser parser)
            throws XmlPullParserException {
        return parser.getEventType() == XmlPullParser.END_DOCUMENT;
    }

    protected String getTagText(XmlPullParser parser, String name)
            throws XmlPullParserException, IOException {
        String result = null;
        while (!isEndTag(parser, name) && !isEndDoc(parser)) {
            if (parser.getEventType() == XmlPullParser.TEXT) {
                result = parser.getText();
            }
            parser.next();
        }
        return result;
    }


    protected boolean isEndTag(XmlPullParser parser, String name)
            throws XmlPullParserException {
        return (parser.getEventType() == XmlPullParser.END_TAG)
                && parser.getName().equals(name);

    }

    protected boolean isStartTag(XmlPullParser parser, String name)
            throws XmlPullParserException {
        return (parser.getEventType() == XmlPullParser.START_TAG)
                && parser.getName().equalsIgnoreCase(name);
    }



}
