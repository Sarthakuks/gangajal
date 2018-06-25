package gangajal.app.project.uks.gangajal.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String STATUS = "status";
    private static final String NOTIFICATION = "notification";
    private static final String SHARED_PREF_NAME = "App_File";

    public static void setStatus(Context context, boolean value) {
        SharedPreferences preferences = context
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(STATUS, value);
        edit.apply();
    }

    public static boolean getStatus(Context context) {
        SharedPreferences preferences = context
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(STATUS, false);
    }

    public static void setNotification(Context context, String value) {
        SharedPreferences preferences = context
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(NOTIFICATION, value);
        edit.apply();
    }

    public static String getNotification(Context context) {
        SharedPreferences preferences = context
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(NOTIFICATION, "");
    }

    public static void setString(Context context, String value, String key) {
        SharedPreferences preferences = context
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences preferences = context
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void clear(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }
}
