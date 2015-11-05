package edu.jmramirez.nerdweather.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.jmramirez.nerdweather.Fragments.NWSettingsFragment;
import edu.jmramirez.nerdweather.R;

public class NWSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new NWSettingsFragment())
                .commit();
    }
}
