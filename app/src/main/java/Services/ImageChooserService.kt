package Services;
import Services.SharedPrferenceServiceForInsta
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.File
import androidx.core.content.FileProvider


import java.util.*


class ImageChooserService(val activity: Activity) {

    val APP_TAG = "Camera"
    lateinit var imagePreviewView: ImageView
    var mCurrentPhotoPath: String? = null
    var photoFile: File? = null
    val uuid = UUID.randomUUID()
    val mpath = "$uuid.png"
    internal var sharedPrferenceServiceForGenuCheck = SharedPrferenceServiceForInsta(activity)

    companion object {
        val MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE: Int = 5
        val SELECT_PICTURE: Int = 6
        val MY_PERMISSIONS_REQUEST_CAMERA = 7
        val TAKE_PICTURE: Int = 8
    }


    fun launchImageSelectionOption() {
        imagePreviewView = ImageView(activity)


        val imageSelectionOption = arrayOf<CharSequence>("Camera", "Gallery")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Select Option")
        builder.setItems(imageSelectionOption, { _, which ->
            when (which) {
                0 -> launchCamera()
                1 -> launchGalary()
                else -> {
                }
            }
        })
        builder.show()
    }

    fun launchGalary() {

        if (!AppPermissionsService(activity).isPermissionsGranted(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
            AppPermissionsService(activity).askForPermissions(
                    arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE)
        } else
            showGalary()

    }

    fun showGalary() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT


        activity.startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE)
    }

    fun launchCamera() {

        if (!AppPermissionsService(activity).isPermissionsGranted(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            AppPermissionsService(activity).askForPermissions(
                    arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_CAMERA)
        } else
            takePhoto()
    }

    fun takePhoto() {
        /*  val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
         activity.startActivityForResult(cameraIntent, TAKE_PICTURE)*/

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference to access to future access

        photoFile = getPhotoFileUri(mpath)

        val fileProvider = FileProvider.getUriForFile(activity, "com.example.instagramclone.provider", photoFile!!)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        sharedPrferenceServiceForGenuCheck.setString("path", fileProvider.toString())
        activity.startActivityForResult(intent, TAKE_PICTURE)


    }


    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir = File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory")
        }
        // Return the file target for the photo based on filename

        return File(mediaStorageDir.getPath() + File.separator + fileName)
    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }


    @SuppressLint("NewApi")
    fun getRealPathFromURI(uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(activity, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().path + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id))

                return getDataColumn(activity, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf<String>(split[1])

                return getDataColumn(activity, contentUri, selection,
                        selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(activity, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)

        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     * The context.
     * @param uri
     * The Uri to query.
     * @param selection
     * (Optional) FilterActivity used in the query.
     * @param selectionArgs
     * (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(context: Context, uri: Uri?,
                      selection: String?, selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = uri?.let {
                context.contentResolver.query(it, projection,
                        selection, selectionArgs, null)
            }
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            if (cursor != null)
                cursor.close()
        }
        return null
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri
                .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri
                .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri
                .authority
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA ->
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                }
            MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE -> showGalary()
        }
    }
}