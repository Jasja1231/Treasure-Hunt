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
public class GetAllHuntLocationsRequest extends AsyncTask{

    private ViewHuntsDetails to_update; //current calling parent fragment
    private HuntInstance hunt;          //Current hunt to get locations for
    private String BASE_URL = "http://sots.brookes.ac.uk/~p0073862/services/hunt";
    private String urlForAllLocations;

    /**Constructor*/
    public GetAllHuntLocationsRequest(ViewHuntsDetails to_update,HuntInstance hunt){
        this.to_update = to_update;
        this.hunt = hunt;

        urlForAllLocations = BASE_URL+"/locations/"+hunt.getName();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return request("location",urlForAllLocations);
    }


    @Override
    protected void onPostExecute(Object o) {
        //update list of all hunt locations
        to_update.updateAllHuntLocations(o);
    }

    /**The actual request*/
    protected Object request(String tagName, String urlReached) {
        ArrayList<Location> result = new ArrayList<>();
        try {
            URL url = new URL(urlReached);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), null);
            while (!isEndDoc(parser)) {
                if (isStartTag(parser, tagName)) {
                    if (getItem(parser) != null)
                        result.add(getItem(parser));
                }
                parser.next();
            }
        } catch (XmlPullParserException e) {
            System.out.println(" CUSTOM ERROR MESSAGE :: Parser failure");
        } catch (IOException e) {
            System.out.println(" CUSTOM ERROR MESSAGE :: IOException while getting all hunt locations\"");
        }
        return result;
    }

    /**Get location details from XML*/
    private Location getItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        Location location = new Location();
        //TODO: Add check for empty values -- WHICH SHOULD NOT HAPPEN
        while (!isEndTag(parser, "location") && !isEndDoc(parser)){
            if (isStartTag(parser, "name")) {
                location.setName(getTagText(parser, "name"));
            }
            else if (isStartTag(parser, "position")) {
                location.setPosition(getTagText(parser, "position"));
            }
            else if (isStartTag(parser, "description")) {
                location.setDescription(getTagText(parser, "description"));
            }
            else if (isStartTag(parser, "latitude")) {
                location.setLatitude(getTagText(parser, "latitude"));
            }
            else if (isStartTag(parser, "longitude")) {
                location.setLongitude(getTagText(parser, "longitude"));
            }
            else if (isStartTag(parser, "question")) {
                location.setQuestion(getTagText(parser, "question"));
            }
            else if (isStartTag(parser, "answer")) {
                location.setAnswer(getTagText(parser, "answer"));
            }
            else if (isStartTag(parser, "clue")) {
                location.setClue(getTagText(parser, "clue"));
            }
            parser.next();
        }

        if (isEndTag(parser, "location"))
            return location;
        else
            return null;
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
