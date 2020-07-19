package com.example.anmlogbook;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_NAME = "name";
    private static final String KEY_USER_ID ="userid";
    private static final String KEY_USER_DESIGNATION ="designation";
    private static final String KEY_USER_MOBILE ="mobile";
    private static final String KEY_USER_SUBCENTER ="subcenter";
    private static final String KEY_USER_PHC ="phc";




    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int userid,String name,String designation,String mobile,String subcenter,String phc){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,userid);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_USER_DESIGNATION,designation);
        editor.putString(KEY_USER_MOBILE,mobile);
        editor.putString(KEY_USER_SUBCENTER,subcenter);
        editor.putString(KEY_USER_PHC,phc);
        editor.apply();
        return true;
    }
    public boolean isUserLoggedin(){
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_NAME,null)!= null){
            return  true;
        }
        return false;
    }

    public boolean isUserMo(){
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_DESIGNATION,null)== "MO"){
            return  true;
        }
        return false;
    }
    public boolean userLogout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public String UserDesignation(){
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_DESIGNATION,null);
    }
}
