package com.example.final_project_ui_example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.chaos.view.PinView;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.view.circulartimerview.CircularTimerListener;
import com.view.circulartimerview.CircularTimerView;
import com.view.circulartimerview.TimeFormatEnum;

import java.util.Timer;
import java.util.TimerTask;



public class Otp_Activity extends AppCompatActivity {
    TextView tvrRechance; //재전송
    TextView tvotpView; //otp를 보여주는 것
    EditText etTextOtp;
    int otpInputi;

    private static final String TAG = "Otp_Activity";
    PinView pinView;
    ImageView button;
    CircularTimerView progressBar;
    ImageView btnOtpSend;

    GitHubService service;
    HashMap<String, Object> input;
    HashMap<String, Object> input2;
    String your_otp;
     User chosun;

    String user_name;
    String user_phone;
    String real_fake;

    String feel1;
    String feel2;
    String feel3;
    int feel1_state;
    int feel2_state;
    int feel3_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Intent loading = getIntent();
        real_fake = loading.getStringExtra("real_fake");
        user_name = loading.getStringExtra("user_name");
        user_phone = loading.getStringExtra("user_phone");
        feel1 = loading.getStringExtra("feel1");
        feel2 =  loading.getStringExtra("feel2");
        feel3 = loading.getStringExtra("feel3");
        feel1_state = loading.getIntExtra("feel1_state", 70);
        feel2_state = loading.getIntExtra("feel2_state", 20);
        feel3_state = loading.getIntExtra("feel3_state", 10);


        tvrRechance = (TextView)findViewById(R.id.tvrRechance);
        btnOtpSend = (ImageView)findViewById(R.id.btnOtpSend);

        button = (ImageView)findViewById(R.id.btnOtpSend);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setProgress(0);






        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.22.67:8000/")
                .baseUrl("http://192.168.88.128:8000/")
                .addConverterFactory(GsonConverterFactory.create()) //아래의 service에서 callback 받는것을 자동으로 Convert 해주게 하는것
                .build();


//        ◆◆지금은 초선이로 대체했지만 추후엔 딥러닝에서 받은 사용자의 이름과 전화번호를 받을 것,
//        그리고 바로 전 Activity인 Loading 에서 사용자의 정보를 받는 레트로핏 통신을 추가할 예정◆◆
//        초선 "01022223303"
        chosun = new User(user_name, user_phone);
        input = new HashMap<>();
        input.put("user_name", chosun.getUserName());
        input.put("phone", chosun.getPhone());

        tvrRechance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Otp_Activity.this,"재전송을 누르셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
//
//       OTP를 받아오는 레트로핏 통신
        service = retrofit.create(GitHubService.class);


