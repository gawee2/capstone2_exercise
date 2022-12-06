package com.example.spring.DTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class OpenMatchDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //오픈매치 인덱스, 이거로 매칭 테이블에서 해당 유저와 오픈 매치와의 관계 파악할 예정

    private String openUserId;
    private LocalDateTime openTime;

    private String subject;
    private String article;

    private String sportType;
    private Integer personnel; //인원

    private LocalDateTime playDateTime;

    private Double lat;
    private Double lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public Integer getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Integer personnel) {
        this.personnel = personnel;
    }


    public LocalDateTime getPlayDateTime() {
        return playDateTime;
    }

    public void setPlayTime(LocalDateTime playDateTime) {
        this.playDateTime = playDateTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
