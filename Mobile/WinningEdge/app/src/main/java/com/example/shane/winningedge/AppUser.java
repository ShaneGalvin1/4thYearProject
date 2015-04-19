package com.example.shane.winningedge;

import android.app.Application;

/**
 * Created by Shane on 16/04/2015.
 */
public class AppUser extends Application{
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public void removeUser() {
        mUser = null;
    }
}
