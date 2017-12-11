package com.thkoeln.paulo.hearme;

import android.preference.PreferenceFragment;
import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;


//public class SettingsFragment extends Fragment {
public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);
        addPreferencesFromResource(R.xml.pref_headers);


        return rootView;
    }



}