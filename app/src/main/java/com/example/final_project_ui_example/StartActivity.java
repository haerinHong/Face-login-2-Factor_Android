package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

public class StartActivity extends Activity {
    private static final String TAG = "StartActivity";
    private static Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 5000); // 1초 후에 hd handler 실행  3000ms = 3초

        typeface = Typeface.createFromAsset(this.getAssets(), "tmoneyroundwindextrabold.otf");


        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);
        rotatingTextWrapper.setTypeface(typeface);

        Rotatable rotatable = new Rotatable(Color.parseColor("#ffff00"), 1000, "With Face", "And OTP", "By도원결의");;
        rotatable.setSize(35);
        rotatable.setTypeface(typeface);
        rotatable.setAnimationDuration(500);
        rotatingTextWrapper.setContent("Log-in ", rotatable);


    }
    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), IfLogin.class)); //로딩이 끝난 후, ChoiceFunction 이동
            StartActivity.this.finish(); // 로딩페이지 Activity stack에서 제거
        }
    }
    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

}