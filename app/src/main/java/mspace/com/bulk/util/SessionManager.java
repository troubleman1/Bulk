package mspace.com.bulk.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import mspace.com.bulk.LoginActivity;
import mspace.com.bulk.model.User;

/**
 * Created by root on 4/7/16.
 */
public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;
    //SHARED PREFERENCE FILE NAME
    private static final String PREF_NAME = "USER_DETAILS";

    //ALL SHARED PREFERENCE KEYS
    public static final String IS_LOGIN="IsLoggedIn";

    private static final String KEY_NAME="first_name";
    public static final String KEY_EMAIL="last_name";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE );
        editor = pref.edit();

    }

    public void createLoginSession(User user){

        editor.putBoolean(IS_LOGIN , true);
        editor.putString(KEY_NAME ,user.getFirstName());
        editor.putString(KEY_EMAIL ,user.getLastName());
        editor.commit();
    }

    private boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN ,false);
    }



    public void checkLogin(){
        if(! this.isLoggedIn()){
            Intent i = new Intent(context , LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            context.startActivity(i);
        }
    }

    public User getUserDetails(){
        User u = new User();
        u.setFirstName(pref.getString(KEY_NAME ,null));
        u.setLastName(pref.getString(KEY_EMAIL ,null));
        return  u;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context ,LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }



}
