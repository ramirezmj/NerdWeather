package edu.jmramirez.nerdweather.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.jmramirez.nerdweather.Network.JsonParser;
import edu.jmramirez.nerdweather.Model.NWStation;
import edu.jmramirez.nerdweather.Model.NWStationAdapter;
import edu.jmramirez.nerdweather.R;

public class NWMainActivity extends AppCompatActivity {

    private final static String TAG = "NWMainActivity";
    private final static String INTENT = "ICAO";

    private String DEFAULT_NORTH = "44.1";
    private String DEFAULT_SOUTH = "-9.9";
    private String DEFAULT_EAST = "-22.4";
    private String DEFAULT_WEST = "55.2";

    private String WS_NORTH;
    private String WS_SOUTH;
    private String WS_EAST;
    private String WS_WEST;

    ListView listView;
    NWStationAdapter adapter;
    List <NWStation> jsonResponse;

    Double minTemp;
    String minTempStationName;

    //URL to get JSON Array
    private String WEBSERVICE_URL = "http://api.geonames.org/weatherJSON?formatted=true"
            +"&north="+WS_NORTH
            +"&south="+WS_SOUTH
            +"&east="+WS_EAST
            +"&west="+WS_WEST
            +"&username=ramirezmj&style=full";

    // UI elements
    EditText northET, southET, eastET, westET;
    Button loadButton;

    // Handler to handle the connection to the web service
    Handler 	loadDataHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nwmain);
        getUI();
        checkPositionValues();
        loadDataCall();

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPositionValues();
                loadDataCall();
            }
        });
    }

    private void getUI() {
        // Reference to the UI elements
        northET = (EditText) findViewById(R.id.northEditText);
        southET = (EditText) findViewById(R.id.southEditText);
        eastET  = (EditText) findViewById(R.id.eastEditText);
        westET  = (EditText) findViewById(R.id.westEditText);
        loadButton = (Button) findViewById(R.id.loadButton);
        listView = (ListView) findViewById(R.id.weatherListView);
    }

    private void checkPositionValues() {
        String textNorthET = northET.getText().toString();
        if (textNorthET.length() > 0) {
            WS_NORTH = textNorthET;
        } else {
            WS_NORTH = DEFAULT_NORTH;
        }

        String textsouthET = southET.getText().toString();
        if (textsouthET.length() > 0) {
            WS_SOUTH = textsouthET ;
        } else {
            WS_SOUTH = DEFAULT_SOUTH;
        }

        String texteastET = eastET.getText().toString();
        if (texteastET.length() > 0) {
            WS_EAST = texteastET;
        } else {
            WS_EAST = DEFAULT_EAST;
        }

        String textwestET = westET.getText().toString();
        if (textwestET.length() > 0) {
            WS_WEST = textwestET;
        } else {
            WS_WEST = DEFAULT_WEST;
        }
    }

    private void loadDataCall() {

        WEBSERVICE_URL = "http://api.geonames.org/weatherJSON?formatted=true"
                +"&north="+WS_NORTH
                +"&south="+WS_SOUTH
                +"&east="+WS_EAST
                +"&west="+WS_WEST
                +"&username=ramirezmj&style=full";

        new Thread(new LoadDataFromWebService(loadDataHandler, WEBSERVICE_URL)).start();
    }

    /**
     * Connects to a web service and retrieves the specified data.
     * Uses a Handler to handle back the response to the UI Thread.
     */
    private class LoadDataFromWebService implements Runnable {

        // UI Thread handler
        Handler handler;

        // URL of the web service
        String	url;

        public LoadDataFromWebService(Handler handler, String url) {
            this.handler = handler;
            this.url = url;
        }

        @Override
        public void run() {
            HttpURLConnection httpUrlConnection = null;

            // Set the ProgressBar to be visible
            this.handler.post(new Runnable() {

                @Override public void run() {
//                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            try {
                // Connect to the web service
                httpUrlConnection = (HttpURLConnection)
                        new URL(url).openConnection();

                // Get a reference to the web service's InputStream
                InputStream in = httpUrlConnection.getInputStream();

                // Read the raw data from the InputStream
                final String webserviceResponse = readFromInputStream(in);

                // Parse the JSON data response from the web service
                jsonResponse = new JsonParser(webserviceResponse).parseStation();

                getMinimumTemperature(jsonResponse);

                adapter = new NWStationAdapter(NWMainActivity.this, jsonResponse);

                // Hide the ProgressBar from the View hierarchy and show the
                // web service's response in the TextView
                this.handler.post(new Runnable() {

                    @Override public void run() {
//                        progressBar.setVisibility(View.GONE);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                String weatherID = String.valueOf(jsonResponse.get(position).getIcao());

//                                Toast.makeText(NWMainActivity.this, weatherID, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(NWMainActivity.this, NWDetailActivity.class);
                                intent.putExtra(INTENT, weatherID);
                                startActivity(intent);
                            }
                        });
                    }
                });

            } catch (MalformedURLException e) {
                Log.e("LOAD_DATA_FROM_WEBSERVICE", "MalformedURLException");

            } catch (IOException e) {
                Log.e("LOAD_DATA_FROM_WEBSERVICE", "IOException");

            } catch (JSONException e) {
                Log.e("LOAD_DATA_FROM_WEBSERVICE", "JSONException");
            }
            finally {
                if ( httpUrlConnection != null )
                    httpUrlConnection.disconnect();
            }
        }

        /**
         * Reads raw data from an InputStream.
         *
         * @param in The InputStream to read from.
         *
         * @return A String representation of the InputStream's data
         */
        private String readFromInputStream(InputStream in) {

            BufferedReader bufferedReader = null;
            StringBuilder responseBuilder = new StringBuilder();

            try {

                bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";

                while ( (line = bufferedReader.readLine()) != null ) {
                    responseBuilder.append(line);
                }

            } catch (IOException e) {
                Log.e("LOAD_DATA_FROM_WEBSERVICE", "IOException");
            }
            finally {

                if ( bufferedReader != null ) {

                    try { bufferedReader.close();
                    } catch (IOException e) {
                        Log.e("LOAD_DATA_FROM_WEBSERVICE", "IOException");
                    }
                }
            }

            return responseBuilder.toString();
        }
    }

    private void getMinimumTemperature(List<NWStation> stations) {

        minTemp = 100.0;
        String station = "";
        double temp;
        for(int i=0; i<stations.size(); i++){
            if(stations.get(i).getTemperature() != null && !stations.get(i).getTemperature().isEmpty()) {
                temp = Double.parseDouble(stations.get(i).getTemperature());
            }else {
                temp = 100;
            }
            if(temp <= minTemp){
                minTemp = temp;
                minTempStationName = stations.get(i).getStationName();
            }
        }
        Log.d("minimum temp: ", String.valueOf(minTemp));
        Log.d("station: ", minTempStationName);


        // Notification
        Intent intent = new Intent(NWMainActivity.this, NWMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(NWMainActivity.this, 0,
                intent, Intent.FLAG_ACTIVITY_NEW_TASK);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(NWMainActivity.this.getApplicationContext());

        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("Freezing Notification");
        builder.setContentText("In "+minTempStationName+" is currently "+minTemp+"ºC");
        builder.setContentIntent(pendingIntent);

        Notification newSessionNotification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) NWMainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, newSessionNotification);

    }

    /** SETTINGS MENU **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                Intent intent = new Intent(NWMainActivity.this, NWSettingsActivity.class);
                startActivity(intent);
                return true;
        }
    }

    /** END SETTINGS MENU **/
}
