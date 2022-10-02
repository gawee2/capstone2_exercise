package com.mju.exercise.Domain;

import com.google.gson.annotations.SerializedName;

public class SignUpDTO {

    @SerializedName("userId")
    private String userId;
    @SerializedName("userPw")
    private String userPw;
    @SerializedName("email")
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
