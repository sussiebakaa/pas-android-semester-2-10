package com.example.pas_api.login;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final String sessionLogin= "session_login";
    private final String token= "token";
    private final String firstRun = "first_run";

    public Preference(Context context) {
        pref = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setSessionLogin(boolean value) {
        editor.putBoolean(sessionLogin, value);
        editor.commit();
    }

    public boolean getSessionLogin(){return  pref.getBoolean(sessionLogin, false);}

    public  void setToken (String value) {
        editor.putString(token, value);
        editor.commit();
    }

    public String getToken(){ return pref.getString(token, "");}

    public boolean getFirstRun() {
        return pref.getBoolean(firstRun, true);
    }

    public void setFirstRun(boolean input) {
        editor.putBoolean(firstRun, input);
        editor.commit();
    }
}
