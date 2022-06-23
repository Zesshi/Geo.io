package net.ictcampus.geoio;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class CoinsClass {

    public static void setCoins(String key, int value, Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            editor.apply();

    }

    public static int getCoins(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Log.e("GET", "vor get");
        int halo = (int) preferences.getInt(key, 0);
        Log.e("COIN", String.valueOf(halo));
        return halo;
    }
}
