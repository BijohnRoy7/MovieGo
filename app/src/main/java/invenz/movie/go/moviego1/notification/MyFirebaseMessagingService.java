package invenz.movie.go.moviego1.notification;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessagingService;

import invenz.movie.go.moviego1.activities.MainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "ROY";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From (MyFirebaseMessagingService): " + remoteMessage.getFrom());
        Log.d(TAG, "onMessageReceived (MyFirebaseMessagingService): "+remoteMessage.getNotification().getBody());

        showNotification(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());

    }

    /*##### My method ######*/
    private void showNotification(String from, String notification) {

        /*#####                      MyNotificationManager object                    #######*/
        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);

        myNotificationManager.showMyNotification(from, notification, intent);
    }



}