        try {
            service.postOTP("otp1/", input).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()) {
                        Log.d("Otp_Activity", "otp에 접속 성공\n" + response.raw());
                        User postMessages= response.body();
                        Log.d("Otp_Activity" , "OTP ___ Retrofit2 Test : "+postMessages.getOtp());

                        your_otp = postMessages.getOtp();

                        AlertDialog.Builder dlg = new AlertDialog.Builder(Otp_Activity.this);
                        dlg.setTitle("OTP 6자리 입력해주세요"); //제목
                        dlg.setMessage(your_otp); // 메시지
                        dlg.setIcon(R.drawable.otp); // 아이콘 설정
//                버튼 클릭시 동작
                        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                //토스트 메시지
                                Toast.makeText(Otp_Activity.this,"확인을 눌르셨습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dlg.show();

//                        tvotpView.setText(your_otp);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("Otp_Activity" + "otp failure",t.getMessage());
                    Log.e("Otp_Activity",t.getStackTrace().toString());

                    otpfail(1);

                }
            });

        } catch (Exception ex) {
            Log.e("Otp_Activity" + "접속조차 실패 ", ex.getMessage());
            Log.e("Otp_Activity", ex.getLocalizedMessage());
            otpfail(2);
        }


        //        ■■ OTP 입력 창 ■■
        pinView = findViewById(R.id.secondPinView);
        pinView.bringToFront();
        pinView.setTextColor(
                ResourcesCompat.getColor(getResources(), R.color.colorAccent, getTheme()));
        pinView.setTextColor(
                ResourcesCompat.getColorStateList(getResources(), R.color.text_colors, getTheme()));
        pinView.setLineColor(
                ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()));
        pinView.setLineColor(
                ResourcesCompat.getColorStateList(getResources(), R.color.line_colors, getTheme()));
        pinView.setItemCount(6);
        pinView.setItemHeight(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        pinView.setItemWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        pinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_radius));
        pinView.setItemSpacing(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        pinView.setLineWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        pinView.setAnimationEnable(true);// start animation when adding text
        pinView.setCursorVisible(false);
        pinView.setCursorColor(
                ResourcesCompat.getColor(getResources(), R.color.line_selected, getTheme()));
        pinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged() called with: s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnOtpSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpInput = pinView.getText().toString();
                try {
                    otpInputi = Integer.parseInt(otpInput);
                } catch (Exception e) {
                    Toast.makeText(Otp_Activity.this, "숫자만 입력하시오", Toast.LENGTH_SHORT).show();
                }

//                ◆◆Retrofit 입력 값 전송◆◆

                try {
                    input2 = new HashMap<>();
                    input2.put("user_name", chosun.getUserName());
                    input2.put("phone", chosun.getPhone());
                    input2.put("otp_id", otpInput);

                    Log.e("Otp_Activity" + "당신이 입력한 OTP ",otpInput);

                    service.postOTP2("otp2/", input2).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()) {
                                Log.d("Otp_Activity", "접속 성공\n" + response.raw());
                                User postMessages= response.body();
                                Log.d("Otp_Activity" , "Retrofit2 Test : "+postMessages.getMessage());

                                String otp_res = postMessages.getMessage();

                                if (otp_res.equals("승인")) {

                                    Intent iflogin = new Intent(Otp_Activity.this, IfLogin.class);
                                    iflogin.putExtra("real_fake", real_fake);
                                    iflogin.putExtra("name", user_name);
                                    iflogin.putExtra("feel1", feel1);
                                    iflogin.putExtra("feel2", feel2);
                                    iflogin.putExtra("feel3", feel3);
                                    iflogin.putExtra("feel1_state", feel1_state);
                                    iflogin.putExtra("feel2_state",feel2_state);
                                    iflogin.putExtra("feel3_state", feel3_state);
                                    startActivity(iflogin);
//                                    Toast.makeText(Otp_Activity.this, "인증에 성공했습니다! \n 초선님, 반갑습니다 ", Toast.LENGTH_SHORT).show();

                                }else if (otp_res.equals("거부")) {
                                    dialogs();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("Otp_Activity" + "otp ffff",t.getMessage());
                            Log.e("Otp_Activity",t.getStackTrace().toString());
                        }
                    });

                }catch (Exception ex) {
                    Log.e("Otp_Activity" + "otp 접속조차 실패 ", ex.getMessage());
                    Log.e("Otp_Activity", ex.getLocalizedMessage());
                }
            }
        });

        //      ■■ OTP 타이머 ■■
        progressBar.setCircularTimerListener(new CircularTimerListener() {
            @Override
            public String updateDataOnTick(long remainingTimeInMs) {
                return String.valueOf((int)Math.ceil((remainingTimeInMs / 1000.f)));
            }

            @Override
            public void onTimerFinished() {
//                Toast.makeText(Otp_Activity.this, "입력 가능한 시간이 종료되었습니다.\n 재전송을 클릭하세요", Toast.LENGTH_SHORT).show();
                progressBar.setPrefix("");
                progressBar.setSuffix("");
                progressBar.setText("시간 종료");

                AlertDialog.Builder dlg = new AlertDialog.Builder(Otp_Activity.this);
                dlg.setTitle("OTP 입력 시간 종료"); //제목
                dlg.setMessage("OTP 재전송시 확인을 누르시오"); // 메시지
                dlg.setIcon(R.drawable.otp); // 아이콘 설정
//                버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        // 다시 ■ ■ Retrofit2 재전송 ■ ■
                        try {
                            service.postOTP("otp1/", input).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()) {
                                        Log.d("Otp_Activity", "otp에 접속 성공\n" + response.raw());
                                        User postMessages= response.body();
                                        Log.d("Otp_Activity" , "OTP ___ Retrofit2 Test : "+postMessages.getOtp());

                                        your_otp = postMessages.getOtp();

                                        AlertDialog.Builder dlg = new AlertDialog.Builder(Otp_Activity.this);
                                        dlg.setTitle("OTP 6자리 입력해주세요"); //제목
                                        dlg.setMessage(your_otp); // 메시지
                                        dlg.setIcon(R.drawable.otp); // 아이콘 설정
//                버튼 클릭시 동작
                                        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int which) {
                                                //토스트 메시지
                                                Toast.makeText(Otp_Activity.this,"확인을 눌르셨습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        dlg.show();
//                                        다시 재시작
                                        progressBar.startTimer();
//                                        tvotpView.setText(your_otp);
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Log.e("Otp_Activity" + "otp failure",t.getMessage());
                                    Log.e("Otp_Activity",t.getStackTrace().toString());

                                    otpfail(1);

                                }
                            });

                        } catch (Exception ex) {
                            Log.e("Otp_Activity" + "접속조차 실패 ", ex.getMessage());
                            Log.e("Otp_Activity", ex.getLocalizedMessage());
                            otpfail(2);
                        }

                    }
                });
                dlg.setNegativeButton("뒤로 가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dlg.show();
            }
        }, 120, TimeFormatEnum.SECONDS, 10);


// To start timer

        progressBar.startTimer();



    }

    public void otpfail(int code) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(Otp_Activity.this);
        dlg.setTitle("안내 메세지"); //제목
        String error1 = "서버 접속은 했으나 실패했습니다.";
        String error2 = "서버 접속에 실패했습니다.";
        dlg.setIcon(R.drawable.otp);

        if (code == 1) {
            dlg.setMessage("회원가입 등록 오류"); // 메시지
            dlg.setPositiveButton(error1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지
                    Toast.makeText(Otp_Activity.this, "확인하셨습니다..", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (code == 2) {
            dlg.setMessage("회원가입 등록 오류"); // 메시지
            dlg.setPositiveButton(error2, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지
                    Toast.makeText(Otp_Activity.this, "확인하셨습니다..", Toast.LENGTH_SHORT).show();

                }
            });
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
                Toast.makeText(Otp_Activity.this,"확인을 누르셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        dlg.show();
    }
}