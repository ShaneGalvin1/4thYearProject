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
import android.widget.RadioButton;
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


        final RadioButton mRadioFootball = (RadioButton) findViewById(R.id.radio_football);

        final CheckBox mCheckboxStats = (CheckBox) findViewById(R.id.check_stats);

        final EditText mHomeTeam = (EditText) findViewById(R.id.homeTeam);
        final EditText mAwayTeam = (EditText) findViewById(R.id.awayTeam);

        Button mSetupConfirm = (Button) findViewById(R.id.setupConfirm);

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
                    if(mRadioFootball.isChecked())
                    {
                        b = true;
                    }

                    Match m = new Match(d,h,a,b);
                    if(!mCheckboxStats.isChecked())
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

    public void onCheckStatsClicked(View view)
    {
        if (((CheckBox) view).isChecked())
        {
            findViewById(R.id.select_stat_team).setVisibility(View.VISIBLE);
        }
        else
        {
            findViewById(R.id.select_stat_team).setVisibility(View.GONE);
        }
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
            //((ToggleButton) a.findViewById(R.id.toggleFootball)).setText(s);
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
            //((ToggleButton) a.findViewById(R.id.toggleHurling)).setText(s);
        }
    }
}
