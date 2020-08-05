package com.wuguangxin.utils.demo;

import java.io.Serializable;

/**
 * Created by wuguangxin on 2020/8/1.
 */
public class UserBean implements Serializable {

    private static final long serialVersionUID = -2600179427363046440L;

    private String id;
    private String username;

    public UserBean(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserBean{");
        sb.append("id='").append(id).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
