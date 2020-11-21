package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;
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


public class Activity_otpotp extends AppCompatActivity {
    private static final String TAG = "Activity";
    PinView pinView;
    ImageView button;
    CircularTimerView progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recreate_otp);


        button = (ImageView)findViewById(R.id.btnOtpSend);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setProgress(0);

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
//          ■■ OTP 전송 ■■
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String res = pinView.getText().toString();
                Toast.makeText(Activity_otpotp.this, "결과는 "+ res, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Activity_otpotp.this, "입력 가능한 시간이 종료되었습니다.\n 재전송을 클릭하세요", Toast.LENGTH_SHORT).show();
                progressBar.setPrefix("");
                progressBar.setSuffix("");
                progressBar.setText("시간 종료");
            }
        }, 60, TimeFormatEnum.SECONDS, 10);



// To start timer

        progressBar.startTimer();


    }

}