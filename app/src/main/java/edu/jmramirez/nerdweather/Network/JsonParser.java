package edu.jmramirez.nerdweather.Network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.jmramirez.nerdweather.Model.NWStation;
import edu.jmramirez.nerdweather.Model.NWStationDetails;

/**
 *
 * JSON parser class. Parses incoming data from donkikochan.uab.cat web service
 *
 */
public class JsonParser {

    private static final String DATA_JSON_ARRAY = "weatherObservations";
    private static final String DATA_JSON_DETAIL = "weatherObservation";
    private static final String TAG_STATION_NAME = "stationName";
    private static final String TAG_ICAO = "ICAO";
    private static final String TAG_TEMPERATURE = "temperature";


    private String jsonString;

    public JsonParser() {  }

    public JsonParser(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    /**
     * Parses the JSON String stored in the local variable jsonString.
     *
     * @return A human readable String obtained from the JSON String.
     *
     * @throws JSONException
     */
    public List parseStation() throws JSONException {

//        StringBuilder stringBuilder = new StringBuilder();

        List <NWStation> stationsList = new ArrayList<NWStation>();

        // Create a new instance of the JSONTokener object
        JSONTokener jsonTokener = new JSONTokener(this.jsonString);

        // Get the root element of the parsing JSON
        JSONObject rootObject = (JSONObject) jsonTokener.nextValue();

        // Extract the "data" JSONArray from the response JSONObject
        JSONArray dataArray = rootObject.getJSONArray(DATA_JSON_ARRAY);

        // Extract the JSON objects contained in the "data" array
        for( int i = 0; i < dataArray.length(); i++ ) {

            JSONObject dataObject = dataArray.getJSONObject(i);

            // Get the "id" values of the current JSON Object
            // Note that such instruction is valid only if the JSONObject contains such a ke

            stationsList.add(new NWStation(
                    dataObject.getString(TAG_STATION_NAME),
                    dataObject.getString(TAG_ICAO),
                    dataObject.getString(TAG_TEMPERATURE)));

        }
        return stationsList;
    }

    public List parseDetails() throws JSONException {

        List <NWStationDetails> stationDetails = new ArrayList<NWStationDetails>();

        JSONObject dataObject = new JSONObject(jsonString);
        JSONObject newJSON = dataObject.getJSONObject(DATA_JSON_DETAIL);
        dataObject = new JSONObject(newJSON.toString());

        stationDetails.add(new NWStationDetails(
                dataObject.getString("weatherCondition"),
                dataObject.getString("clouds"),
                dataObject.getString("windDirection"),
                dataObject.getString("ICAO"),
                dataObject.getString("elevation"),
                dataObject.getString("countryCode"),
                dataObject.getString("lng"),
                dataObject.getString("temperature"),
                dataObject.getString("dewPoint"),
                dataObject.getString("windSpeed"),
                dataObject.getString("humidity"),
                dataObject.getString("stationName"),
                dataObject.getString("datetime"),
                dataObject.getString("lat")));

        return stationDetails;
    }
}