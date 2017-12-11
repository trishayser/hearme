package com.thkoeln.paulo.hearme;

//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class SettingsActivity extends PreferenceActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.mypreferences);
            //setContentView(R.layout.settings_activity);

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.menu_main, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//
//            if (id == R.id.profil_change) {
//
//                return true;
//            }
//
//            return super.onOptionsItemSelected(item);
//        }
    }



