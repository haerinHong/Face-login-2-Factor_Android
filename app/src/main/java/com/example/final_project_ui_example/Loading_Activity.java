package com.example.final_project_ui_example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Loading_Activity extends Activity {
    public final static int LOADING = 560;
    String videopath;
    File videoFile;
    String now_Time;
    Retrofit retrofit;
    GitHubService service;
    int CODE = -1;
    String real_fake ="";
    String name = "";
    String feel1 = "";
    String feel2 = "";
    String feel3 = "";
    int feel1_state = -1;
    int feel2_state = -1;
    int feel3_state = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        ImageView ivloading = (ImageView)findViewById(R.id.ivloading);
//        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(ivloading);
//        Glide.with(Loading_Activity.this).load(R.raw.spinner_nontrans).into(ivloading);
         retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.22.67:8000/")
                .baseUrl("http://192.168.88.128:8000/")
                .addConverterFactory(GsonConverterFactory.create()) //아래의 service에서 callback 받는것을 자동으로 Convert 해주게 하는것
                .build();
        Intent loading = getIntent();
        videopath = loading.getStringExtra("videoSendPath");
        videoFile = new File(videopath);
        now_Time = loading.getStringExtra("now_Time");
        retrofit();
        startLoading();

    }
    private void retrofit() {
        service = retrofit.create(GitHubService.class);
        RequestBody reqFile = RequestBody.create(MediaType.parse("video/*"), videoFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", now_Time, reqFile);

        try {
            Call<ResponseBody> req = service.postVideo(body);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("Loading_Activity", "접속 성공\n" + response.raw());
//                                        Image postMessages = response.body();
                    ResponseBody response1 = response.body();
                    Log.d("Loading_Activity", "response.body()\n" +response.body());
                    try {
                        String videoresult = response.body().string();
                        Log.d("Loading_Activity", "response.body()string()\n"+ videoresult);


                        JSONObject jsonObject = new JSONObject(videoresult);

                        real_fake = jsonObject.getString("real_fake");
                         name = jsonObject.getString("name");
                         feel1 = jsonObject.getString("feel1");
                         feel2 = jsonObject.getString("feel2");
                         feel3 = jsonObject.getString("feel3");
                         feel1_state = jsonObject.getInt("feel1_state");
                         feel2_state = jsonObject.getInt("feel2_state");
                         feel3_state = jsonObject.getInt("feel3_state");

                        Log.d("VideoActivity", "real_fake = "+ real_fake + " name = "+ name + " 1 =  " + feel1 + " 2 =  "+ feel2 + " 3 = "+ feel3);
                        Log.d("VideoActivity", "feel1 = "+ feel1_state + " feel2 = "+ feel2_state + " feel3_state " + feel3_state);

                        if (real_fake.equals("fake")) {
                            CODE = 0;
                            AlertDialog.Builder dlg = new AlertDialog.Builder(Loading_Activity.this);
                            dlg.setTitle("얼굴 인식 오류"); //제목
                            dlg.setMessage("얼굴 인식 결과 Fake로 나왔습니다. \n 가운데에 맞춰 촬영해주세요\n '확인'을 누르면 뒤로 갑니다."); // 메시지
                            dlg.setIcon(R.drawable.otp); // 아이콘 설정
//                버튼 클릭시 동작
                            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //토스트 메시지
//                                    Toast.makeText(Loading_Activity.this, "확인을 누르셨습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                            dlg.show();

                        } else {
                            CODE = 1;
                            Intent intent = new Intent(Loading_Activity.this, Otp_Activity.class);
                            intent.putExtra("real_fake", real_fake);
                            intent.putExtra("name", name);
                            intent.putExtra("feel1", feel1);
                            intent.putExtra("feel2", feel2);
                            intent.putExtra("feel3", feel3);
                            intent.putExtra("feel1_state", feel1_state);
                            intent.putExtra("feel2_state", feel2_state);
                            intent.putExtra("feel3_state", feel3_state);
                            startActivity(intent);
                        }
//                                } else if (){ 개인 디비에 올라가지 않는다면
//
//                                }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("VideoActivity", t.getMessage());
                    Log.e("VideoActivity", t.getStackTrace().toString());
                }
            });
        } catch (Exception ex) {
            Log.e("VideoActivity" + "접속조차 실패 ", ex.getMessage());
            Log.e("VideoActivity", ex.getLocalizedMessage());
        }
    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent loading = new Intent(getBaseContext(), Otp_Activity.class);
            }
        }, 300000);
    }

}