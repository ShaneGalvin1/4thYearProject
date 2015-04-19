package com.example.shane.winningedge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    private Session mSession;

    private boolean mReg = false;
    private UserLogin mAuthTask = null;
    private UserRegister mAuthTaskReg = null;
    private UserCheck mAuthTaskCheck = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView, mPasswordConfirm;
    private Button mEmailSignInButton;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mPageText, mPageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((AppUser) getApplication()).removeUser();
        mSession = Session.getCurrentSession(this);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mPasswordConfirm = (EditText) findViewById(R.id.passwordConfirm);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mReg == false) {
                    attemptLogin();
                }
                else if(mReg == true)
                {
                    attemptRegister();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mPageText = (TextView) findViewById(R.id.newReg);

        mPageLink = (TextView) findViewById(R.id.linkReg);
        mPageLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mPageLink.getText().equals(getString(R.string.link_reg)))
                {
                    mPasswordConfirm.setVisibility(View.VISIBLE);
                    mPageText.setText(getString(R.string.log));
                    mPageLink.setText(getString(R.string.log_here));
                    mEmailSignInButton.setText(R.string.action_reg);
                    mReg = true;
                }
                else
                {
                    mPasswordConfirm.setVisibility(View.GONE);
                    mPageText.setText(getString(R.string.new_reg));
                    mPageLink.setText(getString(R.string.link_reg));
                    mEmailSignInButton.setText(R.string.action_sign_in);
                    mReg = false;
                }
            }
        });
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
                showProgress(true);
                mAuthTask = new UserLogin(email, password);
                mAuthTask.execute((Void) null);

        }
    }

    //
    // Attempt Register
    //
    public void attemptRegister() {
        if (mAuthTaskReg != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordConfirm.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();



        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if(TextUtils.isEmpty(passwordConfirm))
        {
            mPasswordConfirm.setError(getString(R.string.error_field_required));
            focusView = mPasswordConfirm;
            cancel = true;
        } else if(!password.equals(passwordConfirm)) {
            mPasswordConfirm.setError(getString(R.string.error_password_confirm));
            focusView = mPasswordConfirm;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user register attempt.
            mAuthTaskCheck = new UserCheck(email, password);
            mAuthTaskCheck.execute();
        }
    }




    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    private class UserLogin extends AsyncTask<Void, Void, User> {
        private User u = new User();

        public UserLogin(String email, String password) {
            u.setEmail(email);
            u.setPassword(password);
        }
        @Override
        protected User doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //Login
            try {
                return restTemplate.postForObject("http://weservice.azurewebsites.net/api/Users/login", u, User.class);
            } catch(HttpClientErrorException e) {
                u.setEmail("HTTP");
                u.setPassword("");
                return u;
            } catch(RestClientException e) {
                u.setEmail("REST");
                u.setPassword("");
                return u;
            }

        }

        //@Override
        protected void onPostExecute(User u) {
            View focusView = null;
            // LOGIN
            if(u.getEmail().equals("REST"))
            {
                mEmailView.setError(getString(R.string.error_server_side));
                focusView.requestFocus();
                mAuthTask = null;
                showProgress(false);
            }
            if(u.getEmail().equals("HTTP"))
            {
                mEmailView.setError(getString(R.string.error_server_side));
                focusView.requestFocus();
                mAuthTask = null;
                showProgress(false);
            }

            if(u.getEmail() == null || u.getEmail().isEmpty())
            {
                mEmailView.setError(getString(R.string.error_email));
                focusView.requestFocus();
                mAuthTask = null;
                showProgress(false);
            }
            else if(u.getEmail().equals("ok"))
            {
                mPasswordView.setError(getString(R.string.error_password));
                focusView.requestFocus();
                mAuthTask = null;
                showProgress(false);

            }
            else
            {
                ((AppUser) getApplication()).setUser(u);

                mSession.putUserEmail(u.getEmail());
                mSession.putUserId(u.getUserId());

                // Finish this activity
                finish();

                // Start the main activity
                Intent i = new Intent(getApplicationContext(), SetupActivity.class);
                startActivity(i);
            }

        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

    // Register
    private class UserRegister extends AsyncTask<Void, Void, User> {
        private User u = new User();

        public UserRegister(User user)
        {
            u = user;
        }
        @Override
        protected User doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //Register
            try {
                return restTemplate.postForObject("http://weservice.azurewebsites.net/api/Users", u, User.class);
            } catch(HttpClientErrorException e) {
                u.setEmail("");
                u.setPassword("");
                return u;
            } catch(RestClientException e) {
                u.setEmail("");
                u.setPassword("");
                return u;
            }

        }

        //@Override
        protected void onPostExecute(User u) {
            View focusView = null;
            // REGISTER
            if(u.getEmail() == null || u.getPassword() == null || u.getEmail().isEmpty() ||  u.getPassword().isEmpty())
            {
                // REST or HTTP Error Occurred
                mAuthTask = null;
                showProgress(false);
                mEmailView.setError(getString(R.string.error_server_side));
                focusView.requestFocus();
            }
            else
            {
                ((AppUser) getApplication()).setUser(u);

                mSession.putUserEmail(u.getEmail());
                mSession.putUserId(u.getUserId());

                // Finish this activity
                finish();

                // Start the main activity
                Intent i = new Intent(getApplicationContext(), SetupActivity.class);
                startActivity(i);
            }

        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    // Register
    private class UserCheck extends AsyncTask<Void, Void, String> {
        User u = new User();
        public UserCheck(String email, String password) {
            u.setEmail(email);
            u.setPassword(password);
        }

        @Override
        protected String doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            // Check
            try {
                return restTemplate.postForObject("http://weservice.azurewebsites.net/api/Users/check", u, String.class);
            } catch (HttpClientErrorException e) {
                return "HTTP Error";
            } catch (RestClientException e) {
                return "REST Error";
            }

        }

        //@Override
        protected void onPostExecute(String s) {
            if(s.equals("Exists")) {
                // Email exists
                View focusView = mEmailView;
                mAuthTaskReg = null;
                showProgress(false);
                mEmailView.setError(getString(R.string.error_email_exists));
                focusView.requestFocus();
            } else if(s.equals("Available")) {
                // Email is Available
                User u = new User();
                showProgress(true);
                u.setEmail(u.getEmail());
                u.setPassword(u.getPassword());
                mAuthTaskReg = new UserRegister(u);
                mAuthTaskReg.execute((Void) null);
            } else if(s.equals("HTTP Error")) {
                // HTTP Error
                mAuthTaskReg = null;
                showProgress(false);
                Toast.makeText(getApplicationContext(), "HTTP Error, please try again",
                        Toast.LENGTH_LONG).show();
            } else {
                // REST Error
                mAuthTaskReg = null;
                showProgress(false);
                Toast.makeText(getApplicationContext(), "REST Error, please try again",
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}



