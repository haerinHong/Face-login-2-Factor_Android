package com.example.final_project_ui_example;

import java.util.Date;

public class OTP {
    public Date sent_date;
    public String phone;
    public String user_id;

    public OTP() {
        super();
    }

    public OTP(Date sent_date, String phone, String user_id) {
        this.sent_date = sent_date;
        this.phone = phone;
        this.user_id = user_id;
    }

    public Date getSent_date() {
        return sent_date;
    }

    public void setSent_date(Date sent_date) {
        this.sent_date = sent_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
