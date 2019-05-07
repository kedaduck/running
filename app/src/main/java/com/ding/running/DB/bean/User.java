package com.ding.running.DB.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName User
 * @Author Leoren
 * @Date 2019/5/6 9:05
 * Description :
 * @Version v1.0
 */
public class User extends LitePalSupport {

    private Integer id;

    private String username;

    private String password;

    public User() {
    }

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
