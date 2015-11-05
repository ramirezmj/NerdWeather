package edu.jmramirez.nerdweather.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;

import edu.jmramirez.nerdweather.R;

public class NWSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.layout.activity_nwsettings);
    }

}
