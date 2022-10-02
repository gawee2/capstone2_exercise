package com.mju.exercise.Domain;

import com.google.gson.annotations.SerializedName;

public class ApiResponseDTO {

    @SerializedName("code")
    private int code;
    @SerializedName("result")
    private ApiResponseResultDTO result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ApiResponseResultDTO getResult() {
        return result;
    }

    public void setResult(ApiResponseResultDTO result) {
        this.result = result;
    }
}
