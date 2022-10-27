package com.example.spring.DTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class MatchingDTO {


    private Long id;
    private String openUserId;

    private String subject;
    private String article;

    private String sportType;

    private Integer personnel; //인원
    private Double lat;
    private Double lng;
    private String playDate;
    private String playTime;



}
