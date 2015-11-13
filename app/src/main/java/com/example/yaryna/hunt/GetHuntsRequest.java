package com.example.yaryna.hunt;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Yaryna on 01/11/2015.
 */
public class GetHuntsRequest extends AsyncTask<URL,Void,ArrayList<HuntInstance>> {

    private final String BASE_URL ="http://sots.brookes.ac.uk/~p0073862/services/hunt";
    Updatable updatable_element;

    @Override
    protected void onPostExecute(ArrayList<HuntInstance> huntInstances) {
        updatable_element.newResultsToUpdate(huntInstances);
    }

    /**
     * Constructor
     */
    public GetHuntsRequest(Updatable to_Update){
        updatable_element = to_Update;
    }

    /**
     * The request is made as a background process.
     * Reads values needed to create HuntInstances.
     */
    @Override
    protected ArrayList<HuntInstance> doInBackground(URL... params) {
        ArrayList<HuntInstance> list_of_hunts = new ArrayList<>();
        try{
            URL url = new URL(BASE_URL + "/hunts");
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            parser.setInput(url.openStream(), null);
            while (!isEndDoc(parser)){
                if (isStartTag(parser, "hunt")) {
                    list_of_hunts.add(getHunt(parser));
                }
                parser.next();
            }
        }
        catch (XmlPullParserException ex){
            System.out.println("XML parser failure");
        }
        catch (IOException  ex){
            System.out.println("IO failure");
        }
        return list_of_hunts;
    }

    /**
     * Creates HuntInstance based on the xml  read data.
     * Trows XmlPullParserException, IOException.
     */
    private HuntInstance getHunt(XmlPullParser parser) throws XmlPullParserException, IOException {
        String huntName = "";
        String creator = "";

        while(!isEndTag(parser,"hunt") && !isEndDoc(parser)){
            if (isStartTag(parser, "name")) {
                huntName = getTagText(parser, "name");
            } else if (isStartTag(parser, "creator")) {
                creator = getTagText(parser, "creator");
            }
            parser.next();
        }
        if(isEndTag(parser,"hunt")){
            HuntInstance gotHunt = new HuntInstance(huntName,creator);
            if(Username.getInstance().getUsername().equalsIgnoreCase(creator)){
                gotHunt.setMyHunt(true);
            }
            return gotHunt;
        } else {
            return null;
        }
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
