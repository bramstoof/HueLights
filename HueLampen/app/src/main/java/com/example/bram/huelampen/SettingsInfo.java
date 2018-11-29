package com.example.bram.huelampen;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsInfo {
    private Context context;
    private final String SHARED_PREFS = "sharedPrefs";
    private final String LANGUAGE = "Locale.Helper.Selected.Language";
    private final String LOCATIONIP = "LocationIP";
    private final String LOCATIONNAME = "LocationName";

    public SettingsInfo(Context context) {
        this.context = context;
    }

    public void saveLangwitch(String langwitch) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE, langwitch);
        editor.apply();
    }

    public void saveLocationIP(String location) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCATIONIP, location);
        editor.apply();
    }

    public String loadLangwitch() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LANGUAGE, "en");
    }

    public String loadLocationIP() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LOCATIONIP, "");
    }

    public void saveLocationName(String location) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCATIONNAME, location);
        editor.apply();
    }

    public String loadLocationName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LOCATIONNAME, "");
    }
}
