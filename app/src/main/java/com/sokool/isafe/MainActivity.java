package com.sokool.isafe;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    protected Button callForHelp;
    protected Button budies;
    protected Button insurance;
    protected Guideline guideline;
    protected Guideline guideline2;
    private int numMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(toolbar);


        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Tag", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                        Log.e("token", token);
                    }
                });

        String msg = getIntent().getStringExtra("message");
        if(msg!= null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            EmergenceDialogFragment emergenceDialogFragment =  EmergenceDialogFragment.newInstance(msg,msg,msg);
        emergenceDialogFragment.show(fragmentManager,"emergency");
        }



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

    public void notif(String title, String body) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext());
        Intent ii = new Intent(getApplicationContext(), MainActivity.class);
        ii.putExtra("message",body);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(body);
        bigText.setBigContentTitle(title);
        bigText.setSummaryText("Accident");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(body);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_i d";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        callForHelp = (Button) findViewById(R.id.call_for_help);
        callForHelp.setOnClickListener(MainActivity.this);
        budies = (Button) findViewById(R.id.buddies);
        budies.setOnClickListener(MainActivity.this);
        insurance = (Button) findViewById(R.id.insurance);
        insurance.setOnClickListener(MainActivity.this);
        guideline = (Guideline) findViewById(R.id.guideline);
        guideline2 = (Guideline) findViewById(R.id.guideline2);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.call_for_help) {
//            notif("Paul has got an accident","Paul has got an accident at Bukoto street");

            FragmentManager fragmentManager = getSupportFragmentManager();
            CallDialogFragment callDialogFragment = new CallDialogFragment();
            callDialogFragment.show(fragmentManager,"call");

        } else if (view.getId() == R.id.buddies) {
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
