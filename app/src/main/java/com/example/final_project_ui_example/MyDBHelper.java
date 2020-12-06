package com.example.final_project_ui_example;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {


    public static final String DB_NAME= "my_db";
    public static final String TABLE_USER = "User";
    public static final String TABLE_OTP = "OTP";
    public static final String TABLE_FACE = "Face";
    public static final String TABLE_EMOTION = "Emotion";




    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE DATABASE study_db default CHARACTER SET UTF8;

        String create_user_table = "CREATE TABLE "+ TABLE_USER +"(\n" +
                "  user_id INT NOT NULL,\n" +
                "  account_number varchar(30),\n" +
                "  user_name varchar(10),\n" +
                "  created_date datetime,\n" +
                "  phone char(12) NOT NULL,\n" +
                "  istrain int,\n" +
                "  otp_id VARCHAR(100),\n" +
                "  PRIMARY KEY (user_id, phone)\n" +
                ")";
        String create_face = "CREATE TABLE"+  TABLE_FACE +"(\n" +
                "  user_id INT NOT NULL,\n" +
                "  image_path varchar(255),\n" +
                "  image_register_date datetime\n" +
                ")";
        String create_otp = "CREATE TABLE OTP (\n" +
                "\totp_id VARCHAR(100) NOT NULL PRIMARY KEY,\n" +
                "    sent_date datetime ,\n" +
                "    phone char(12)\n" +
                ")";
        String create_emotion = "CREATE TABLE Emotion (\n" +
                "  emotion_state int(6) PRIMARY KEY,\n" +
                "  emtion_image_path varchar(1000),\n" +
                "  phone char(12)\n" +
                ")";

        db.execSQL(create_user_table);
        db.execSQL(create_face);
        db.execSQL(create_otp);
        db.execSQL(create_emotion);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
