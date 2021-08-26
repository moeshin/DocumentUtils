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

        DirUtil dir = new DirUtil(activity.getExternalFilesDir(null));
        dir.createFile("test");
        dir.createFile("test.txt");
        dir.createFile("test.md");
        dir.createFile("test.json");

        preferenceScreen.addPreference(new MainPreferenceCategory(
                activity,
                "Context#getExternalFilesDir(null)",
                dir.getDir()
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
