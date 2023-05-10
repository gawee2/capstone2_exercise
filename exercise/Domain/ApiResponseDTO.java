package com.mju.exercise.Domain;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class ApiResponseDTO {

    @SerializedName("code")
    private int code;
    @SerializedName("result")
    private Object result;

    public ApiResponseDTO(){

    }

    public ApiResponseDTO(int code, Object result){
        this.code = code;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
