package com.awetg.profiumtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.awetg.profiumtest.service.CapturePhotoService
import com.awetg.profiumtest.util.RuntimePermissionUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RuntimePermissionUtil.getInstance(this).requestAllUnGrantedermissions()

        auth = FirebaseAuth.getInstance()
//        val token = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).getString("TOKEN", "Not set")

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("DBG", "subscribe successfull")
                }
            }

        // create foreground service for activity tracker notification channel for android oreo or higher
        createNotificationChannel(
            "CHANNEL_ID",
            "CHANNEL_NAME",
            "CHANNEL_DESCTIPTION",
            NotificationManager.IMPORTANCE_LOW
        )

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser.toString())
    }


    private fun updateUI(currentUser:String){

    }


    // Creates notification channel with given params
    private fun createNotificationChannel(channelId: String, channelName: String, channelDescription: String, priority: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName,priority)
            channel.description = channelDescription
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}
