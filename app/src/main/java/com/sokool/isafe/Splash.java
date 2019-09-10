package com.sokool.isafe;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (checkAndRequestPermissions()) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Intent mainIntent;
                    if(user != null){
                         mainIntent = new Intent(getBaseContext(), MainActivity.class);
                    }else {
                         mainIntent = new Intent(getBaseContext(), RegistrationActivity.class);
                    }
//                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 2000);
        }
//        MyMessageService myMessageService = new MyMessageService();
//        myMessageService.

        checkAndRequestPermissions();

    }

    private boolean checkAndRequestPermissions() {


        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CALL_PHONE,
                    },
                    1);

            return false;


        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults.length != 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Intent mainIntent;
                        if(user != null){
                            mainIntent = new Intent(getBaseContext(), MainActivity.class);
                        }else {
                            mainIntent = new Intent(getBaseContext(), RegistrationActivity.class);
                        }
                        startActivity(mainIntent);
                        finish();


                    } else {

                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }


            }
        }
    }


}
