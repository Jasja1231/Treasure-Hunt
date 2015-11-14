package com.example.yaryna.hunt;

import android.os.AsyncTask;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Yaryna on 13/11/2015.
 */
public class GetRegisteredUsersOnHuntRequest extends AsyncTask<URL,Void,ArrayList<String>> {

    public ArrayList<String> testAnswer;
    public ArrayList<String> getS(){return testAnswer;}

    private final String BASE_URL ="http://sots.brookes.ac.uk/~p0073862/services/hunt";
    private String URLplayers;
    /**For update results of request*/
    Updatable updatable_element;//this would be a fragment we want to update with the results we have gotten
    HuntInstance hunt;//hunt we are getting registered users for


    public GetRegisteredUsersOnHuntRequest(Updatable to_update, HuntInstance hunt){
        this.updatable_element=to_update;
        this.hunt = hunt;

        //Get URL for a specific hunt players list
        URLplayers = BASE_URL + "/players/"+ hunt.getName();
    }



    @Override
    protected void onPostExecute(ArrayList<String> results) {
        //Update updatable element after get request is executed
        updatable_element.newResultsToUpdate(results);
    }

    /**Request will be made in background*/
    @Override
    protected ArrayList<String> doInBackground(URL... params) {
        ArrayList<String> resultUsernames = new ArrayList<>();
        try {
            URL url = new URL(URLplayers);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), null);
            while (!isEndDoc(parser)) {
                if (isStartTag(parser, "user")) {
                    ArrayList<String> temp = getItem(parser);
                    if(temp != null && temp.isEmpty()==false)
                        resultUsernames.addAll(temp);
                }
                parser.next();
            }

        } catch (XmlPullParserException e) {
            System.out.println( "Failed to create parser");
        } catch (IOException e) {
            System.out.println( "IOException occured");
        }

        return resultUsernames;
    }


    /**Get the element from the XML for a specific tag provided
     * returns list of usernames that are regestered in a hunt*/
    private ArrayList<String> getItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<String> registeredUsernames = new ArrayList<>();
        while(!isEndDoc(parser) && !isEndTag(parser,"user")){
            String username = getTagText(parser, "user");
            if(username != null)
                registeredUsernames.add(username);
            parser.next();
        }

        for (String s : registeredUsernames) {
            System.out.println("USERNAME : " + s);
        }
        return registeredUsernames;
    }


    /**
     * Get text content from the tag with specified name.
     */
    private String getTagText(XmlPullParser parser, String name)
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


    /**
     * Check if tag is a start tag.Trows XmlPullParserException.
     */
    private boolean isStartTag(XmlPullParser parser, String tag_name)throws XmlPullParserException {
        return (parser.getEventType() == XmlPullParser.START_TAG)
                && parser.getName().equalsIgnoreCase(tag_name);
    }

    /**
     * Check if tag is a end tag.Trows XmlPullParserException.
     */
    private boolean isEndTag(XmlPullParser parser, String name)
            throws XmlPullParserException {
        return (parser.getEventType() == XmlPullParser.END_TAG)
                && parser.getName().equals(name);
    }

    /**
     * Check if previous tag is at the end of the document.
     * Trows XmlPullParserException.
     */
    private boolean isEndDoc(XmlPullParser parser)  throws XmlPullParserException {
        return parser.getEventType() == XmlPullParser.END_DOCUMENT;
    }

}
