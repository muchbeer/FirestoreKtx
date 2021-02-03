package raum.muchbeer.firestorektx.utility

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.NullPointerException


class MyFirebaseMessageService : FirebaseMessagingService() {


     companion object {
        const val LOG_TAG = "MyFirebaseMessageSe"

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(LOG_TAG, "tHE Token value is : ${token}")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
         val notificationData : String
        val notificationBody : String?
          val notificationTitle : String?

        try{
          notificationData  = remoteMessage.data.toString();
         notificationBody = remoteMessage.notification?.body //notification.body;
           notificationTitle = remoteMessage.notification?.title
            Log.d(LOG_TAG, "Notification Body is: ${notificationBody}");
            Log.d(LOG_TAG, "Notification Title is:  ${notificationTitle}" );
            Log.d(LOG_TAG, "Notification Data is: ${notificationData}");
        }catch (e : NullPointerException) {
            Log.d(LOG_TAG, "NullPointException error is: ${e.message}");
        }


       // showNotification(notificationTitle, notificationBody);*/
    }

    }
