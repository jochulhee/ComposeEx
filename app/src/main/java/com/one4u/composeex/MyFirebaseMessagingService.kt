package com.one4u.composeex

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("", "onNewToken : $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("", "onMessageReceived data : ${message.data}")
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "new message\n${message.data}", Toast.LENGTH_SHORT).show()
        }
    }
}