package com.test.runHiddenApi;

import android.app.Application;
import android.util.Log;

/**
 * Developed by Sunil kumar 12-05-2022
 */
public class HpApplication extends Application {
    static {
        System.loadLibrary("keys");
    }
    public native String getProUrl();
    public native String getX();

    private static final String TAG = "HpApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        RunHiddenApi.AZURE_URL=EncryptionDecryption.decryptUid(getProUrl(),getX());
        Log.d(TAG, "onCreate: "+RunHiddenApi.AZURE_URL);
    }
}
