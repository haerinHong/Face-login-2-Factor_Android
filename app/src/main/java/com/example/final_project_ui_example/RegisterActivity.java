package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    String name;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText etName = (EditText)findViewById(R.id.etName);
        EditText etPhone = (EditText)findViewById(R.id.etPhone);
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
    }
    public void onRegisterClick(View view) {
        switch(view.getId()) {
            case R.id.btnCheckDuplicate : // 전화번호를 db 내 검색해 중복 값 확인

                break;
            case R.id.btnImageUpload:

                break;
            case R.id.ivRegisterFace : //사진 업로드시 보여줘야할 imageview
                break;
            case R.id.btnRegister: // db연결, 추가 후, Toast로 띄워준다.

                break;
            case R.id.btnCancelRegister:
                finish();
                break;
        }
    }
}