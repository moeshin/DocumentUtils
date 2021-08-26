package com.moeshin.util.document;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class DocumentUtils {

    private static final String PACKAGE_DOCUMENTS_UI = "com.android.documentsui";
    private static final String PACKAGE_DOCUMENTS_UI_GOOGLE = "com.google.android.documentsui";
    private static final String ACTIVITY_DOCUMENTS_UI = "com.android.documentsui.files.FilesActivity";
    private static final String AUTHORITY_DOCUMENTS_UI = "com.android.externalstorage.documents";
    private static final String SCHEME_CONTENT = "content";
    private static final String PATH_DOCUMENT = "document";
    private static final String ROOT_ID_PRIMARY_EMULATED = "primary";

    private static boolean isChildPath(String path, String parent) {
        if (path.startsWith(parent)) {
            int pathLength = path.length();
            int parentLength = parent.length();
            return pathLength == parentLength ||
                    (pathLength > parentLength && path.charAt(parentLength) == '/');
        }
        return false;
    }

    /**
     * Get path relative to sdcard (external storage)
     */
    @SuppressLint("SdCardPath")
    public static String getRelativePath(String path) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (path.startsWith("/")) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            if (sdcard.endsWith("/")) {
                sdcard = sdcard.substring(0, sdcard.length() - 1);
            }
            if (isChildPath(path, sdcard) || isChildPath(path, sdcard = "/sdcard")) {
                return path.substring(sdcard.length());
            }
        }
        return null;
    }

    public static String getRelativePath(File file) {
        try {
            return getRelativePath(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("CommentedOutCode")
    public static Uri createDocumentUri(String relativePath) {
//        return DocumentsContract.buildDocumentUri(DOCUMENTS_UI_AUTHORITY,
//                ROOT_ID_PRIMARY_EMULATED + ':' + relativePath);
        return new Uri.Builder()
                .scheme(SCHEME_CONTENT)
                .authority(AUTHORITY_DOCUMENTS_UI)
                .appendPath(PATH_DOCUMENT)
                .appendPath(ROOT_ID_PRIMARY_EMULATED + ':' + relativePath)
                .build();
    }

    public static Uri createDocumentUri(File file) {
        return createDocumentUri(getRelativePath(file));
    }

    /**
     * View files in DocumentsUi app
     */
    public static Intent createOpenDocumentsUiIntent(Context context, Uri initialUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String pkg = PACKAGE_DOCUMENTS_UI;
        try {
            context.getPackageManager().getPackageInfo(pkg, 0);
        } catch (PackageManager.NameNotFoundException e) {
            pkg = PACKAGE_DOCUMENTS_UI_GOOGLE;
        }
        intent.setComponent(new ComponentName(pkg, ACTIVITY_DOCUMENTS_UI));
        intent.setPackage(pkg);
        if (initialUri != null) {
            intent.setData(initialUri);
        }
        return intent;
    }

    public static Intent createOpenDocumentsUiIntent(Context context) {
        return createOpenDocumentsUiIntent(context, createExternalStorageUri());
    }

    public static Uri createExternalStorageUri() {
//        StorageManager storageManager = context.getSystemService(StorageManager.class);
//        return storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent()
//                .getParcelableExtra(DocumentsContract.EXTRA_INITIAL_URI));
//        return DocumentsContract.buildRootUri(AUTHORITY_DOCUMENTS_UI, ROOT_ID_PRIMARY_EMULATED);
        return createDocumentUri("");
    }
}
