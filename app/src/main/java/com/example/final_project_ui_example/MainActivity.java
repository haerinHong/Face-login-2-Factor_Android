package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static int LOGIN_ACTIVITY = 100;
    public final static int START_ACTIVITY = 200 ;
    public final static int REGISTER_ACTIVITY = 300;
    public final static int START_ACTIVITY2 = 400 ;
    private MyDBHelper myDBHelper;
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
                Intent Start = new Intent(MainActivity.this, PhotoActivity.class);
                startActivityForResult(Start, START_ACTIVITY);
                break;
            case R.id.btn_if_login:
                Intent iflogin = new Intent(MainActivity.this, IfLogin.class);
                startActivityForResult(iflogin, START_ACTIVITY2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_ACTIVITY) {
            switch (resultCode) {
                case 1000:
                    String name = data.getExtras().getString("name");
                    String phone = data.getExtras().getString("phone");
                    Log.d("ONAVTIVITY", "name "+ name+ " phone "+ phone);
                    Toast.makeText(MainActivity.this, "추가된 사용자의\n 이름 : "+ name + "\n 전화번호 : "+ phone, Toast.LENGTH_LONG).show();
            }
        }
    }
}