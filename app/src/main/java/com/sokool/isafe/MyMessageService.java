package com.sokool.isafe;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyMessageService extends FirebaseMessagingService {
    String TAG = "faa";

    public MyMessageService() {
    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("l", "llal");
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // ...
        Log.e("l", remoteMessage.getMessageId());
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            String title = remoteMessage.getNotification().getTitle();
            String message="";
            String location="";
            Log.e("dd",remoteMessage.getData().toString());


            if (remoteMessage.getData() != null) {
                 title = remoteMessage.getNotification().getTitle();
                 message = remoteMessage.getData().get("message");
                 location = remoteMessage.getData().get("location");
            } else {
//                title = remoteMessage.getNotification().getTitle();
//                String data = remoteMessage.getNotification().getBody();
//                try {
//                    JSONObject jsonObject = new JSONObject(data);
//
//                    message = jsonObject.getString("message");
//                    location = jsonObject.getString("location");
//                    Log.e(TAG, "Message data payload: " + title+message+location);
//                    notif(title,message,location);
//
//                } catch (JSONException e) {

//                }
            }

            notif(title,message,location);



//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
////                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
////                handleNow();
//            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public void notif(String title, String message, String location) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext());
        Intent ii = new Intent(getApplicationContext(), MainActivity.class);
        ii.putExtra("message",message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(message);
        bigText.setBigContentTitle(title);
        bigText.setSummaryText("Accident");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message+"\n"+location);
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


}
