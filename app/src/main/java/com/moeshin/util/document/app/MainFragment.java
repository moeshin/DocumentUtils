package com.moeshin.util.document.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class MainFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Activity activity = requireActivity();
        PreferenceScreen preferenceScreen = getPreferenceManager().createPreferenceScreen(activity);
        setPreferenceScreen(preferenceScreen);

        preferenceScreen.addPreference(new MainPreferenceCategory(
                activity,
                "Context#getExternalFilesDir(null)",
                activity.getExternalFilesDir(null)
        ));
        preferenceScreen.addPreference(new MainPreferenceCategory(
                activity,
                "Environment.getExternalStorageDirectory()",
                Environment.getExternalStorageDirectory()
        ));
        preferenceScreen.addPreference(new MainPreferenceCategory(
                activity,
                activity.getString(R.string.no_set_initial_dir),
                null
        ));
    }
}
