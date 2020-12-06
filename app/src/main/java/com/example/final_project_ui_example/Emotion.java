package com.example.final_project_ui_example;

public class Emotion {
    public int emotion_state;
    public String emtion_image_path;
    public String phone;

    public Emotion() {
        super();
    }

    public Emotion(int emotion_state, String emtion_image_path, String phone) {
        this.emotion_state = emotion_state;
        this.emtion_image_path = emtion_image_path;
        this.phone = phone;
    }

    public int getEmotion_state() {
        return emotion_state;
    }

    public void setEmotion_state(int emotion_state) {
        this.emotion_state = emotion_state;
    }

    public String getEmtion_image_path() {
        return emtion_image_path;
    }

    public void setEmtion_image_path(String emtion_image_path) {
        this.emtion_image_path = emtion_image_path;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
