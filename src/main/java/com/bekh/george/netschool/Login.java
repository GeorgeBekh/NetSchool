package com.bekh.george.netschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Login extends ActionBarActivity {
    EditText username;
    EditText password;
    Button accept;
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        accept = (Button) findViewById(R.id.accept);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.accept:
                        setAccept();
                        break;
                }
            }
        };
        accept.setOnClickListener(clickListener);
    }
    private void setAccept(){
        String un = username.getText().toString();
        String pw = password.getText().toString();
        intent.setClass(Login.this,Main2Activity.class);
        intent.putExtra(StartActivity.LOGIN,un);
        intent.putExtra(StartActivity.PASSWORD,pw);
        startActivity(intent);

    }

}
