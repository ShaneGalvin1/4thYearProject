package com.example.shane.winningedge;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends ActionBarActivity {

    private User mUser;
    private Session mSession;

    private Match m;
    private int id, sId;
    private Score score;

    // UI Components
    private TextView hGoals, hPoints, aGoals, aPoints;
    private Button homeGoal, homePoint, awayGoal, awayPoint, matchEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new getNextId(this).execute();
        hGoals = (TextView) findViewById(R.id.hGoals);
        hPoints = (TextView) findViewById(R.id.hPoints);
        aGoals = (TextView) findViewById(R.id.aGoals);
        aPoints = (TextView) findViewById(R.id.aPoints);

        Intent i = getIntent();
        m = (Match) i.getSerializableExtra("Match");

        // Button Click Events
        homeGoal = (Button) findViewById(R.id.homeGoal);
        homeGoal.setEnabled(false);
        homeGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = hGoals.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                hGoals.setText(i+"");
                score = new Score(sId, true, id);
                sId++;
                new PostScore(getParent()).execute();
            }
        });
        homePoint = (Button) findViewById(R.id.homePoint);
        homePoint.setEnabled(false);
        homePoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = hPoints.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                hPoints.setText(i+"");
                score = new Score(sId, false, id);
                sId++;
                new PostScore(getParent()).execute();
            }
        });
        awayGoal = (Button) findViewById(R.id.awayGoal);
        awayGoal.setEnabled(false);
        awayGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = aGoals.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                aGoals.setText(i+"");
                m.setOppGoals(m.getOppGoals()+1);
                new Update(getParent()).execute();
            }
        });
        awayPoint = (Button) findViewById(R.id.awayPoint);
        awayPoint.setEnabled(false);
        awayPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = aPoints.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                aPoints.setText(i+"");
                m.setOppPoints(m.getOppPoints()+1);
                new Update(getParent()).execute();
            }
        });

        matchEvent = (Button) findViewById(R.id.matchEvent);
        matchEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(matchEvent.getText().equals("Start Match"))
                {
                    Calendar currentDate = Calendar.getInstance();
                    long d = currentDate.getTimeInMillis();
                    m.setMatchDate(d);
                    toggleButtons();
                    m.setInPlay(true);
                    new CreateMatch(getParent()).execute();
                    matchEvent.setText("Half Time");
                }
                else if(matchEvent.getText().equals("Half Time"))
                {
                    toggleButtons();
                    m.setInPlay(false);
                    m.setHalfTime(true);
                    new Update(getParent()).execute();
                    matchEvent.setText("Start Second Half");
                }
                else if(matchEvent.getText().equals("Start Second Half"))
                {
                    toggleButtons();
                    m.setInPlay(true);
                    m.setHalfTime(false);
                    new Update(getParent()).execute();
                    matchEvent.setText("Full Time");
                }
                else if(matchEvent.getText().equals("Full Time"))
                {
                    toggleButtons();
                    m.setInPlay(false);
                    m.setFullTime(true);
                    new Update(getParent()).execute();
                    matchEvent.setText("Exit Match");
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void toggleButtons()
    {
        if(homeGoal.isEnabled()) {
            homeGoal.setEnabled(false);
            homePoint.setEnabled(false);
            awayGoal.setEnabled(false);
            awayPoint.setEnabled(false);
        } else {
            homeGoal.setEnabled(true);
            homePoint.setEnabled(true);
            awayGoal.setEnabled(true);
            awayPoint.setEnabled(true);
        }
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
                m = restTemplate.postForObject("http://weservice.azurewebsites.net/api/Matches", m, Match.class);
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
                id = m.getMatchId();
            } else if(s.equals("HTTP")) {
                // Error Handling
            } else {
                // Error Handling
            }

        }
    }


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
                restTemplate.put("http://weservice.azurewebsites.net/api/Matches/" + m.getMatchId(),m, Match.class);

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

