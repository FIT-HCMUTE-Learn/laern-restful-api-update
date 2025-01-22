package com.example.demo.bean;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class User {
    private Integer id;

    @Size(min=2, message = "Name should have at least 2 characters")
    private String name;

    @Past(message = "Birth Date should be in the past")
    private Date birthday;

    public User(Integer id, String name, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return String.format("User [id=%s, name=%s, birthDate=%s]", id, name, birthday);
    }
}
