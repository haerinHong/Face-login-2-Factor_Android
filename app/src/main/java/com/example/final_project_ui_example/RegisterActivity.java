package com.example.final_project_ui_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


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
    String byteArray;
    Button btnCheckDuplicate;

    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;
    final static int IMAGE_UP_LOAD = 116;
    final private static String TAG = "Register_haerin";

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("ename");
//    DatabaseReference conditionRefPhone = mRootRef.child("phone");

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

        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.88.128:8000/")
                .addConverterFactory(GsonConverterFactory.create()) //아래의 service에서 callback 받는것을 자동으로 Convert 해주게 하는것
                .build();


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
                                    TastyToast.makeText(getApplicationContext(), "중복된 전화번호입니다", TastyToast.LENGTH_LONG,
                                            TastyToast.ERROR);
                                } else {
                                    Log.d("RegisterActivity" + "check Duplicate", "중복이 아니다.");
                                    TastyToast.makeText(getApplicationContext(), etPhone.getText().toString() + "\n사용가능한 전화번호 입니다.", TastyToast.LENGTH_LONG,
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


    public void onRegisterClick(View view) {
        switch(view.getId()) {
//            case R.id.btnCheckDuplicate : // 전화번호를 db 내 검색해 중복 값 확인
//                Toast.makeText(RegisterActivity.this, etPhone.getText().toString() + "\n사용가능한 전화번호 입니다.", Toast.LENGTH_SHORT).show();
//                if('중복 아님')
//                ◆◆Retrofit 합체!!◆◆


//              예쁜 Toast
//                TastyToast.makeText(getApplicationContext(), etPhone.getText().toString()+"\n등록이 완료되었습니다", TastyToast.LENGTH_LONG,
//                        TastyToast.SUCCESS);
////                else ('중복 일때')
//                TastyToast.makeText(getApplicationContext(), "중복된 전화번호입니다", TastyToast.LENGTH_LONG,
//                        TastyToast.ERROR);
//                break;
            case R.id.btnImageUpload: //사진을 앨범에서 가져오는 버튼.
                Intent ImageUpload = new Intent();
                ImageUpload.setType("image/*");
                ImageUpload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(ImageUpload, IMAGE_UP_LOAD);
                break;


            case R.id.ivRegisterFace : //사진 업로드시 보여줘야할 imageview
                break;
            case R.id.btnRegister: // db연결, 추가 후, Toast로 띄워준다.
                conditionRef.setValue(etName.getText().toString());
//                conditionRefPhone.setValue(phone);

//                ◆◆Retrofit Post 연결◆◆
                String input_name = etName.getText().toString();
                String input_phone = etPhone.getText().toString();

                final User chosun = new User(input_name, input_phone);
//                input = new HashMap<>();
                input.put("user_name", chosun.getUserName());
                input.put("phone", chosun.getPhone());

                try {
                    service.postPeople("register/", input).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()) {
                                Log.d("RegisterActivity", "접속 성공\n" + response.raw());
                                User postMessages= response.body();
                                Log.d("RegisterActivity" , "Retrofit2 Test : "+postMessages.getMessage());

//                                제대로 회원가입 했는지 여부
                                String register_res = postMessages.getMessage();

                                if (register_res.equals("등록 성공")) {
                                    Intent register_intent = new Intent();
                                    register_intent.putExtra("name", etName.getText().toString());
                                    register_intent.putExtra("phone", etPhone.getText().toString());
                                    setResult(1000, register_intent);

                                    Log.d("REGISTER_ACTIVITY 성공 축하합니다", etName.getText().toString() + " phone = "+ etPhone.getText().toString());
                                    finish();
                                } else {
                                    Log.d("RegisterActivity", "접속은 했지만 등록은 실패" + response.raw());
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("RegisterActivity" + "실패1",t.getMessage());
                            Log.e("RegisterActivity",t.getStackTrace().toString());

//                            등록 실패시 AlertDialog를 띄워주는 함수
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
            case R.id.ivcamera:
                dispatchTakePictureIntent();
                break;
        }
    }
    public void registerfail(int code) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(RegisterActivity.this);
        dlg.setTitle("안내 메세지"); //제목
        String error1 = "서버 접속은 했으나 실패했습니다.";
        String error2 = "서버 접속에 실패했습니다.";
        dlg.setIcon(R.drawable.registeralertdialog);

        if (code == 1) {
            dlg.setMessage("회원가입 등록 오류"); // 메시지
            dlg.setPositiveButton(error1,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지
                    Toast.makeText(RegisterActivity.this,"확인하셨습니다..", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (code==2) {
            dlg.setMessage("회원가입 등록 오류"); // 메시지
            dlg.setPositiveButton(error2,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지
                    Toast.makeText(RegisterActivity.this,"확인하셨습니다..", Toast.LENGTH_SHORT).show();

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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try { photoFile = createImageFile(); }
            catch (IOException ex) {}
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.final_project_ui_example.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        //                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;
//                        = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) {
                                    ivRegisterFace.setImageBitmap(bitmap);
                                    byteArray = bitmapToByteArray(bitmap);
                                    Log.d(TAG, "당신의 바이트는 " + byteArray);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) {
                                    ivRegisterFace.setImageBitmap(bitmap);
                                    byteArray = bitmapToByteArray(bitmap);
                                    Log.d(TAG, "당신의 바이트는 " + byteArray);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                }
                case IMAGE_UP_LOAD:
                    if (resultCode == RESULT_OK) {
                        try {
                            InputStream in = getContentResolver().openInputStream(intent.getData());
                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();
                            ivRegisterFace.setImageBitmap(img);
                        } catch (Exception e) {e.printStackTrace();}
                    } else if (requestCode == RESULT_CANCELED) {
                        Toast.makeText(RegisterActivity.this, "사진 선택 취소", Toast.LENGTH_LONG).show();
                    }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
    //  촬영된 사진을 이미지 파일로 저장하는 함수 createImageFile()
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd__HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + " was " + grantResults[0]);
        }
    }

    public String bitmapToByteArray(Bitmap bitmapPicture) {
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

}