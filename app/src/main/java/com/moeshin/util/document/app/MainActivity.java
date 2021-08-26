package com.moeshin.util.document.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainPreferenceCategory.REQUEST_CODE) {
            String msg;
            if (resultCode == RESULT_CANCELED) {
                msg = getString(android.R.string.cancel);
            } else if (data == null) {
                msg = "data == null";
            } else if (data.getData() != null) {
                msg = "data.getData() = " + data.getData();
            } else if (data.getClipData() != null) {
                StringBuilder sb = new StringBuilder(128);
                sb.append("data.getClipData() =\n");
                ClipData clipData = data.getClipData();
                int length = clipData.getItemCount();
                for (int i = 0; i < length; ++i) {
                    sb.append(i).append(": ").append(clipData.getItemAt(i).getUri()).append("\n");
                }
                msg = sb.toString();
            } else {
                return;
            }
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("onActivityResult")
                    .setMessage(msg)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }
}