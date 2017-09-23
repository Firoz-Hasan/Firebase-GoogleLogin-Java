package com.bdjobs.training.firebaseexercise;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Personal_info extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    SessionManagement person_info_management;
    TextView usernameTV, emailTV;
    ImageView profilepicIMV;
    Button signoutBTN;
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        usernameTV = (TextView) findViewById(R.id.usernameTV);
        emailTV = (TextView) findViewById(R.id.emailTV);
        signoutBTN = (Button) findViewById(R.id.signoutBTN);
        profilepicIMV = (ImageView) findViewById(R.id.profilepicIMV);


        person_info_management = new SessionManagement(getApplicationContext());
        googleAuthentication();


        usernameTV.setText(person_info_management.getUserName());
        emailTV.setText(person_info_management.getEmail());
        //  profilepicIMV.setImageResource(R.drawable.googleg_standard_color_18);
        //profilepicIMV.setImageBitmap(getBitmapFromURL(person_info_management.getPhotoUrl()));
        //   Toast.makeText(this, person_info_management.getPhotoUrl(), Toast.LENGTH_SHORT).show();
//        String myURL = person_info_management.getPhotoUrl();
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeFile(String.valueOf(new URL(myURL).openStream()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        profilepicIMV.setImageBitmap(bmp);

        signoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    private void signOut() {

        mAuth.signOut();
        person_info_management.logoutUser();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //  Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        //  startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                        person_info_management.logoutUser();
//                        Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);

                    }
                });

    }

    private void googleAuthentication() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(Personal_info.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
