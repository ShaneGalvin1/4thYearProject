package com.example.shane.winningedge;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

    private User mUser;
    private Session mSession;

    private RadioButton mRadioFootball;
    private CheckBox mCheckboxStats;
    private EditText mHomeTeam, mAwayTeam;
    private Button mSetupConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mUser = ((AppUser) getApplication()).getUser();
        mSession = Session.getCurrentSession(this);

        mRadioFootball = (RadioButton) findViewById(R.id.radio_football);

        mCheckboxStats = (CheckBox) findViewById(R.id.check_stats);

        mHomeTeam = (EditText) findViewById(R.id.homeTeam);
        mAwayTeam = (EditText) findViewById(R.id.awayTeam);

        mSetupConfirm = (Button) findViewById(R.id.setupConfirm);

        mSetupConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View focusView = null;
                boolean cancel = false;

                mHomeTeam.setError(null);
                mAwayTeam.setError(null);

                String h = mHomeTeam.getText().toString();
                String a = mAwayTeam.getText().toString();

                if(TextUtils.isEmpty(h) || h == null)
                {
                    mHomeTeam.setError(getString(R.string.error_field_required));
                    focusView = mHomeTeam;
                    cancel = true;
                }
                else if(TextUtils.isEmpty(a) || a == null)
                {
                    mAwayTeam.setError(getString(R.string.error_field_required));
                    focusView = mAwayTeam;
                    cancel = true;
                }

                if(cancel) {
                    focusView.requestFocus();
                }
                else
                {

                    boolean b = false;
                    if(mRadioFootball.isChecked())
                    {
                        b = true;
                    }

                    Match m = new Match(h,a,b);
                    m.setUserId(mUser.getUserId());
                    if(!mCheckboxStats.isChecked())
                    {
                        // Finish this activity
                        finish();

                        // Start the main activity

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("Match", m);
                        startActivity(i);
                    }
                    else
                    {
                        // Finish this activity
                        finish();

                        // Start the main activity

                        Intent i = new Intent(getApplicationContext(), StatActivity.class);
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

}
