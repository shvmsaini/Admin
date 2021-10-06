package com.agrobuy.app.admin.dataclasses;

import androidx.annotation.NonNull;

public class User {
    String user_ID;
    String userEmail;

    public User(String user_ID, String userEmail) {
        this.user_ID = user_ID;
        this.userEmail = userEmail;
    }


    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

}
