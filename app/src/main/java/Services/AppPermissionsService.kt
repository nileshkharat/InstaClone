package Services;

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AppPermissionsService(val activity: Activity) {

    fun isCallPermissionGranted(): Boolean {
        return !(ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
    }

    fun askForCallPermission(requestPermissionId : Int) {
        ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                requestPermissionId)
    }

    /**
     * Check if permissions granted
     *
     * @param permissions   List of permissions
     * @return Boolean             if all permissions are granted
     */
    fun isPermissionsGranted(permissions: Array<String>): Boolean {

        for (permission in permissions) {

            if ((ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)) {
                return false
            }
        }

        return true
    }

    /**
     * Get Permission Grant status
     *
     * @param permissions           List of permissions
     * @return Map<String, Boolean> Permissions vs granted status
     */
    fun getPermissionsGrantedStatus(permissions: Array<String>): Map<String, Boolean> {

        var permissionsStatus: HashMap<String, Boolean> = HashMap()

        for (permission in permissions) {

            if ((ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)) {
                permissionsStatus.put(permission, false)
            }else{
                permissionsStatus.put(permission, true)
            }
        }

        return permissionsStatus
    }


    /**
     * Ask for permissions
     *
     * @param permissions           List of permissions
     * @param requestPermissionId   Request id that could be used in
     *                              onActivityResults call back to identify
     *                              response for request
     */
    fun askForPermissions(permissions: Array<String>, requestPermissionId : Int) {
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions, requestPermissionId)
        }
    }

}