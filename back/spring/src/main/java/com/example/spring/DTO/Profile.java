package com.example.spring.DTO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Profile {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
