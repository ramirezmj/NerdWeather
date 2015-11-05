package edu.jmramirez.nerdweather.Activities;


import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import edu.jmramirez.nerdweather.Model.NWStationDetails;
import edu.jmramirez.nerdweather.Network.JsonParser;
import edu.jmramirez.nerdweather.R;

public class NWDetailActivity extends AppCompatActivity {

    private final static String TAG = "NWDetailActivity";
    public final static String INTENT = "ICAO";
    boolean temperature, windSpeed, humidity;

    String temperatureCheckBox = "temperature_option";
    String windSpeedCheckBox = "wind_option";
    String humidityCheckBox = "humidity_option";

    private String mIcao;
    private String WEBSERVICE_URL;
    // Handler to handle the connection to the web service
    Handler loadDataHandler = new Handler();
    List <NWStationDetails> jsonResponse;

    // Labels
    TextView weatherConditionLabel, cloudsLabel, windDirectionLabel,
            icaoLabel, elevationLabel, countryCodeLabel, LongitudeLabel, temperatureLabel,
            dewPointLabel, windSpeedLabel, humidityLabel, stationNameLabel, dateTimeLabel, latitudeLabel;
    // TextViews
    TextView weatherConditionTV, cloudsTV, windDirectionTV,
             icaoTV, elevationTV, countryCodeTV, longitudeTV, temperatureTV,
            dewPointTV, windSpeedTV, humidityTV, stationNameTV, dateTimeTV, latitudeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nwdetail);
        mIcao = getIntent().getStringExtra(INTENT);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NWDetailActivity.this);
        temperature = sharedPreferences.getBoolean(temperatureCheckBox, true);
        windSpeed = sharedPreferences.getBoolean(windSpeedCheckBox, true);
        humidity = sharedPreferences.getBoolean(humidityCheckBox, true);

        WEBSERVICE_URL = "http://api.geonames.org/weatherIcaoJSON?" +
                "ICAO="+ mIcao +
                "&username=ramirezmj";
        getUI();
        new Thread(new LoadDataFromWebService(loadDataHandler, WEBSERVICE_URL)).start();
    }

    private void getUI() {

        weatherConditionLabel = (TextView) findViewById(R.id.weatherConditionLabel);
        cloudsLabel  = (TextView) findViewById(R.id.cloudsLabel);
        windDirectionLabel = (TextView) findViewById(R.id.windDirectionLabel);
        icaoLabel = (TextView) findViewById(R.id.icaoLabel);
        elevationLabel = (TextView) findViewById(R.id.elevationLabel);
        countryCodeLabel = (TextView) findViewById(R.id.countryCodeLabel);
        LongitudeLabel = (TextView) findViewById(R.id.longitudeLabel);
        temperatureLabel = (TextView) findViewById(R.id.temperatureLabel);
        dewPointLabel = (TextView) findViewById(R.id.dewPointLabel);
        windSpeedLabel = (TextView) findViewById(R.id.windSpeedLabel);
        humidityLabel = (TextView) findViewById(R.id.humidityLabel);
        stationNameLabel = (TextView) findViewById(R.id.stationNameLabel);
        dateTimeLabel = (TextView) findViewById(R.id.dateTimeLabel);
        latitudeLabel = (TextView) findViewById(R.id.latitudeLabel);

        weatherConditionTV = (TextView) findViewById(R.id.weatherConditionTV);
        cloudsTV  = (TextView) findViewById(R.id.cloudsTV);
        windDirectionTV = (TextView) findViewById(R.id.windDirectionTV);
        icaoTV = (TextView) findViewById(R.id.icaoTV);
        elevationTV = (TextView) findViewById(R.id.elevationTV);
        countryCodeTV = (TextView) findViewById(R.id.countyCodeTV);
        longitudeTV = (TextView) findViewById(R.id.longitudeTV);
        temperatureTV = (TextView) findViewById(R.id.temperatureTV);
        dewPointTV = (TextView) findViewById(R.id.dewPointTV);
        windSpeedTV = (TextView) findViewById(R.id.windSpeedTV);
        humidityTV = (TextView) findViewById(R.id.humidityTV);
        stationNameTV = (TextView) findViewById(R.id.stationNameTV);
        dateTimeTV = (TextView) findViewById(R.id.dateTimeTV);
        latitudeTV = (TextView) findViewById(R.id.latitudeTV);

        toggleVisibility();
    }

    private void toggleVisibility() {
        if (!temperature) {
            temperatureTV.setVisibility(View.GONE);
            temperatureLabel.setVisibility(View.GONE);
        }
        if (!windSpeed) {
            windSpeedTV.setVisibility(View.GONE);
            windSpeedLabel.setVisibility(View.GONE);
        }
        if(!humidity) {
            humidityTV.setVisibility(View.GONE);
            humidityLabel.setVisibility(View.GONE);
        }
    }

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
                jsonResponse = new JsonParser(webserviceResponse).parseDetails();

                // Hide the ProgressBar from the View hierarchy and show the
                // web service's response in the TextView
                this.handler.post(new Runnable() {

                    @Override public void run() {
//                        progressBar.setVisibility(View.GONE);

                        weatherConditionTV.setText(jsonResponse.get(0).getWeatherCondition());
                        cloudsTV.setText(jsonResponse.get(0).getClouds());
                        windDirectionTV.setText(jsonResponse.get(0).getWindDirection());
                        icaoTV.setText(jsonResponse.get(0).getIcao());
                        elevationTV.setText(jsonResponse.get(0).getElevation());
                        countryCodeTV.setText(jsonResponse.get(0).getCountryCode());
                        longitudeTV.setText(jsonResponse.get(0).getLongitude());
                        temperatureTV.setText(jsonResponse.get(0).getTemperature());
                        dewPointTV.setText(jsonResponse.get(0).getDewPoint());
                        windSpeedTV.setText(jsonResponse.get(0).getWindSpeed());
                        humidityTV.setText(jsonResponse.get(0).getHumidity());
                        stationNameTV.setText(jsonResponse.get(0).getStationName());
                        dateTimeTV.setText(jsonResponse.get(0).getDateTime());
                        latitudeTV.setText(jsonResponse.get(0).getLatitude());
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
}
