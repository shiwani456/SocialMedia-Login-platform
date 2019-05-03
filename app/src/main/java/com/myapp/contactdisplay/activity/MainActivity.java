package com.myapp.contactdisplay.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.myapp.contactdisplay.R;
import com.myapp.contactdisplay.application.ActivityUserSpace;
import com.myapp.contactdisplay.helper.HelperClass;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import twitter4j.Twitter;
import twitter4j.User;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ActivityUserSpace session = null;
    private LoginButton login_button;
    private CallbackManager callbackManager = null;
    private Object FacebookCallback;
    private TwitterLoginButton twitterLoginButton;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    private Twitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new ActivityUserSpace(this).getInstance(MainActivity.this);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));


        login_button = findViewById(R.id.login_button);
        twitterLoginButton = findViewById(R.id.twitterLoginButton);
        facebookLogin();
        googlePlusLogin();
        twitterLogin();
    }


    private void googlePlusLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void twitterLogin() {
        if (session.getUserName() != null) {
            Intent intent = new Intent(MainActivity.this, LandingActivity.class);
            startActivity(intent);
        } else {


            twitterLoginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    TwitterAuthToken authToken = twitterSession.getAuthToken();
                    String token = authToken.token;
                    String secret = authToken.secret;
                    String name = twitterSession.getUserName();
                    session.setUserName(name);
                    Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                    startActivity(intent);
                    finish();


                }

                @Override
                public void failure(TwitterException exception) {

                }
            });

        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("data", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e("data", "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            session.setUserName(personName);
            session.setUserEmail(email);

            Log.e("data", "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);


        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    private void facebookLogin() {

        Toast.makeText(getApplicationContext(), "facebook login!", Toast.LENGTH_SHORT).show();
        if (session.getUserName() != null) {
            Intent intent = new Intent(MainActivity.this, LandingActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Logged in if inside!", Toast.LENGTH_SHORT).show();


            AppEventsLogger.activateApp(this);
            callbackManager = CallbackManager.Factory.create();
            login_button.setReadPermissions("email", "public_profile");
            Toast.makeText(getApplicationContext(), "Logged in public profilr called", Toast.LENGTH_SHORT).show();

            HelperClass.setStatusBarColored(this);

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

            login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    Toast.makeText(getApplicationContext(), "Logged in successfully!", Toast.LENGTH_SHORT).show();
                    AccessToken accessToken = loginResult.getAccessToken();
                    getUserProfile(accessToken);
                    Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "Logged in cencel!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getApplicationContext(), "Logged in error!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


    private void getUserProfile(com.facebook.AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("TAG", object.toString());

                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    /* val user_mobile_phone = `object`.getString("user_mobile_phone ")*/
                    String id = object.getString("id");


                    session.setUserName(first_name + " " + last_name);
                    session.setUserEmail(email);

                    /* session!!.setUserMobilePhone(user_mobile_phone)*/

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Logged in exception!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
