package com.example.final_project_ui_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.RequestBody;

public class User {
    @Expose
    @SerializedName("user_name")
    private String user_name;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("otp")
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public User(String user_name, String phone) {
        this.user_name = user_name;
        this.phone = phone;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

