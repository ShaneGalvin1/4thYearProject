package com.example.shane.winningedge;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;


public class SetupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);


        final ToggleButton mToggleFootball = (ToggleButton) findViewById(R.id.toggleFootball);
        final ToggleButton mToggleHurling = (ToggleButton) findViewById(R.id.toggleHurling);
        final ToggleButton mToggleScore = (ToggleButton) findViewById(R.id.toggleScore);
        final ToggleButton mToggleStat = (ToggleButton) findViewById(R.id.toggleStat);

        final EditText mHomeTeam = (EditText) findViewById(R.id.homeTeam);
        final EditText mAwayTeam = (EditText) findViewById(R.id.awayTeam);

        Button mSetupConfirm = (Button) findViewById(R.id.setupConfirm);
        Button mSetupCancel = (Button) findViewById(R.id.setupCancel);

        // Invisible Components
        final TextView mChoose = (TextView) findViewById(R.id.chooseTeam);
        final CheckBox mCheckHome = (CheckBox) findViewById(R.id.checkHome);
        final CheckBox mCheckAway = (CheckBox) findViewById(R.id.checkAway);

        mToggleFootball.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mToggleFootball.isChecked() == true)
                {
                    mToggleHurling.setChecked(false);
                }
                if(mToggleFootball.isChecked() == false)
                {
                    mToggleHurling.setChecked(true);
                }
            }
        });
        mToggleHurling.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mToggleHurling.isChecked() == true)
                {
                    mToggleFootball.setChecked(false);
                }
                if(mToggleHurling.isChecked() == false)
                {
                    mToggleFootball.setChecked(true);
                }
            }
        });
        mToggleScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mToggleScore.isChecked() == true)
                {
                    mToggleStat.setChecked(false);
                    mChoose.setVisibility(View.GONE);
                    mCheckHome.setVisibility(View.GONE);
                    mCheckAway.setVisibility(View.GONE);
                }
            }
        });
        mToggleStat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mToggleStat.isChecked() == true)
                {
                    mToggleScore.setChecked(false);
                    mChoose.setVisibility(View.VISIBLE);
                    mCheckHome.setVisibility(View.VISIBLE);
                    mCheckAway.setVisibility(View.VISIBLE);
                }
            }
        });






        mSetupCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code to go back
            }
        });

        mSetupConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mHomeTeam.getText().equals("") || mHomeTeam.getText().equals("Home Team"))
                {

                }
                else if(mAwayTeam.getText().equals("") || mAwayTeam.getText().equals("Away Team"))
                {

                }
                else
                {
                    Calendar currentDate = Calendar.getInstance();
                    long d = currentDate.getTimeInMillis();
                    String h = mHomeTeam.getText().toString();
                    String a = mAwayTeam.getText().toString();
                    boolean b = false;
                    if(mToggleFootball.isChecked())
                    {
                        b = true;
                    }

                    Match m = new Match(d,h,a,b);
                    if(mToggleScore.isChecked())
                    {
                        // Finish this activity
                        finish();

                        // Start the main activity

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("Match", m);
                        startActivity(i);
                    }
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
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

    private class Update extends AsyncTask<Void, Void, String> {
        private Activity a;

        public Update(Activity a) {
            this.a = a;
        }
        @Override
        protected String doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Calendar currentDate = Calendar.getInstance();
            long d = currentDate.getTimeInMillis();

            String h = "Team 1";
            String a = "Team 2";
            boolean b = true;

            Match m = new Match(d,h,a,b);
            try {
                restTemplate.postForObject("http://weservice.azurewebsites.net/api/Matches", m, Match.class);
                return "Success";
            } catch(HttpClientErrorException e) {
                return "HTTP Error";
            } catch(RestClientException e) {
                return "REST Error";
            }

        }

        //@Override
        protected void onPostExecute(String s) {
            ((ToggleButton) a.findViewById(R.id.toggleFootball)).setText(s);
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
            Score score = new Score();
            try {
                restTemplate.postForObject("http://weservice.azurewebsites.net/api/Scores", score, Score.class);
                //return restTemplate.getForObject("http://weservice.azurewebsites.net/api/Matches/Name/1", String.class);
                return "Success";
            } catch(HttpClientErrorException e) {
                return "HTTP Error";
            } catch(RestClientException e) {
                return "REST Error";
            }

        }

        //@Override
        protected void onPostExecute(String s) {
            ((ToggleButton) a.findViewById(R.id.toggleHurling)).setText(s);
        }
    }
}
