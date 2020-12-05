package com.example.final_project_ui_example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import android.provider.MediaStore;
import android.util.Log;
import android.media.MediaRecorder;
import android.net.Uri;
import android.content.Intent;

import android.view.View;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.conscrypt.Conscrypt;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class VideoActivity extends AppCompatActivity {
    public String TAG = "VideoActivity";
    Button takeVideo;
    VideoView videoView;
    Button btnVideoOk;
    ImageView iv_photo;
    public MediaRecorder mediaRecorder;

    Retrofit retrofit;
    GitHubService service;
    HashMap<String, Object> input;

    Uri videoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Security.insertProviderAt(Conscrypt.newProvider(), 1);

        takeVideo = findViewById(R.id.take_video_tv);
        videoView = findViewById(R.id.video_preview);
        btnVideoOk = (Button)findViewById(R.id.btnVideoOk);
        iv_photo = (ImageView)findViewById(R.id.iv_photo);

        iv_photo.bringToFront();

        takeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!requestPermission()) {
                    dispatchTakeVideoIntent();
                }
            }
        });

        //      ◆◆Retrofit2◆◆
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.22.65:8000/")
                .baseUrl("http://192.168.88.128:8000/")
                .addConverterFactory(GsonConverterFactory.create()) //아래의 service에서 callback 받는것을 자동으로 Convert 해주게 하는것
                .build();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(VideoActivity.this, new
                        String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    public void onVideoClick(View view) {
        switch (view.getId()) {
            case R.id.btnVideoOk:
                service = retrofit.create(GitHubService.class);

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat nowTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String now_Time = nowTime.format(date);

                File videoFile = new File(getPath(videoUri));
                RequestBody reqFile = RequestBody.create(MediaType.parse("video/*"), videoFile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("upload", now_Time, reqFile);

                try {
                    Call<ResponseBody> req = service.postVideo(body);
                    req.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.d("VideoActivity", "접속 성공\n" + response.raw());
//                                        Image postMessages = response.body();
                            ResponseBody response1 = response.body();
                            Log.d("VideoActivity", "response.body()\n" +response.body());
                            try {
                                String videoresult = response.body().string();
                                Log.d("VideoActivity", "response.body()string()\n"+ videoresult);



                                JSONObject jsonObject = new JSONObject(videoresult);
//                                 Log.d("VideoActivity.this", jsonObject.getString("name"));

//                                String[] js = videoresult.split(":");
//                                String tmp = "";
//                                Log.d("VideoActivity.this","js =  "+js[1] + " " + js[3] );


                                String real_fake = jsonObject.getString("real_fake");
                                String name = jsonObject.getString("name");
                                String feel1 = jsonObject.getString("feel1");
                                String feel2 = jsonObject.getString("feel2");
                                String feel3 = jsonObject.getString("feel3");
                                int feel1_state = jsonObject.getInt("feel1_state");
                                int feel2_state = jsonObject.getInt("feel2_state");
                                int feel3_state = jsonObject.getInt("feel3_state");

                                Log.d("VideoActivity", "real_fake = "+ real_fake + " name = "+ name + " 1 =  " + feel1 + " 2 =  "+ feel2 + " 3 = "+ feel3);
                                Log.d("VideoActivity", "feel1 = "+ feel1_state + " feel2 = "+ feel2_state + " feel3_state " + feel3_state);

                                if (real_fake.equals("fake")) {
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(VideoActivity.this);
                                    dlg.setTitle("얼굴 인식 오류"); //제목
                                    dlg.setMessage("얼굴 인식 결과 Fake로 나왔습니다. \n 가운데에 맞춰 촬영해주세요"); // 메시지
                                    dlg.setIcon(R.drawable.otp); // 아이콘 설정
//                버튼 클릭시 동작
                                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //토스트 메시지
                                            Toast.makeText(VideoActivity.this, "확인을 누르셨습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    dlg.show();
                                } else {
                                    Intent intent = new Intent(VideoActivity.this, Loading_Activity.class);
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
    }


    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int REQUEST_PERMISSION = 2;

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = intent.getData();
            String path = getPath(videoUri);
            String name = getName(videoUri);
            String uriId = getUriId(videoUri);
            Log.e("###", "실제경로 : " + path + "\n파일명 : " + name + "\nuri : " + videoUri.toString() + "\nuri id : " + uriId);

            videoView.bringToFront();
            videoView.setVideoURI(videoUri);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
    }
    // 실제 경로 찾기
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // 파일명 찾기
    private String getName(Uri uri)
    {
        String[] projection = { MediaStore.Images.ImageColumns.DISPLAY_NAME };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // uri 아이디 찾기
    private String getUriId(Uri uri)
    {
        String[] projection = { MediaStore.Images.ImageColumns._ID };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    requestPermission();
                    return;
                }
            }

            dispatchTakeVideoIntent();
        }
    }

    private boolean requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_PERMISSION);

            return true;
        }

        return false;
    }


}