package com.example.final_project_ui_example;

import com.google.gson.annotations.SerializedName;

public class VideoResponse {
//    {"message": 'success', "real_fake": real_fake, 'name':name,
//    "feel1":feel1, "feel1_state":feel1_state,
//    "feel2":feel2, "feel2_state":feel2_state,
//    "feel3":feel3, "feel3_state":feel3_state})
    @SerializedName("message")
    private String message;
    @SerializedName("real_fake")
    private String real_fake;
    @SerializedName("name")
    private String name;
    @SerializedName("feel1")
    private String feel1;
    @SerializedName("feel2")
    private String feel2;
    @SerializedName("feel3")
    private String feel3;

    @SerializedName("feel1_state")
    private String feel1_state;
    @SerializedName("feel2_state")
    private String feel2_state;
    @SerializedName("feel3_state")
    private String feel3_state;

    public VideoResponse(String message, String real_fake, String name, String feel1, String feel2, String feel3, String feel1_state, String feel2_state, String feel3_state) {
        this.message = message;
        this.real_fake = real_fake;
        this.name = name;
        this.feel1 = feel1;
        this.feel2 = feel2;
        this.feel3 = feel3;
        this.feel1_state = feel1_state;
        this.feel2_state = feel2_state;
        this.feel3_state = feel3_state;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReal_fake() {
        return real_fake;
    }

    public void setReal_fake(String real_fake) {
        this.real_fake = real_fake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeel1() {
        return feel1;
    }

    public void setFeel1(String feel1) {
        this.feel1 = feel1;
    }

    public String getFeel2() {
        return feel2;
    }

    public void setFeel2(String feel2) {
        this.feel2 = feel2;
    }

    public String getFeel3() {
        return feel3;
    }

    public void setFeel3(String feel3) {
        this.feel3 = feel3;
    }

    public String getFeel1_state() {
        return feel1_state;
    }

    public void setFeel1_state(String feel1_state) {
        this.feel1_state = feel1_state;
    }

    public String getFeel2_state() {
        return feel2_state;
    }

    public void setFeel2_state(String feel2_state) {
        this.feel2_state = feel2_state;
    }

    public String getFeel3_state() {
        return feel3_state;
    }

    public void setFeel3_state(String feel3_state) {
        this.feel3_state = feel3_state;
    }
}
