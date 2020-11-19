package com.example.final_project_ui_example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Otp_Activity extends AppCompatActivity {
    TextView tvrRechance; //재전송
    TextView tvotpView; //otp를 보여주는 것
    EditText etTextOtp;
    int otpInputi;
    String OTP_ANSWER = "413185";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        tvrRechance = (TextView)findViewById(R.id.tvrRechance);
        tvotpView = (TextView)findViewById(R.id.tvotpView);
        etTextOtp = (EditText)findViewById(R.id.etTextOtp);


        tvrRechance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Otp_Activity.this,"재전송을 눌르셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
//

        AlertDialog.Builder dlg = new AlertDialog.Builder(Otp_Activity.this);
        dlg.setTitle("OTP 6자리 입력해주세요"); //제목
        dlg.setMessage(OTP_ANSWER); // 메시지
        dlg.setIcon(R.drawable.otp); // 아이콘 설정
//                버튼 클릭시 동작
        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //토스트 메시지
                Toast.makeText(Otp_Activity.this,"확인을 눌르셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        dlg.show();

        tvotpView.setText(OTP_ANSWER);
    }

    public void OtpOnClick(View view) {
        switch (view.getId()) {
            case R.id.btnotpend :
                String otpInput = etTextOtp.getText().toString();
                try {
                    otpInputi = Integer.parseInt(otpInput);
                } catch (Exception e) {
                    Toast.makeText(Otp_Activity.this, "숫자만 입력하시오", Toast.LENGTH_SHORT).show();
                }

                if (otpInputi != Integer.parseInt(OTP_ANSWER)) {
                    dialogs();
                }
                else {
                    Toast.makeText(Otp_Activity.this, "인증에 성공했습니다! \n 양문일님, 반갑습니다 ", Toast.LENGTH_SHORT).show();
                    Intent iflogin = new Intent(getApplicationContext(), IfLogin.class);
                    startActivity(iflogin);
                }
        }
    }
    void dialogs() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(Otp_Activity.this);
        dlg.setTitle("OTP 입력 실패"); //제목
        dlg.setMessage("입력하신 OTP 6자리가 틀렸습니다.\n 다시 입력하세요."); // 메시지
        dlg.setIcon(R.drawable.otp); // 아이콘 설정
//                버튼 클릭시 동작
        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //토스트 메시지
                Toast.makeText(Otp_Activity.this,"확인을 눌르셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        dlg.show();
    }
}