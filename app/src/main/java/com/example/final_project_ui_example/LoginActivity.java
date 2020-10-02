package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
   public final static int REGISTER_ACTIVITY = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etid = (EditText)findViewById(R.id.et_phone);
        EditText etpass = (EditText)findViewById(R.id.et_pass);
        Button btnlogin = (Button)findViewById(R.id.btn_login);
        Button btnregister = (Button)findViewById(R.id.btn_register);
        Button btnback = (Button)findViewById(R.id.btnBack);

        String id = etid.getText().toString();
        String passsword = etpass.getText().toString();
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_login:

                break;
            case R.id.btn_register:
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(register, REGISTER_ACTIVITY);
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

}