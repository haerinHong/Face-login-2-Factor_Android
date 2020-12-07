package com.example.final_project_ui_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    String name;
    String phone;
    TextView textName;
    EditText etName;
    EditText etPhone;
    ImageView ivcamera;
    ImageView ivRegisterFace;
    Button btnImageUpload;
    Button btnCheckDuplicate;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int REQUEST_PERMISSION = 2;
    VideoView video_preview;
    Uri videoUri;

    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;
    final static int IMAGE_UP_LOAD = 116;
    final private static String TAG = "Register_haerin";

//  Register Retrofit2 실행하는 서비스
    GitHubService service;
    HashMap<String, Object> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = (EditText)findViewById(R.id.etName);
        etPhone = (EditText)findViewById(R.id.etPhone);
        ivcamera = (ImageView)findViewById(R.id.ivcamera);
        ivRegisterFace = (ImageView)findViewById(R.id.ivRegisterFace);
        btnImageUpload = (Button)findViewById(R.id.btnImageUpload);
        btnCheckDuplicate = (Button)findViewById(R.id.btnCheckDuplicate);
        video_preview = (VideoView)findViewById(R.id.video_preview);

        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.88.128:8000/")
                .addConverterFactory(GsonConverterFactory.create()) //아래의 service에서 callback 받는것을 자동으로 Convert 해주게 하는것
                .build();

        ivcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!requestPermission()) {
                    dispatchTakeVideoIntent();
                }
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(RegisterActivity.this, new
                        String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

//        final User chosun = new User("초선이", "01022223303");
        input = new HashMap<>();
//        input.put("user_name", chosun.getUserName());
//        input.put("phone", chosun.getPhone());

        service = retrofit.create(GitHubService.class);

        btnCheckDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    input.put("user_name",etName.getText().toString() );
                    input.put("phone", etPhone.getText().toString());
                    service.postDuplicatedPeople("register1/",input).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                Log.d("RegisterActivity " + "check Duplicate", "접속 성공\n" + response.raw());

                                User postMessages = response.body();
                                String duplicated_res = postMessages.getMessage();
                                if (duplicated_res.equals("중복")) {
                                    Log.d("RegisterActivity" + "check Duplicate", "중복이다");
                                    TastyToast.makeText(RegisterActivity.this, "중복된 전화번호입니다", TastyToast.LENGTH_LONG,
                                            TastyToast.ERROR);
                                } else {
                                    Log.d("RegisterActivity" + "check Duplicate", "중복이 아니다.");
                                    TastyToast.makeText(RegisterActivity.this, etPhone.getText().toString() + "\n사용가능한 전화번호 입니다.", TastyToast.LENGTH_LONG,
                                            TastyToast.SUCCESS);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                            Log.e("MainActivity" + "실패1",t.getMessage());
                            Log.e("MainActivity",t.getStackTrace().toString());
                        }
                    });
                }catch (Exception ex) {
                    Log.e("MainActivity" + "실패2 ", ex.getMessage());
                    Log.e("MainActivity", ex.getLocalizedMessage());
                }

            }
            });

    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = intent.getData();
            String path = getPath(videoUri);
            String name = getName(videoUri);
            String uriId = getUriId(videoUri);
            Log.e("###", "실제경로 : " + path + "\n파일명 : " + name + "\nuri : " + videoUri.toString() + "\nuri id : " + uriId);

            video_preview.bringToFront();
            video_preview.setVideoURI(videoUri);
            video_preview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
    }


    public void onRegisterClick(View view) {
        switch(view.getId()) {
            case R.id.btnImageUpload: //사진을 앨범에서 가져오는 버튼.
//                Intent ImageUpload = new Intent();
//                ImageUpload.setType("image/*");
//                ImageUpload.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(ImageUpload, IMAGE_UP_LOAD);
                break;


            case R.id.ivRegisterFace : //사진 업로드시 보여줘야할 imageview
                break;

            case R.id.btnRegister: // db연결, 추가 후, Toast로 띄워준다.
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat nowTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String now_Time = nowTime.format(date);

                File videoFile = new File(getPath(videoUri));
                RequestBody reqFile = RequestBody.create(MediaType.parse("video/*"), videoFile);
                MultipartBody.Part video_body = MultipartBody.Part.createFormData("upload", now_Time, reqFile);

//                ◆◆Retrofit Post 연결◆◆
                String input_name = etName.getText().toString();
                String input_phone = etPhone.getText().toString();

                final User chosun = new User(input_name, input_phone);
//                input = new HashMap<>();
                input.put("user_name", chosun.getUserName());
                input.put("phone", chosun.getPhone());

                RequestBody req_name = RequestBody.create(MediaType.parse("text/plain"),chosun.getUserName());
                RequestBody req_phone = RequestBody.create(MediaType.parse("text/plain"),chosun.getPhone());
                MultipartBody.Part multi_user_name = MultipartBody.Part.createFormData("user_name", now_Time, req_name);
                MultipartBody.Part multi_user_phone = MultipartBody.Part.createFormData("phone", now_Time, req_phone);
                try {
                    Call<ResponseBody> req = service.postPeople(video_body, multi_user_name, multi_user_phone);
                    req.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.d("RegisterActivity", "접속 성공\n" + response.raw());

                            String registerresult = null;
                            try {
                                registerresult = response.body().string();
                                Log.d("RegisterActivity", "response.body()string()\n"+ registerresult);
                                JSONObject jsonObject = new JSONObject(registerresult);
                                String register_final_result = jsonObject.getString("message");
                                Log.d("VideoActivity", " register_final_result = " + register_final_result );

                                if (register_final_result.equals("등록 완료")) {
                                    Intent register_intent = new Intent();
                                    register_intent.putExtra("name", etName.getText().toString());
                                    register_intent.putExtra("phone", etPhone.getText().toString());
                                    setResult(1000, register_intent);

                                    Log.d("REGISTER_ACTIVITY 성공 축하합니다", etName.getText().toString() + " phone = "+ etPhone.getText().toString());
                                    finish();
                                } else {
                                    Log.d("RegisterActivity", "접속은 했지만 등록은 실패" + response.raw());
                                }

                            } catch (IOException | JSONException e) {
                                Log.d("RegisterActivity", "접속은 했지만 등록은 실패" + response.raw());
                                e.printStackTrace();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            registerfail(1);
                        }
                    });

                }catch (Exception ex) {
                    Log.e("RegisterActivity" + "실패2 ", ex.getMessage());
                    Log.e("RegisterActivity", ex.getLocalizedMessage());

//                  등록 실패시 AlertDialog를 띄워주는 함수
                    registerfail(2);
                }
                break;

            case R.id.btnCancelRegister:
                finish();
                break;
        }
    }
    public void registerfail(int code) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterActivity.this);
        dlg.setTitle("안내 메세지"); //제목
        final String error1 = "서버 접속은 했으나 실패했습니다.";
        final String error2 = "서버 접속에 실패했습니다.";
        dlg.setIcon(R.drawable.registeralertdialog);

        if (code == 1) {
            dlg.setMessage("회원가입 등록 오류"); // 메시지
            dlg.setPositiveButton(error1,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지
                    Toast.makeText(RegisterActivity.this,error1+"확인하셨습니다..", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (code==2) {
            dlg.setMessage("회원가입 등록 오류"); // 메시지
            dlg.setPositiveButton(error2,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지
                    Toast.makeText(RegisterActivity.this,error2+"확인하셨습니다..", Toast.LENGTH_SHORT).show();

                }
            });
        }

//        dlg.setIcon(R.drawable.); // 아이콘 설정
//                버튼 클릭시 동작
        dlg.setPositiveButton("동의",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //토스트 메시지
                Toast.makeText(RegisterActivity.this,"동의하셨습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        dlg.setNegativeButton("거절", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(RegisterActivity.this,"거절하셨습니다. \n Facein 회원가입이 어렵습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        dlg.show();
    }



}