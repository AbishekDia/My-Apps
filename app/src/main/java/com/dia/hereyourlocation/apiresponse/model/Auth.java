package com.dia.hereyourlocation.apiresponse.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abishek on 2/13/2017.
 */

public class Auth {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
