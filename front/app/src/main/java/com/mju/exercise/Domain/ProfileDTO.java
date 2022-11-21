package com.mju.exercise.Domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileDTO implements Serializable {

    @SerializedName("idx")
    private Long idx;

    @SerializedName("userId")
    private String userID;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("image")
    private String image;
    @SerializedName("region")
    private String region;
    @SerializedName("introduce")
    private String introduce;

    @SerializedName("favMon")
    private boolean favMon;
    @SerializedName("favTue")
    private boolean favTue;
    @SerializedName("favWed")
    private boolean favWed;
    @SerializedName("favThu")
    private boolean favThu;
    @SerializedName("favFri")
    private boolean favFri;
    @SerializedName("favSat")
    private boolean favSat;
    @SerializedName("favSun")
    private boolean favSun;

    @SerializedName("favSoccer")
    private boolean favSoccer;
    @SerializedName("favFutsal")
    private boolean favFutsal;
    @SerializedName("favBaseball")
    private boolean favBaseball;
    @SerializedName("favBasketball")
    private boolean favBasketball;
    @SerializedName("favBadminton")
    private boolean favBadminton;
    @SerializedName("favCycle")
    private boolean favCycle;

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isFavMon() {
        return favMon;
    }

    public void setFavMon(boolean favMon) {
        this.favMon = favMon;
    }

    public boolean isFavTue() {
        return favTue;
    }

    public void setFavTue(boolean favTue) {
        this.favTue = favTue;
    }

    public boolean isFavWed() {
        return favWed;
    }

    public void setFavWed(boolean favWed) {
        this.favWed = favWed;
    }

    public boolean isFavThu() {
        return favThu;
    }

    public void setFavThu(boolean favThu) {
        this.favThu = favThu;
    }

    public boolean isFavFri() {
        return favFri;
    }

    public void setFavFri(boolean favFri) {
        this.favFri = favFri;
    }

    public boolean isFavSat() {
        return favSat;
    }

    public void setFavSat(boolean favSat) {
        this.favSat = favSat;
    }

    public boolean isFavSun() {
        return favSun;
    }

    public void setFavSun(boolean favSun) {
        this.favSun = favSun;
    }

    public boolean isFavSoccer() {
        return favSoccer;
    }

    public void setFavSoccer(boolean favSoccer) {
        this.favSoccer = favSoccer;
    }

    public boolean isFavFutsal() {
        return favFutsal;
    }

    public void setFavFutsal(boolean favFutsal) {
        this.favFutsal = favFutsal;
    }

    public boolean isFavBaseball() {
        return favBaseball;
    }

    public void setFavBaseball(boolean favBaseball) {
        this.favBaseball = favBaseball;
    }

    public boolean isFavBasketball() {
        return favBasketball;
    }

    public void setFavBasketball(boolean favBasketball) {
        this.favBasketball = favBasketball;
    }

    public boolean isFavBadminton() {
        return favBadminton;
    }

    public void setFavBadminton(boolean favBadminton) {
        this.favBadminton = favBadminton;
    }

    public boolean isFavCycle() {
        return favCycle;
    }

    public void setFavCycle(boolean favCycle) {
        this.favCycle = favCycle;
    }

}
