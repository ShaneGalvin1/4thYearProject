package com.example.shane.winningedge;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Shane on 16/04/2015.
 */
public class Session {

    private static Session session;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "WinningEdgeSession";

    // All Shared Preferences Keys
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TOKEN = "oauth-token";

    // Constructor
    private Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // Method to enforce singleton pattern
    public static synchronized Session getCurrentSession(Context context) {
        if (session == null) {
            session = new Session(context);
        }
        return session;
    }

    public void putUserId(int id) {
        editor.putInt(KEY_ID, id);
        editor.commit();
    }

    public void putUserEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public void putOAuthToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public void clearCache() {
        editor.clear();
        editor.commit();
    }

    public int getUserId() {
        return pref.getInt(KEY_ID, 0);
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public String getOAuthToken() { return pref.getString(KEY_TOKEN, null); }

}
