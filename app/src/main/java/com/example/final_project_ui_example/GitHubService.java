package com.example.final_project_ui_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
//
//    Call<List<Repo>> listRepos(
//            @Path("user") String user,
//            @Query("user_name") String name,
//            @Query("phone") String phone,
//            @Body User people);

//    @GET("{user}")
//@HTTP(method = "GET", path = "{user}", hasBody = true)
//        Call<List<User>> checkUserValid(
//            @Path("user") String user,
//            @Body User people);

public interface GitHubService {
    // GET 방식
    @POST("{image}")
    Call<Image> getimage(
            @Path("image") String imagepath,
            @Body HashMap<String, Object> param
//            @Query("imagefile") String imagefile
    );
// 로그인시 비디오 전송
    @Multipart
    @POST("img_test/")
    Call<ResponseBody> postVideo(
            @Part MultipartBody.Part image);
//    회원가입
    @Multipart
    @POST("register/")
    Call<ResponseBody> postPeople (
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part user_name,
            @Part MultipartBody.Part phone
    );

    //   회원가입시 중복여부 확인
    @POST("{register1}")
    Call<User> postDuplicatedPeople(
            @Path("register1") String register,
            @Body HashMap<String, Object> param
    );



    @POST("{otp_url}")
    Call<User> postOTP(
            @Path("otp_url") String otp_url,
            @Body HashMap<String, Object> param
    );

    @POST("{otp_url2}")
    Call<User> postOTP2(
            @Path("otp_url2") String otp_url2,
            @Body HashMap<String, Object> param
    );

//    @GET("{register}}")
//    Call<List<Repo>> listRepos(@Path("register") String register,
//    @Body HashMap<String, Object> param);
//                                       String user);
////
//    @GET("register")
//    Call<List<User>> listPeople(
//            @Body User people
//    );
//
//    @FormUrlEncoded
//    @GET("register")
//    Call<User> getPeople(
//            @Body HashMap<String, Object> param
//    );
////
//    @GET("register")
//    Call<User> getPeople(
//            @Body HashMap<String, Object> param
//    );
//


}

//class RequestPut {
//    @SerializedName("user_name")
//    private String userName;
//
//    @SerializedName("phone")
//    private String phone;
//
//
//    @SerializedName("message")
//    private String message;
//
//    @SerializedName("otp")
//    private String otp;
//
//    public String getOtp() {
//        return otp;
//    }
//
//    public void setOtp(String otp) {
//        this.otp = otp;
//    }
//
//    public RequestPut(HashMap<String, Object> parameters) {
//       this.userName = (String) parameters.get("user_name");
//       this.phone = (String) parameters.get("phone");
//   }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//}



class Repo{
    @SerializedName("user_name")
    private String userName;

    @SerializedName("phone")
    private String phone;


    @SerializedName("message")
    private String message;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}