package com.mju.exercise.Domain;

import com.google.gson.annotations.SerializedName;

public class ApiLoginResponseDTO {

    @SerializedName("refreshIdx")
    private int refreshIdx;
    @SerializedName("accessToken")
    private String accessToken;

    public int getRefreshIdx() {
        return refreshIdx;
    }

    public void setRefreshIdx(int refreshIdx) {
        this.refreshIdx = refreshIdx;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
