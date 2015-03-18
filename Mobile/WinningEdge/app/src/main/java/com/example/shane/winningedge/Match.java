package com.example.shane.winningedge;

import java.util.Date;
import java.util.List;

/**
 * Created by Shane on 18/03/2015.
 */
public class Match {

    // Associates matches with user
    private int mUserId;
    public void setUserId(int id)
    {
        mUserId = id;
    }
    public int getUserId()
    {
        return mUserId;
    }
    // Match Info
    private int mMatchId;
    public void setMatchId(int id)
    {
        mMatchId = id;
    }
    public int getMatchId()
    {
        return mMatchId;
    }

    private Date mMatchTime;
    public void setMatchTime(Date time)
    {
        mMatchTime = time;
    }
    public Date getMatchTime()
    {
        return mMatchTime;
    }

    private String mOppostion;
    public void setOpposition(String opp)
    {
        mOppostion = opp;
    }
    public String getOpposition()
    {
        return mOppostion;
    }

    // Scores
    private List<Score> mScores;
    public void setScores(List<Score> s)
    {
        mScores = s;
    }
    public List<Score> getScores()
    {
        return mScores;
    }
    // Opposition Scores
    private int mOppGoals;
    public void setOppGoals(int in)
    {
        mOppGoals = in;
    }
    public int getOppGoals()
    {
        return mOppGoals;
    }

    private int mOppPoints;
    public void setOppPoints(int in)
    {
        mOppPoints = in;
    }
    public int getOppPoints()
    {
        return mOppPoints;
    }
    // Shooting
    private int mShots;
    public void setShots(int in)
    {
        mShots = in;
    }
    public int getShots()
    {
        return mShots;
    }

    private int mWides;
    public void setWides(int in)
    {
        mWides = in;
    }
    public int getWides()
    {
        return mWides;
    }

    private int mShorts;
    public void setShorts(int in)
    {
        mShorts = in;
    }
    public int getShorts()
    {
        return mShorts;
    }

    // Tackling
    private int mFouls;
    public void setFouls(int in)
    {
        mFouls = in;
    }
    public int getFouls()
    {
        return mFouls;
    }

    private int mScoresFromFouls;
    public void setScoresFromFouls(int in)
    {
        mScoresFromFouls = in;
    }
    public int getScoresFromFouls()
    {
        return mScoresFromFouls;
    }

    private int mBlocks;
    public void setBlocks(int in)
    {
        mBlocks = in;
    }
    public int getBlocks()
    {
        return mBlocks;
    }

    private int mHooks;
    public void setHooks(int in)
    {
        mHooks = in;
    }
    public int getHooks()
    {
        return mHooks;
    }

    // Passing
    private int mSuccessfulPasses;
    public void setSuccessfulPasses(int in)
    {
        mSuccessfulPasses = in;
    }
    public int getSuccessfulPassess()
    {
        return mSuccessfulPasses;
    }

    private int mUnsuccessfulPasses;
    public void setUnsuccessfulPasses(int in)
    {
        mUnsuccessfulPasses = in;
    }
    public int getUnsuccessfulPasses()
    {
        return mUnsuccessfulPasses;
    }

    private int mSuccessfulHandPasses;
    public void setSuccessfulHandPasses(int in)
    {
        mSuccessfulHandPasses = in;
    }
    public int getSuccessfulHandPasses()
    {
        return mSuccessfulHandPasses;
    }

    private int mUnsuccessfulHandPasses;
    public void setUnsuccessfulHandPasses(int in)
    {
        mUnsuccessfulHandPasses = in;
    }
    public int getUnsuccessfulHandPasses()
    {
        return mUnsuccessfulHandPasses;
    }

    // Restarts (kickouts/puckouts)
    private int mOwnWon;
    public void setOwnWon(int in)
    {
        mOwnWon = in;
    }
    public int getOwnWon()
    {
        return mOwnWon;
    }

    private int mOwnLost;
    public void setOwnLost(int in)
    {
        mOwnLost = in;
    }
    public int getOwnLosts()
    {
        return mOwnLost;
    }

    private int mOppWon;
    public void setOppWon(int in)
    {
        mOppWon = in;
    }
    public int getOppWon()
    {
        return mOppWon;
    }

    private int mOppLost;
    public void setOppLost(int in)
    {
        mOppLost = in;
    }
    public int getOppLost()
    {
        return mOppLost;
    }

}
