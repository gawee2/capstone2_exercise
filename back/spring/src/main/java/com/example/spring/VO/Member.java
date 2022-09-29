package com.example.spring.VO;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String userPw;
    private String email;
    private String introduce;
    private String region;
    private String cellPhone;
    private String nickname;
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private boolean fav_soccer = false;
    private boolean fav_futsal = false;
    private boolean fav_baseball = false;
    private boolean fav_basketball = false;
    private boolean fav_badminton = false;
    private boolean fav_cycle = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isFav_soccer() {
        return fav_soccer;
    }

    public void setFav_soccer(boolean fav_soccer) {
        this.fav_soccer = fav_soccer;
    }

    public boolean isFav_futsal() {
        return fav_futsal;
    }

    public void setFav_futsal(boolean fav_futsal) {
        this.fav_futsal = fav_futsal;
    }

    public boolean isFav_baseball() {
        return fav_baseball;
    }

    public void setFav_baseball(boolean fav_baseball) {
        this.fav_baseball = fav_baseball;
    }

    public boolean isFav_basketball() {
        return fav_basketball;
    }

    public void setFav_basketball(boolean fav_basketball) {
        this.fav_basketball = fav_basketball;
    }

    public boolean isFav_badminton() {
        return fav_badminton;
    }

    public void setFav_badminton(boolean fav_badminton) {
        this.fav_badminton = fav_badminton;
    }

    public boolean isFav_cycle() {
        return fav_cycle;
    }

    public void setFav_cycle(boolean fav_cycle) {
        this.fav_cycle = fav_cycle;
    }
}
