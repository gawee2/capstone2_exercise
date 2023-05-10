package com.mju.exercise.Domain;

import com.google.gson.annotations.SerializedName;

public class MatchingDTO {

    @SerializedName("id")
    private Long id;

    @SerializedName("openMatchId")
    private Long openMatchId;

    @SerializedName("userIndex")
    private Long userIndex;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOpenMatchId() {
        return openMatchId;
    }

    public void setOpenMatchId(Long openMatchId) {
        this.openMatchId = openMatchId;
    }

    public Long getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(Long userIndex) {
        this.userIndex = userIndex;
    }
}
