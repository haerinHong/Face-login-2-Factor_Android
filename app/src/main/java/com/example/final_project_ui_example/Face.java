package com.example.final_project_ui_example;

import java.util.Date;

public class Face {
    public int user_id;
    public String image_path;
    public Date image_register_date;

    public Face() {
        super();
    }

    public Face(int user_id, String image_path, Date image_register_date) {
        this.user_id = user_id;
        this.image_path = image_path;
        this.image_register_date = image_register_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Date getImage_register_date() {
        return image_register_date;
    }

    public void setImage_register_date(Date image_register_date) {
        this.image_register_date = image_register_date;
    }
}
