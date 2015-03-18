package com.example.shane.winningedge;

/**
 * Created by Shane on 18/03/2015.
 */
public class User {
    private int mUserId;
    public void setUserId(int id)
    {
        mUserId = id;
    }
    public int getUserId()
    {
        return mUserId;
    }

    private String mEmail;
    public void setEmail(String email)
    {
        mEmail = email;
    }
    public String getEmail()
    {
        return mEmail;
    }

    private String mPassword;
    public void setPassword(String password)
    {
        mPassword = password;
    }
    public String getPassword()
    {
        return mPassword;
    }
    // Constructor
    public User()
    {

    }
    public User(int id, String email, String password)
    {
        setUserId(id);
        setEmail(email);
        setPassword(password);
    }
}
