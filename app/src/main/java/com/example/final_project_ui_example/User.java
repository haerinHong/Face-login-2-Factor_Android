package com.example.final_project_ui_example;

import java.util.Date;

public class User {
    public int user_id;
    public String account_number;
    public String user_name;
    public Date created_date;
    public String phone;
    public int istrain;

    public User() {
        super();
    }

    public User(int user_id, String account_number, String user_name, Date created_date, String phone, int istrain) {
        this.user_id = user_id;
        this.account_number = account_number;
        this.user_name = user_name;
        this.created_date = created_date;
        this.phone = phone;
        this.istrain = istrain;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIstrain() {
        return istrain;
    }

    public void setIstrain(int istrain) {
        this.istrain = istrain;
    }
}
