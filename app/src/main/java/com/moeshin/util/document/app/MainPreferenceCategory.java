package com.moeshin.util.document.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;

import com.moeshin.util.document.DocumentUtils;

import java.io.File;

public class MainPreferenceCategory extends PreferenceCategory {

    public static final int REQUEST_CODE = 10;

    private final Activity activity;
    private final Uri uri;

    public MainPreferenceCategory(Activity activity, String title, File dir) {
        super(activity);
        this.activity = activity;
        setTitle(title);
        setIconSpaceReserved(false);
        if (dir == null) {
            uri = null;
        } else {
            setSummary(dir.getPath());
            uri = DocumentUtils.createDocumentUri(dir);
        }
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        TextView summary = (TextView) holder.findViewById(android.R.id.summary);
        if (summary != null) {
            summary.setSingleLine(false);
        }
    }

    public Preference createItem(String title,
                                 Preference.OnPreferenceClickListener clickListener) {
        Preference preference = new Preference(getContext());
        preference.setIconSpaceReserved(false);
        preference.setTitle(title);
        preference.setOnPreferenceClickListener(clickListener);
        return preference;
    }

    public Preference createItem(@StringRes int resId,
                                 Preference.OnPreferenceClickListener clickListener) {
        return createItem(getContext().getString(resId), clickListener);
    }

    @Override
    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);
        addPreference(createItem(R.string.open_documents_ui, pref -> {
            activity.startActivity(DocumentUtils.createOpenDocumentsUiIntent(activity, uri));
            return false;
        }));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addPreference(createItem("ACTION_OPEN_DOCUMENT", pref -> {
                activity.startActivityForResult(
                        new Intent(Intent.ACTION_OPEN_DOCUMENT)

                                // Must call setType, can be other types
                                .setType("*/*")

                                // If there are multiple types, please use Intent.EXTRA_MIME_TYPES
                                // But don’t forget to call setType
//                                .putExtra(Intent.EXTRA_MIME_TYPES,
//                                        new String[] {"text/plain", "text/markdown"})

                                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                                .putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
                        , REQUEST_CODE
                );
                return false;
            }));
            addPreference(createItem("ACTION_OPEN_DOCUMENT_TREE", pref -> {
                activity.startActivityForResult(
                        new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                                .putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
                        , REQUEST_CODE
                );
                return false;
            }));
        }
    }
}
