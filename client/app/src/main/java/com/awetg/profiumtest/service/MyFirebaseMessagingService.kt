package com.awetg.profiumtest.service

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data: Map<String, String> = remoteMessage.data
        val action = data["action"]
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, action, Toast.LENGTH_LONG).show()
        }
        if (action != null) {
//            startService(Intent(baseContext, CapturePhotoService::class.java))
//            CapturePhotoService.enqueueWork(baseContext, Intent(baseContext, CapturePhotoService::class.java))
            ContextCompat.startForegroundService(baseContext, Intent(baseContext, CapturePhotoService::class.java))
        }
    }

    override fun onNewToken(token: String) {
        getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            .edit()
            .putString("TOKEN", token)
            .apply()
    }
}