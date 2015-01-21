package com.example.couponduniatest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class sharedPreferences {
	static final String PREF_LATTITUDE= "lattitude";
	static final String PREF_LONGITIDE= "longitude";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLatandLongi(Context ctx, String latt , String longi) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LATTITUDE, latt);
        editor.putString(PREF_LONGITIDE,longi);
        editor.commit();
    }

    public static String getLattitute(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LATTITUDE, "");
    }
    public static String getLongitude(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LONGITIDE, "");
    }
}
