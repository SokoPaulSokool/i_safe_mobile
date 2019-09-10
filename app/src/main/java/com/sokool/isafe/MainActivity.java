package com.sokool.isafe;

import android.os.Bundle;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    protected Button callForHelp;
    protected Button budies;
    protected Button insurance;
    protected Guideline guideline;
    protected Guideline guideline2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(toolbar);


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

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        callForHelp = (Button) findViewById(R.id.call_for_help);
        callForHelp.setOnClickListener(MainActivity.this);
        budies = (Button) findViewById(R.id.budies);
        budies.setOnClickListener(MainActivity.this);
        insurance = (Button) findViewById(R.id.insurance);
        insurance.setOnClickListener(MainActivity.this);
        guideline = (Guideline) findViewById(R.id.guideline);
        guideline2 = (Guideline) findViewById(R.id.guideline2);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.call_for_help) {

        } else if (view.getId() == R.id.budies) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ContactsDialogFragment contactsDialogFragment =  ContactsDialogFragment.newInstance("Buddies");
            contactsDialogFragment.show(fragmentManager,"Buddies");

        } else if (view.getId() == R.id.insurance) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ContactsDialogFragment contactsDialogFragment =  ContactsDialogFragment.newInstance("Insurance");
            contactsDialogFragment.show(fragmentManager,"Insurance");

        }
    }
}
