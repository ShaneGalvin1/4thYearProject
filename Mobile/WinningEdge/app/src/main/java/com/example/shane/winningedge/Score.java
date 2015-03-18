package com.example.shane.winningedge;
import java.util.UUID;
/**
 * Created by Shane on 18/03/2015.
 */
public class Score {
    //String uniqueID = UUID.randomUUID().toString();
    private int mScoreId;
    public void setScoreId(int id)
    {
        mScoreId = id;
    }
    public int getScoreId()
    {
        return mScoreId;
    }

    private boolean mGoal;
    public void setGoal(boolean b)
    {
        mGoal = b;
    }
    public boolean getGoal()
    {
        return mGoal;
    }

    private boolean mFromPlay;
    public void setFromPlay(boolean b)
    {
        mFromPlay = b;
    }
    public boolean getFromPlay()
    {
        return mFromPlay;
    }

    private int mMins;
    public void setMins(int in)
    {
        mMins = in;
    }
    public int getMins()
    {
        return mMins;
    }

    private int mSecs;
    public void setSecs(int in)
    {
        mSecs = in;
    }
    public int getSecs()
    {
        return mSecs;
    }

    private int mDistance;
    public void setDistance(int in)
    {
        mDistance = in;
    }
    public int getDistanced()
    {
        return mDistance;
    }

    // Associate Scores with a particular match
    private int mMatchId;
    public void setMatchId(int id)
    {
        mMatchId = id;
    }
    public int getMatchId()
    {
        return mMatchId;
    }
}
