package com.example.final_project_ui_example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public final static int LOGIN_ACTIVITY = 100;
    public final static int START_ACTIVITY = 200 ;
    public final static int REGISTER_ACTIVITY = 300;
    public final static int START_ACTIVITY2 = 400 ;
    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLetslogin :
//                Intent login = new Intent(MainActivity.this, LoginActivity.class);
//                 startActivityForResult(login, LOGIN_ACTIVITY);
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(register, REGISTER_ACTIVITY);
                break;
            case R.id.btnStart:
                Intent Start = new Intent(MainActivity.this, AuthorizationActivity.class);
                startActivityForResult(Start, START_ACTIVITY);
                break;
            case R.id.btn_if_login:
                Intent iflogin = new Intent(MainActivity.this, IfLogin.class);
                startActivityForResult(iflogin, START_ACTIVITY2);
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == LOGIN_ACTIVITY) {
//            switch (resultCode) {
//                case RE
//            }
//        }
//    }
}