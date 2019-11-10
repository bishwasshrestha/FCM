package com.awetg.profiumtest.util

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class RuntimePermissionUtil private constructor (private val activity: Activity) {

    // add all permissions here
    private val requiredPermissions = arrayOf<String>(
        android.Manifest.permission.CAMERA
    )

    fun requestPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE)
    }

    companion object : SingletonHolder<RuntimePermissionUtil, Activity>(::RuntimePermissionUtil) {
        const val PERMISSION_REQUEST_CODE = 1111
    }

    fun requestAllUnGrantedermissions() {
        val unGrantedPermissions = requiredPermissions.filter {
            ActivityCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }
//        val permissionsWithReuestRationale = unGrantedPermissions.filter {
//            ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
//        }
        if (unGrantedPermissions.isNotEmpty())
            requestPermissions(Array(unGrantedPermissions.size) {unGrantedPermissions[it]})
    }

    // An activityType requesting permission can listen to onRequestPermissionsResult and take further action with this method if needed
    fun showDialogAndAsk(title: String, message: String, onPositiveResponse: DialogInterface.OnClickListener? = null, onNegativeResponse: DialogInterface.OnClickListener? = null) {
        val alterDialog = AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", onPositiveResponse)
            .setCancelable(false)
        if (onNegativeResponse != null) alterDialog.setNegativeButton("No", onNegativeResponse)
        alterDialog.show()
    }

    fun isPermissionAvailable(permission: String): Boolean = ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED
}