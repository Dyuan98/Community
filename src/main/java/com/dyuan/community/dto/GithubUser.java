package com.dyuan.community.dto;

/**
 * @author dyuan
 * @date 2020/1/22 22:46
 */

public class GithubUser {
    private String user;
    private long id;
    private String bio;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
