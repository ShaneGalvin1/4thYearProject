package com.example.shane.winningedge;

import android.support.annotation.NonNull;

import org.joda.time.JodaTimePermission;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Shane on 18/03/2015.
 */
@SuppressWarnings("serial")
public class Match implements Serializable {

    // Associates matches with user
    // Foreign Key
    private int userId;
    public void setUserId(int in)
    {
        this.userId = in;
    }
    public int getUserId()
    {
        return userId;
    }
    // Match Info
    private int matchId;
    public void setMatchId(int in)
    {
        this.matchId = in;
    }
    public int getMatchId()
    {
        return matchId;
    }


    private long matchDate;
    public void setMatchDate(long d)
    {
        this.matchDate = d;
    }
    public long getMatchDate()
    {
        return matchDate;
    }

    private boolean home;
    public void setHome(boolean b)
    {
        this.home = b;
    }
    public boolean getHome()
    {
        return home;
    }

    private String team;
    public void setTeam(String s)
    {
        this.team = s;
    }
    public String getTeam()
    {
        return team;
    }

    private String oppostion;
    public void setOppostion(String s)
    {
        this.oppostion = s;
    }
    public String getOppostion()
    {
        return oppostion;
    }

    private boolean football;
    public void setFootball(boolean b)
    {
        this.football = b;
    }
    public boolean getFootball()
    {
        return football;
    }

    // Variables for livescore
    private boolean inPlay;
    public void setInPlay(boolean b)
    {
        this.inPlay = b;
    }
    public boolean getInPlay()
    {
        return inPlay;
    }

    private boolean halfTime;
    public void setHalfTime(boolean b)
    {
        this.halfTime = b;
    }
    public boolean getHalfTime()
    {
        return halfTime;
    }

    private boolean fullTime;
    public void setFullTime(boolean b)
    {
        this.fullTime = b;
    }
    public boolean getFullTime()
    {
        return fullTime;
    }

    // Opposition Scores
    private int oppGoals;
    public void setOppGoals(int in)
    {
        this.oppGoals = in;
    }
    public int getOppGoals()
    {
        return oppGoals;
    }

    private int oppPoints;
    public void setOppPoints(int in)
    {
        this.oppPoints = in;
    }
    public int getOppPoints()
    {
        return oppPoints;
    }
    // Shooting
    private int shots;
    public void setShots(int in)
    {
        this.shots = in;
    }
    public int getShots()
    {
        return shots;
    }

    private int wides;
    public void setWides(int in)
    {
        this.wides = in;
    }
    public int getWides()
    {
        return wides;
    }

    private int shorts;
    public void setShorts(int in)
    {
        this.shorts = in;
    }
    public int getShorts()
    {
        return shorts;
    }
    // Tackling
    private int fouls;
    public void setFouls(int in)
    {
        this.fouls = in;
    }
    public int getFouls()
    {
        return fouls;
    }

    private int scoresFromFouls;
    public void setScoresFromFouls(int in)
    {
        this.scoresFromFouls = in;
    }
    public int getScoresFromFouls()
    {
        return scoresFromFouls;
    }

    private int blocks;
    public void setBlocks(int in)
    {
        this.blocks = in;
    }
    public int getBlocks()
    {
        return blocks;
    }

    private int hooks;
    public void setHooks(int in)
    {
        this.hooks = in;
    }
    public int getHooks()
    {
        return hooks;
    }

    private int yellows;
    public void setYellows(int in) { this.yellows = in; }
    public int getYellows() { return yellows; }

    private int reds;
    public void setReds(int in) { this.reds = in; }
    public int getReds() { return reds; }
    // Passing
    private int successfulPasses;
    public void setSuccessfulPasses(int in)
    {
        this.successfulPasses = in;
    }
    public int getSuccessfulPasses()
    {
        return successfulPasses;
    }

    private int unsuccessfulPasses;
    public void setUnsuccessfulPasses(int in)
    {
        this.unsuccessfulPasses = in;
    }
    public int getUnsuccessfulPasses()
    {
        return unsuccessfulPasses;
    }

    private int successfulHandPasses;
    public void setSuccessfulHandPasses(int in)
    {
        this.successfulHandPasses = in;
    }
    public int getSuccessfulHandPasses()
    {
        return successfulHandPasses;
    }

    private int unsuccessfulHandPasses;
    public void setUnsuccessfulHandPasses(int in)
    {
        this.unsuccessfulHandPasses = in;
    }
    public int getUnsuccessfulHandPasses()
    {
        return unsuccessfulHandPasses;
    }
    // Restarts (kickouts/puckouts)
    private int ownWon;
    public void setOwnWon(int in)
    {
        this.ownWon = in;
    }
    public int getOwnWon()
    {
        return ownWon;
    }

    private int ownLost;
    public void setOwnLost(int in)
    {
        this.ownLost = in;
    }
    public int getOwnLost()
    {
        return ownLost;
    }

    private int oppWon;
    public void setOppWon(int in)
    {
        this.oppWon = in;
    }
    public int getOppWon()
    {
        return oppWon;
    }

    private int oppLost;
    public void setOppLost(int in)
    {
        this.oppLost = in;
    }
    public int getOppLost()
    {
        return oppLost;
    }
    public Match()
    {

    }
    public Match(String t, String o, boolean b, boolean h)
    {
        setTeam(t);
        setOppostion(o);
        setFootball(b);
        setHome(h);
        setFullTime(false);
        setInPlay(false);
        setHalfTime(false);
    }
}
