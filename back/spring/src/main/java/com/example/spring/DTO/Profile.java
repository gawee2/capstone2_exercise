package com.example.spring.DTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String userId;
    private String nickname;
    private String image;
    private String region;
    private String introduce;

    private boolean favMon;
    private boolean favTue;
    private boolean favWed;
    private boolean favThu;
    private boolean favFri;
    private boolean favSat;
    private boolean favSun;

    private boolean favSoccer;
    private boolean favFutsal;
    private boolean favBaseball;
    private boolean favBasketball;
    private boolean favBadminton;
    private boolean favCycle;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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




}
