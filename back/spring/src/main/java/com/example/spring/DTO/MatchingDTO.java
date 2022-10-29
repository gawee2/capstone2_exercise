package com.example.spring.DTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MatchingDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long openMatchId;

    //이 부분 member에서는 String으로 했는데 여기서는 idx로 하려니까 헷갈림 통일해야할듯
    //전부 인덱스값으로 처리하고 클라이언트에서 인덱스 값을 가지고 있는게 좋을듯
    private Long userId;


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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
