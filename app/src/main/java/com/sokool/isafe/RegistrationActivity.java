package com.sokool.isafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sokool.isafe.interfaces.DataInterface;

public class RegistrationActivity extends AppCompatActivity implements DataInterface {

    protected LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_registration);
        initView();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateAccountFragment createAccountFragment = new CreateAccountFragment();
        fragmentTransaction.add(R.id.view, createAccountFragment, "createAccountFragment");
        fragmentTransaction.commit();
    }

    private void initView() {
        view = (LinearLayout) findViewById(R.id.view);
    }

    @Override
    public void sendData(String data) {
        if (data.equals("account")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AddDeviceFragment addDeviceFragment = new AddDeviceFragment();
            fragmentTransaction.replace(R.id.view, addDeviceFragment, "addDeviceFragment");
            fragmentTransaction.commit();
        }
        if (data.equals("device")) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            this.startActivity(mainIntent);
            finish();
        }

    }
}
