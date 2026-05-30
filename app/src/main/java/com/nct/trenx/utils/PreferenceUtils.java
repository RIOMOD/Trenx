package com.nct.trenx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class PreferenceUtils {
    private static final String PREF_NAME = "trenx_prefs";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setLanguage(Context context, String langCode) {
        getPrefs(context).edit().putString(KEY_LANGUAGE, langCode).apply();
    }

    public static String getLanguage(Context context) {
        return getPrefs(context).getString(KEY_LANGUAGE, "en");
    }

    public static Context updateResources(Context context) {
        String lang = getLanguage(context);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        LocaleList localeList = new LocaleList(locale);
        config.setLocales(localeList);

        return context.createConfigurationContext(config);
    }

    public static void setDarkMode(Context context, boolean isEnabled) {
        getPrefs(context).edit().putBoolean(KEY_DARK_MODE, isEnabled).apply();
        applyTheme(isEnabled);
    }

    public static boolean isDarkMode(Context context) {
        return getPrefs(context).getBoolean(KEY_DARK_MODE, false);
    }

    public static void applyTheme(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static void setNotificationsEnabled(Context context, boolean isEnabled) {
        getPrefs(context).edit().putBoolean(KEY_NOTIFICATIONS, isEnabled).apply();
    }

    public static boolean isNotificationsEnabled(Context context) {
        return getPrefs(context).getBoolean(KEY_NOTIFICATIONS, true);
    }
    
    public static void init(Context context) {
        applyTheme(isDarkMode(context));
    }
}
