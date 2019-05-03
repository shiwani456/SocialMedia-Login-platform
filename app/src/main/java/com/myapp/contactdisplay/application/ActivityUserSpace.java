package com.myapp.contactdisplay.application;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class ActivityUserSpace {

    private String KEY_SHARED_PREF = "activity";
    private ActivityUserSpace activityUserSpace = null;

    private SharedPreferences sharedPreferences ;
    private Context context;


    public ActivityUserSpace(Context _context)
    {

           this.context = _context;
        sharedPreferences = this.context.getSharedPreferences(KEY_SHARED_PREF, MODE_PRIVATE);

    }

    public void clearSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    public ActivityUserSpace getInstance(Context context) {
        initialize(context);
        return activityUserSpace;
    }

    private void initialize(Context context) {
        if (activityUserSpace == null) {
            synchronized(ActivityUserSpace.class) {
                activityUserSpace = new ActivityUserSpace(context);
            }
        }
    }




    public String getUserName() {
        return sharedPreferences.getString("name", null);

    }

    public void setUserName(String name) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("name", name);
        sharedPreferencesEditor.apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString("email", null);

    }

    public void setUserEmail(String email) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("email", email);
        sharedPreferencesEditor.apply();
    }

    public String getUserPhone() {
        return sharedPreferences.getString("phone", null);

    }

    public void setUserPhone(String  phone) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("phone",phone);
        sharedPreferencesEditor.apply();
    }
    public String getUserImageUrl(){
        return sharedPreferences.getString("url", null);

    }

    public void setUserImageUrl(String url) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("url", url);
        sharedPreferencesEditor.apply();
    }


}
