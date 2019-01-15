package com.example.pchrp.pro_1.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManagerKid {

    private static SharedPrefManagerKid cInstance;
    private static Context mCtx;

    private static final String SHARED_NAME_ID  =  "sharedpref2";
    private static final String KEY_CID         =  "c_id";
    private static final String KEY_CNAME       =  "c_name";
    private static final String KEY_CGENDAR     =  "c_gender";
    private static final String KEY_CDATE       =  "c_birthday";
    private static final String KEY_CWEIGHT     =  "c_weight";
    private static final String KEY_CHEIGHT     =  "c_height";
    private static final String KEY_CBLOOD      =  "c_blood";

    private  SharedPrefManagerKid(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefManagerKid getInstance(Context context) {
        if (cInstance == null) {
            cInstance = new SharedPrefManagerKid(context);
        }
        return cInstance;
    }

    public boolean datakid(String id, String name, String gender, String date,String weight,String height,String blood){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_CID,id);
        editor.putString(KEY_CNAME,name);
        editor.putString(KEY_CGENDAR,gender);
        editor.putString(KEY_CDATE,date);
        editor.putString(KEY_CWEIGHT,weight);
        editor.putString(KEY_CHEIGHT,height);
        editor.putString(KEY_CBLOOD,blood);

        editor.apply();

        return true;
    }

    public boolean onClear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getCid(){
        SharedPreferences sharedPreferencesKid = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        return sharedPreferencesKid.getString(KEY_CID, null);
    }

    public String getCgender(){
        SharedPreferences sharedPreferencesKid = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        return sharedPreferencesKid.getString(KEY_CGENDAR, null);
    }
    public String getCname(){
        SharedPreferences sharedPreferencesKid = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        return sharedPreferencesKid.getString(KEY_CNAME, null);
    }
    public String getCdate(){
        SharedPreferences sharedPreferencesKid = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        return sharedPreferencesKid.getString(KEY_CDATE, null);
    }
    public String getCweight(){
        SharedPreferences sharedPreferencesKid = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        return sharedPreferencesKid.getString(KEY_CWEIGHT, null);
    }
    public String getCheight(){
        SharedPreferences sharedPreferencesKid = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        return sharedPreferencesKid.getString(KEY_CHEIGHT, null);
    }
    public String getCblood(){
        SharedPreferences sharedPreferencesKid = mCtx.getSharedPreferences(SHARED_NAME_ID, Context.MODE_PRIVATE);
        return sharedPreferencesKid.getString(KEY_CBLOOD, null);
    }


}
