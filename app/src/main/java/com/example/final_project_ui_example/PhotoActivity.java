package com.example.final_project_ui_example;


import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
//import static com.example.final_project_ui_example.Loading_Activity.LOADING;




public class PhotoActivity extends Activity {
    final private static String TAG = "haerin";
    final private static int OTP_ACTIVITY = 500;
    final private static int LOADING_ACTIVITY = 560;
    Button btn_photo;
    Button btn_ok;
    ImageView iv_photo;
    final static int TAKE_PICTURE = 1;
    String byteArray;


    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;
    public final static int LOADING = 560;
    GitHubService service;
    int photo_result = 0;
    Retrofit retrofit;
    HashMap<String, Object> input;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat nowTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    // nowDate 변수에 값을 저장한다.
    String now_Time = nowTime.format(date);
    File f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        iv_photo = findViewById(R.id.iv_photo);
        btn_photo = findViewById(R.id.btn_photo);
        btn_ok = findViewById(R.id.btn_send);
        iv_photo.bringToFront();

//      ◆◆Retrofit2◆◆
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.22.67:8000/")
                .baseUrl("http://192.168.22.65:8000/")
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
                ActivityCompat.requestPermissions(PhotoActivity.this, new
                        String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_photo:
//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                        dispatchTakePictureIntent();
                        break;
                }
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_send:
                        Log.d("여기1", Integer.toString(photo_result));
                        //                        ◆◆ 레트로핏 통신 ◆◆
                        if (photo_result == 1) {
                            //       OTP를 받아오는 레트로핏 통신
                            service = retrofit.create(GitHubService.class);

                            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", now_Time, reqFile);


                            try {
                                input = new HashMap<>();
                                input.put("img", byteArray);

                                int byte_len = byteArray.length();
                                Log.d("PhotoActivity 길이길이", Integer.toString(byte_len));

                                Call<okhttp3.ResponseBody> req = service.postVideo(body);
                                req.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Log.d("PhotoActivity", "접속 성공\n" + response.raw());
//                                        Image postMessages = response.body();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.e("ResponseBody", t.getMessage());
                                        Log.e("PhotoActivity", t.getStackTrace().toString());
                                    }
                                });

//                                service.getimage("img/", input).enqueue(new Callback<Image>() {
//                                    @Override
//                                    public void onResponse(Call<Image> call, Response<Image> response) {
//                                        Log.d("PhotoActivity", "접속 성공\n" + response.raw());
//                                        Image postMessages = response.body();
//                                        Log.d("PhotoActivity", "Photo ___ Retrofit2 Test : " + postMessages.getMessage());
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Image> call, Throwable t) {
//                                        Log.e("PhotoActivity" + "photo failure", t.getMessage());
//                                        Log.e("PhotoActivity", t.getStackTrace().toString());
//                                    }
//                                });
                            } catch (Exception ex) {
                                Log.e("PhotoActivity" + "접속조차 실패 ", ex.getMessage());
                                Log.e("PhotoActivity", ex.getLocalizedMessage());
                            }

                        }
                        Toast.makeText(PhotoActivity.this, "당신의 바이트는 " + byteArray, Toast.LENGTH_SHORT).show();




                        Intent loading_intent = new Intent(PhotoActivity.this, Loading_Activity.class);
                        startActivity(loading_intent);
//                        startActivityForResult(loading_intent, LOADING_ACTIVITY);
//                        Intent otp_intent = new Intent(PhotoActivity.this, Otp_Activity.class);
//                        startActivityForResult(otp_intent, OTP_ACTIVITY);
                }
            }
        });
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

    public String bitmapToByteArray(Bitmap bitmapPicture) throws IOException {
        String encodedImage;
//        Create a file to write bitmap data
        f = new File(getBaseContext().getCacheDir(), now_Time);
        f.createNewFile();

//        Convert bitmap to byte array

        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(b);
        fos.flush();
        fos.close();

        return encodedImage;
    }
//    private String getStringFromBitmap(Bitmap bitmapPicture) {
//    String encodedImage;
//    ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
//    bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
//    byte[] b = byteArrayBitmapStream.toByteArray();
//    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//    return encodedImage;
//}

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
                                    iv_photo.setImageBitmap(bitmap);
                                    byteArray = bitmapToByteArray(bitmap);
                                    Log.d(TAG, "당신의 바이트는 " + byteArray);
                                    photo_result = 1;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) {
                                    iv_photo.setImageBitmap(bitmap);
                                    byteArray = bitmapToByteArray(bitmap);
                                    Log.d(TAG, "당신의 바이트는 " + byteArray);
                                    photo_result = 1;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                }
                case LOADING_ACTIVITY: {
                    if (resultCode == LOADING) {
                        Log.d("여기2 ", byteArray);
                        Intent otp_intent = new Intent(PhotoActivity.this, Otp_Activity.class);
                        startActivityForResult(otp_intent, OTP_ACTIVITY);
                    }
                }
            }
        }  catch (Exception error) {
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
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int c = findFrontFacingCamera();
        if (c < 0) {
            Toast.makeText(PhotoActivity.this, "No  camera found.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(PhotoActivity.this, "camera_ id " + c,
                    Toast.LENGTH_LONG).show();
        }
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

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d("Camera ERROR", "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }
}
