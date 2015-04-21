package com.example.shane.winningedge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class StatActivity extends ActionBarActivity {
    final Context context = this;

    private User mUser;
    private Session mSession;

    private Match match, m;
    private boolean inter, home;
    private Score score;
    private int sId;

    private TabHost mTab;
    private TextView mTimer, mHome, mAway, mHGoals, mAGoals, mHPoints, mAPoints, mWideCount, mShortCount,
        mFoulCount, mScoredFoulsCount, mBlockCount, mHookTitle, mHookCount, mYellowCount, mRedCount,
        mSPassCount, mUPassCount, mSHPassCount, mUHPassCount, mOwnWon, mOwnLost, mOppWon, mOppLost;
    private Button mEvent, mHomeGoal, mAwayGoal, mHomePoint, mAwayPoint, mWideMinus, mWidePlus, mShortMinus, mShortPlus,
            mFoulMinus, mScoredFoulsMinus, mBlockMinus, mHookMinus, mYellowMinus, mRedMinus, mFoulPlus, mScoredFoulsPlus,
            mBlockPlus, mHookPlus, mYellowPlus, mRedPlus,
            mSPassMinus, mUPassMinus, mSHPassMinus, mUHPassMinus, mOwnWonMinus, mOwnLostMinus, mOppWonMinus, mOppLostMinus,
            mSPassPlus, mUPassPlus, mSHPassPlus, mUHPassPlus, mOwnWonPlus, mOwnLostPlus, mOppWonPlus, mOppLostPlus;

    private View mHookLine;
    // Timer
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int mMins, mSecs;

    // Dialog
    private RadioButton radioPlay, radioDead, radioD21, radioD45, radioD45Plus;

    private static List<String> counties = Arrays.asList("Antrim","Armagh","Carlow","Cavan","Clare","Cork","Derry",
            "Donegal","Down","Dublin","Fermanagh","Galway","Kerry","Kildare","Kilkenny","Laois","Leitrim","Limerick",
            "Longford","Louth","Mayo","Meath","Monaghan","Offaly","Roscommon","Sligo","Tipperary","Tyrone","Waterford",
            "Westmeath","Wexford","Wicklow");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        new getNextId(this).execute();
        Intent i = getIntent();
        match = (Match) i.getSerializableExtra("Match");
        // If home is true, user is recording stats for home team, else away team
        home = match.getHome();
        score = new Score();
        if(counties.contains(match.getTeam()) && counties.contains(match.getOppostion())) {
            inter = true;
        }
        else {
            inter = false;
        }

        // Top Layout Setup
        mTimer = (TextView) findViewById(R.id.timer);
        mHome = (TextView) findViewById(R.id.home);
        mAway = (TextView) findViewById(R.id.away);
        mHGoals = (TextView) findViewById(R.id.hGoals);
        mAGoals = (TextView) findViewById(R.id.aGoals);
        mHPoints = (TextView) findViewById(R.id.hPoints);
        mAPoints = (TextView) findViewById(R.id.aPoints);

        if(home) {
            mHome.setText(match.getTeam());
            mAway.setText(match.getOppostion());
        }
        else {
            mAway.setText(match.getTeam());
            mHome.setText(match.getOppostion());
        }

        // Tabhost Setup
        mTab = (TabHost) findViewById(R.id.tabHost);
        mTab.setup();

        TabHost.TabSpec spec = mTab.newTabSpec("tag1");

        spec.setContent(R.id.shooting);
        spec.setIndicator("Shooting");
        mTab.addTab(spec);

        spec = mTab.newTabSpec("tag2");
        spec.setContent(R.id.tackling);
        spec.setIndicator("Tackling");
        mTab.addTab(spec);

        spec = mTab.newTabSpec("tag3");
        spec.setContent(R.id.passing);
        spec.setIndicator("Passing");
        mTab.addTab(spec);

        // Shooting Tab Setup
        mWideCount = (TextView) findViewById(R.id.wideCount);
        mShortCount = (TextView) findViewById(R.id.shortCount);
        mHomeGoal = (Button) findViewById(R.id.line1Home);
        mAwayGoal = (Button) findViewById(R.id.line1Away);
        mHomePoint = (Button) findViewById(R.id.line2Home);
        mAwayPoint = (Button) findViewById(R.id.line2Away);
        mWideMinus = (Button) findViewById(R.id.line3Minus);
        mWidePlus = (Button) findViewById(R.id.line3Plus);
        mShortMinus = (Button) findViewById(R.id.line4Minus);
        mShortPlus = (Button) findViewById(R.id.line4Plus);

        // Tackling Tab Setup
        mFoulCount = (TextView) findViewById(R.id.foulCount);
        mScoredFoulsCount = (TextView) findViewById(R.id.fromFoulCount);
        mBlockCount = (TextView) findViewById(R.id.blocksCount);
        mHookTitle = (TextView) findViewById(R.id.hooksTitle);
        mHookLine = findViewById(R.id.hookLine);
        mHookCount = (TextView) findViewById(R.id.hooksCount);
        mYellowCount = (TextView) findViewById(R.id.yellowCount);
        mRedCount = (TextView) findViewById(R.id.redCount);
        //Buttons
        mFoulMinus = (Button) findViewById(R.id.tackle1Minus);
        mFoulPlus = (Button) findViewById(R.id.tackle1Plus);
        mScoredFoulsMinus = (Button) findViewById(R.id.tackle2Minus);
        mScoredFoulsPlus = (Button) findViewById(R.id.tackle2Plus);
        mBlockMinus = (Button) findViewById(R.id.tackle3Minus);
        mBlockPlus = (Button) findViewById(R.id.tackle3Plus);
        mHookMinus = (Button) findViewById(R.id.tackle4Minus);
        mHookPlus = (Button) findViewById(R.id.tackle4Plus);
        mYellowMinus = (Button) findViewById(R.id.tackle5Minus);
        mYellowPlus = (Button) findViewById(R.id.tackle5Plus);
        mRedMinus = (Button) findViewById(R.id.tackle6Minus);
        mRedPlus = (Button) findViewById(R.id.tackle6Plus);

        if(match.getFootball()) {
            mHookLine.setVisibility(View.GONE);
            mHookTitle.setVisibility(View.GONE);
            mHookCount.setVisibility(View.GONE);
            mHookMinus.setVisibility(View.GONE);
            mHookPlus.setVisibility(View.GONE);
        }
        // Passing Tab Setup
        mSPassCount = (TextView) findViewById(R.id.sPassCount);
        mUPassCount = (TextView) findViewById(R.id.uPassCount);
        mSHPassCount = (TextView) findViewById(R.id.shPassCount);
        mUHPassCount = (TextView) findViewById(R.id.uhPassesCount);
        mOwnWon = (TextView) findViewById(R.id.ownWonCount);
        mOwnLost = (TextView) findViewById(R.id.ownLostCount);
        mOppWon = (TextView) findViewById(R.id.oppWonCount);
        mOppLost = (TextView) findViewById(R.id.oppLostCount);
        //Buttons
        mSPassMinus = (Button) findViewById(R.id.pass1Minus);
        mSPassPlus = (Button) findViewById(R.id.pass1Plus);
        mUPassMinus = (Button) findViewById(R.id.pass2Minus);
        mUPassPlus = (Button) findViewById(R.id.pass2Plus);
        mSHPassMinus = (Button) findViewById(R.id.pass3Minus);
        mSHPassPlus = (Button) findViewById(R.id.pass3Plus);
        mUHPassMinus = (Button) findViewById(R.id.pass4Minus);
        mUHPassPlus = (Button) findViewById(R.id.pass4Plus);
        mOwnWonMinus = (Button) findViewById(R.id.pass5Minus);
        mOwnWonPlus = (Button) findViewById(R.id.pass5Plus);
        mOwnLostMinus = (Button) findViewById(R.id.pass6Minus);
        mOwnLostPlus = (Button) findViewById(R.id.pass6Plus);
        mOppWonMinus = (Button) findViewById(R.id.pass7Minus);
        mOppWonPlus = (Button) findViewById(R.id.pass7Plus);
        mOppLostMinus = (Button) findViewById(R.id.pass8Minus);
        mOppLostPlus = (Button) findViewById(R.id.pass8Plus);

        //Disable Buttons
        toggleButtons();
        // mEvent Button
        mEvent = (Button) findViewById(R.id.matchEvent1);
        mEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mEvent.getText().equals("Start Match"))
                {
                    Calendar currentDate = Calendar.getInstance();
                    long d = currentDate.getTimeInMillis();
                    match.setMatchDate(d);
                    match.setInPlay(true);
                    new CreateMatch(getParent()).execute();
                    mEvent.setText("Half Time");
                    toggleButtons();
                    Start();
                }
                else if(mEvent.getText().equals("Half Time"))
                {
                    match.setInPlay(false);
                    match.setHalfTime(true);
                    new Update(getParent()).execute();
                    mEvent.setText("Start Second Half");
                    toggleButtons();
                    Stop();
                }
                else if(mEvent.getText().equals("Start Second Half"))
                {
                    match.setInPlay(true);
                    match.setHalfTime(false);
                    new Update(getParent()).execute();
                    mEvent.setText("Full Time");
                    toggleButtons();
                    StartSecond();
                }
                else if(mEvent.getText().equals("Full Time"))
                {
                    match.setInPlay(false);
                    match.setFullTime(true);
                    if(home){
                        String s = mHGoals.getText().toString();
                        int g = Integer.parseInt(s);
                        s = mHPoints.getText().toString();
                        int p = Integer.parseInt(s);
                        match.setShots(match.getWides()+match.getShorts()+g+p);
                        new Update(getParent()).execute();
                    } else {
                        String s = mAGoals.getText().toString();
                        int g = Integer.parseInt(s);
                        s = mAPoints.getText().toString();
                        int p = Integer.parseInt(s);
                        match.setShots(match.getWides()+match.getShorts()+g+p);
                        new Update(getParent()).execute();
                    }
                    mEvent.setText("Exit Match");
                    toggleButtons();
                    Stop();
                }
                else
                {
                    // Finish this activity
                    finish();
                    // Start the main activity
                    Intent i = new Intent(getApplicationContext(), SetupActivity.class);
                    startActivity(i);
                }
            }
        });

        // Listeners for Shooting Tab Buttons
        mHomeGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mHGoals.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mHGoals.setText(i+"");
                if(home) {
                    score = new Score(sId, true, match.getMatchId());
                    score.setMins(mMins);
                    score.setSecs(mSecs);
                    customDialog();
                    sId++;

                } else {
                    // HGoals == oppGoals
                    match.setOppGoals(match.getOppGoals()+1);
                    new Update(getParent()).execute();
                }
            }
        });
        mAwayGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mAGoals.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mAGoals.setText(i+"");
                if(!home) {
                    score = new Score(sId, true, match.getMatchId());
                    score.setMins(mMins);
                    score.setSecs(mSecs);
                    customDialog();
                    sId++;
                } else {
                    // AGoals == oppGoals
                    match.setOppGoals(match.getOppGoals()+1);
                    new Update(getParent()).execute();
                }
            }
        });
        mHomePoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mHPoints.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mHPoints.setText(i+"");
                if(home) {
                    score = new Score(sId, false, match.getMatchId());
                    score.setMins(mMins);
                    score.setSecs(mSecs);
                    customDialog();
                    sId++;
                } else {
                    // HPoints == oppPoints
                    match.setOppPoints(match.getOppPoints() + 1);
                    new Update(getParent()).execute();
                }
            }
        });
        mAwayPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mAPoints.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mAPoints.setText(i+"");
                if(!home) {
                    score = new Score(sId, false, match.getMatchId());
                    score.setMins(mMins);
                    score.setSecs(mSecs);
                    customDialog();
                    sId++;
                } else {
                    // APoints == oppPoints
                    match.setOppPoints(match.getOppPoints()+1);
                    new Update(getParent()).execute();
                }
            }
        });
        mWideMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mWideCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mWideCount.setText(i + "");
                    match.setWides(i);
                    // Update match object
                }
            }
        });
        mWidePlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mWideCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mWideCount.setText(i + "");
                match.setWides(i);
                // Update match object
            }
        });
        mShortMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mShortCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mShortCount.setText(i + "");
                    match.setShorts(i);
                    // Update match object
                }
            }
        });
        mShortPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mShortCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mShortCount.setText(i + "");
                match.setShorts(i);
                // Update match object
            }
        });

        // Listeners for Tackling Tab Buttons
        mFoulMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mFoulCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mFoulCount.setText(i + "");
                    match.setFouls(i);
                }
            }
        });
        mFoulPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mFoulCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mFoulCount.setText(i + "");
                match.setFouls(i);
            }
        });
        mScoredFoulsMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mScoredFoulsCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mScoredFoulsCount.setText(i + "");
                    match.setScoresFromFouls(i);
                }
            }
        });
        mScoredFoulsPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mScoredFoulsCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mScoredFoulsCount.setText(i + "");
                match.setScoresFromFouls(i);
            }
        });
        mBlockMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mBlockCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mBlockCount.setText(i + "");
                    match.setBlocks(i);//fin
                }
            }
        });
        mBlockPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mBlockCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mBlockCount.setText(i + "");
                match.setBlocks(i);
            }
        });
        mHookMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mHookCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mHookCount.setText(i + "");
                    match.setHooks(i);
                }
            }
        });
        mHookPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mHookCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mHookCount.setText(i + "");
                match.setHooks(i);
            }
        });
        mYellowMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mYellowCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mYellowCount.setText(i + "");
                    match.setYellows(i);
                }
            }
        });
        mYellowPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mYellowCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mYellowCount.setText(i + "");
                match.setYellows(i);
            }
        });
        mRedMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mRedCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mRedCount.setText(i + "");
                    match.setReds(i);
                }
            }
        });
        mRedPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mRedCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mRedCount.setText(i + "");
                match.setReds(i);
            }
        });

        // Listeners for Passing
        mSPassMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mSPassCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mSPassCount.setText(i + "");
                    match.setSuccessfulPasses(i);
                }
            }
        });
        mSPassPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mSPassCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mSPassCount.setText(i + "");
                match.setSuccessfulPasses(i);
            }
        });
        mUPassMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mUPassCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mUPassCount.setText(i + "");
                    match.setUnsuccessfulPasses(i);
                }
            }
        });
        mUPassPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mUPassCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mUPassCount.setText(i + "");
                match.setUnsuccessfulPasses(i);
            }
        });
        mSHPassMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mSHPassCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mSHPassCount.setText(i + "");
                    match.setSuccessfulHandPasses(i);
                }
            }
        });
        mSHPassPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mSHPassCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mSHPassCount.setText(i + "");
                match.setSuccessfulHandPasses(i);
            }
        });
        mUHPassMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mUHPassCount.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mUHPassCount.setText(i + "");
                    match.setUnsuccessfulHandPasses(i);
                }
            }
        });
        mUHPassPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mUHPassCount.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mUHPassCount.setText(i + "");
                match.setUnsuccessfulHandPasses(i);
            }
        });
        mOwnWonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mOwnWon.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mOwnWon.setText(i + "");
                    match.setOwnWon(i);
                }
            }
        });
        mOwnWonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mOwnWon.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mOwnWon.setText(i + "");
                match.setOwnWon(i);
            }
        });
        mOwnLostMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mOwnLost.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mOwnLost.setText(i + "");
                    match.setOwnLost(i);
                }
            }
        });
        mOwnLostPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mOwnLost.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mOwnLost.setText(i + "");
                match.setOwnLost(i);
            }
        });
        mOppWonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mOppWon.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mOppWon.setText(i + "");
                    match.setOppWon(i);
                }
            }
        });
        mOppWonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mOppWon.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mOppWon.setText(i + "");
                match.setOppWon(i);
            }
        });
        mOppLostMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mOppLost.getText().toString();
                int i = Integer.parseInt(s);
                if(i > 0) {
                    i--;
                    mOppLost.setText(i + "");
                    match.setOppLost(i);
                }
            }
        });
        mOppLostPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = mOppLost.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                mOppLost.setText(i + "");
                match.setOppLost(i);
            }
        });
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            mTimer.setText("" + mins + ":"
                            + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
            mMins = mins;
            mSecs = secs;
            if(inter == false) {
                if (mEvent.getText().equals("Half Time") && mins >= 30) {
                    mTimer.setTextColor(Color.RED);
                }
                if (mEvent.getText().equals("Full Time") && mins >= 60) {
                    mTimer.setTextColor(Color.RED);
                }
            } else {
                if (mEvent.getText().equals("Half Time") && mins >= 35) {
                    mTimer.setTextColor(Color.RED);
                }
                if (mEvent.getText().equals("Full Time") && mins >= 70) {
                    mTimer.setTextColor(Color.RED);
                }
            }
        }
    };

    private void Start()
    {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }
    private void StartSecond()
    {
        if(inter == false) {
            startTime = SystemClock.uptimeMillis() - (1800000 - updatedTime);
            customHandler.postDelayed(updateTimerThread, 0);
        } else {
            startTime = SystemClock.uptimeMillis() - (2100000 - updatedTime);
            customHandler.postDelayed(updateTimerThread, 0);
        }

    }
    private void Stop()
    {
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);

    }

    private void toggleButtons()
    {
        if(mHomeGoal.isEnabled()) {
            //Shooting
            mHomeGoal.setEnabled(false);
            mAwayGoal.setEnabled(false);
            mHomePoint.setEnabled(false);
            mAwayPoint.setEnabled(false);
            mWideMinus.setEnabled(false);
            mWidePlus.setEnabled(false);
            mShortMinus.setEnabled(false);
            mShortPlus.setEnabled(false);
            //Tackling
            mFoulMinus.setEnabled(false);
            mFoulPlus.setEnabled(false);
            mScoredFoulsMinus.setEnabled(false);
            mScoredFoulsPlus.setEnabled(false);
            mBlockMinus.setEnabled(false);
            mBlockPlus.setEnabled(false);
            mHookMinus.setEnabled(false);
            mHookPlus.setEnabled(false);
            mYellowMinus.setEnabled(false);
            mYellowPlus.setEnabled(false);
            mRedMinus.setEnabled(false);
            mRedPlus.setEnabled(false);
            //Passing
            mSPassMinus.setEnabled(false);
            mSPassPlus.setEnabled(false);
            mUPassMinus.setEnabled(false);
            mUPassPlus.setEnabled(false);
            mSHPassMinus.setEnabled(false);
            mSHPassPlus.setEnabled(false);
            mUHPassMinus.setEnabled(false);
            mUHPassPlus.setEnabled(false);
            mOwnWonMinus.setEnabled(false);
            mOwnWonPlus.setEnabled(false);
            mOwnLostMinus.setEnabled(false);
            mOwnLostPlus.setEnabled(false);
            mOppWonMinus.setEnabled(false);
            mOppWonPlus.setEnabled(false);
            mOppLostMinus.setEnabled(false);
            mOppLostPlus.setEnabled(false);
        } else {
            //Shooting
            mHomeGoal.setEnabled(true);
            mAwayGoal.setEnabled(true);
            mHomePoint.setEnabled(true);
            mAwayPoint.setEnabled(true);
            mWideMinus.setEnabled(true);
            mWidePlus.setEnabled(true);
            mShortMinus.setEnabled(true);
            mShortPlus.setEnabled(true);
            //Tackling
            mFoulMinus.setEnabled(true);
            mFoulPlus.setEnabled(true);
            mScoredFoulsMinus.setEnabled(true);
            mScoredFoulsPlus.setEnabled(true);
            mBlockMinus.setEnabled(true);
            mBlockPlus.setEnabled(true);
            mHookMinus.setEnabled(true);
            mHookPlus.setEnabled(true);
            mYellowMinus.setEnabled(true);
            mYellowPlus.setEnabled(true);
            mRedMinus.setEnabled(true);
            mRedPlus.setEnabled(true);
            //Passing
            mSPassMinus.setEnabled(true);
            mSPassPlus.setEnabled(true);
            mUPassMinus.setEnabled(true);
            mUPassPlus.setEnabled(true);
            mSHPassMinus.setEnabled(true);
            mSHPassPlus.setEnabled(true);
            mUHPassMinus.setEnabled(true);
            mUHPassPlus.setEnabled(true);
            mOwnWonMinus.setEnabled(true);
            mOwnWonPlus.setEnabled(true);
            mOwnLostMinus.setEnabled(true);
            mOwnLostPlus.setEnabled(true);
            mOppWonMinus.setEnabled(true);
            mOppWonPlus.setEnabled(true);
            mOppLostMinus.setEnabled(true);
            mOppLostPlus.setEnabled(true);
        }
    }

    private void customDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(R.string.dialogTitle);

        // set the custom dialog components - text, image and button
        radioPlay = (RadioButton) dialog.findViewById(R.id.radioPlay);
        radioDead = (RadioButton) dialog.findViewById(R.id.radioDead);
        radioD21 = (RadioButton) dialog.findViewById(R.id.radioD21);
        radioD45 = (RadioButton) dialog.findViewById(R.id.radioD45);
        radioD45Plus = (RadioButton) dialog.findViewById(R.id.radioD45Plus);


        Button dialogButton = (Button) dialog.findViewById(R.id.button);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioPlay.isChecked()){
                    score.setFromPlay(true);
                } else if(radioDead.isChecked()) {
                    score.setFromPlay(false);
                }

                if(radioD21.isChecked()){
                    score.setDistance(21);
                } else if(radioD45.isChecked()) {
                    score.setDistance(45);
                } else if(radioD45Plus.isChecked()) {
                    score.setDistance(46);
                }
                new PostScore(getParent()).execute();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class CreateMatch extends AsyncTask<Void, Void, String> {
        private Activity a;

        public CreateMatch(Activity a) {
            this.a = a;
        }
        @Override
        protected String doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                m = restTemplate.postForObject("http://weservice.azurewebsites.net/api/Matches", match, Match.class);
                return "Success";
            } catch(HttpClientErrorException e) {
                return "HTTP";
            } catch(RestClientException e) {
                return "REST";
            }

        }

        //@Override
        protected void onPostExecute(String s) {
            if(s.equals("Success")) {
                match.setMatchId(m.getMatchId());
            } else if(s.equals("HTTP")) {
                // Error Handling
            } else {
                // Error Handling
            }

        }
    }

    // Update Match
    private class Update extends AsyncTask<Void, Void, String> {
        private Activity a;

        public Update(Activity a) {
            this.a = a;
        }
        @Override
        protected String doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                restTemplate.put("http://weservice.azurewebsites.net/api/Matches/" + match.getMatchId(),match, Match.class);
                return "Success";
            } catch(HttpClientErrorException e) {
                return "HTTP";
            } catch(RestClientException e) {
                return "REST";
            }

        }

        //@Override
        protected void onPostExecute(String s) {
            if(s.equals("Success")) {
                // Update Successful
            } else if(s.equals("HTTP")) {
                // Error Handling
            } else {
                // Error Handling
            }
        }
    }



    private class PostScore extends AsyncTask<Void, Void, String> {
        private Activity a;

        public PostScore(Activity a) {
            this.a = a;
        }
        @Override
        protected String doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            try {
                restTemplate.postForObject("http://weservice.azurewebsites.net/api/Scores", score, Score.class);
                return "Success";
            } catch(HttpClientErrorException e) {
                return "HTTP";
            } catch(RestClientException e) {
                return "REST";
            }

        }

        //@Override
        protected void onPostExecute(String s) {
            if(s.equals("Success")) {
                // Update Successful
            } else if(s.equals("HTTP")) {
                // Error Handling
            } else {
                // Error Handling
            }
        }
    }

    private class getNextId extends AsyncTask<Void, Void, String> {
        private Activity a;

        public getNextId(Activity a) {
            this.a = a;
        }
        @Override
        protected String doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            try {
                return restTemplate.getForObject("http://weservice.azurewebsites.net/api/Scores/id", String.class);
            } catch(HttpClientErrorException e) {
                return "HTTP";
            } catch(RestClientException e) {
                return "REST";
            }

        }

        //@Override
        protected void onPostExecute(String s) {
            if(s.equals("REST")) {
                // Error Handling
            } else if(s.equals("HTTP")) {
                // Error Handling
            } else {
                sId = Integer.parseInt(s);
            }
        }
    }
}
