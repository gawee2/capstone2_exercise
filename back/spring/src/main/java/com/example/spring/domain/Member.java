package com.example.spring.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    private boolean fav_soccer = false;
    private boolean fav_futsal = false;
    private boolean fav_baseball = false;
    private boolean fav_basketball = false;
    private boolean fav_badminton = false;
    private boolean fav_cycle = false;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
