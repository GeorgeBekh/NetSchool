package com.bekh.george.netschool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class StartActivity extends ActionBarActivity {
    final public static String ALL ="all";
    final public static String WEEK ="week";
    final public static String DIARY ="diarycontent";
    final public static String MARKS ="markscontent";
    final public static String FORM_DATA ="formdata";
    final public static String LOGIN ="currentlogin";
    public static Typeface tf;

    final public static String PASSWORD ="currentpassword";
    final public static String URL ="http://ns.gymn24.ru/login1.asp";
    private static String currentLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tf = Typeface.createFromAsset(this.getAssets(),"fonts/Dewberry regular.ttf");
        Intent intent = new Intent();
        if(check()){
            intent.setClass(StartActivity.this,Main2Activity.class);
            intent.putExtra(LOGIN, currentLogin);
        }else{
            intent.setClass(StartActivity.this, Login.class);
        }

        startActivity(intent);

    }
    public boolean check(){
        SharedPreferences sPref = getSharedPreferences(FORM_DATA,MODE_PRIVATE);
        if(sPref.getString(LOGIN,"").equals("")){
            Log.v(Main2Activity.TAG,"Регистрация нового пользователя");
            return false;
        }else{
            currentLogin = sPref.getString(LOGIN,"");
            Log.v(Main2Activity.TAG,"Найден зарегестрированный пользователь "+currentLogin);
            return true;
        }
    }


}
