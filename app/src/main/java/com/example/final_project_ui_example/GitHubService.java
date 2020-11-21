package com.example.final_project_ui_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
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


    @POST("{register}")
    Call<User> postPeople(
            @Path("register") String register,
            @Body HashMap<String, Object> param
    );
//  안돌아가 이거ㅜㅜ
//    @GET("{register_get}")
//    Call<List<User>> getPeople(@Path("register_get") String register,
//                         @Body HashMap<String, Object> param);

//    이거는 돌아감
//    @GET("{register}")
//    Call<List<Repo>> listRepos(@Path("register") String register);
//                               @Body HashMap<String, Object> param);
//                                       String user);

    //    중복여부 확인
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