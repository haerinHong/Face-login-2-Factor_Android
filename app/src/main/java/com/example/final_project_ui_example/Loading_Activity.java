package com.example.final_project_ui_example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class Loading_Activity extends Activity {
public final static int LOADING = 560;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        ImageView ivloading = (ImageView)findViewById(R.id.ivloading);
//        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(ivloading);
//        Glide.with(Loading_Activity.this).load(R.raw.spinner_nontrans).into(ivloading);

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent loading = new Intent(getBaseContext(), Otp_Activity.class);
                Intent loading = new Intent(getBaseContext(), Otp_Activity.class);
                startActivity(loading);
                finish();
            }
        }, 3000);
    }

}