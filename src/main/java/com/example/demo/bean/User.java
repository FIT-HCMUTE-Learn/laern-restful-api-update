package com.example.demo.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min=2, message = "Name should have at least 2 characters")
    private String name;

    @NotNull
    @Past(message = "Birth Date should be in the past")
    private Date birthday;

    @NotNull
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User() {

    }

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

    public @NotNull List<Post> getPosts() {
        return posts;
    }

    public void setPosts(@NotNull List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return String.format("User [id=%s, name=%s, birthDate=%s]", id, name, birthday);
    }
}
