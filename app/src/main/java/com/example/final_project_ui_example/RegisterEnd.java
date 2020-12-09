package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterEnd extends AppCompatActivity {
Button btnRegisterGoHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registerend);
        btnRegisterGoHome = (Button)findViewById(R.id.btnRegisterGoHome);

        btnRegisterGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterEnd.this, MainActivity.class));
            }
        });
    }
}