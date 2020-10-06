package Services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;


import com.example.instagramclone.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class Util {
    Context context;
    ProgressDialog progressDialog;

    public Util(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
    }


    // Show progress Dialouge
    public void showProgress() {
        progressDialog.show();
    }

    //stop progress Dialouge
    public void stopProgress() {
        progressDialog.cancel();
        progressDialog.dismiss();
    }

    // pattern to check Email is valid or not valid
    private static final Pattern emails = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+");


    // Move from one Activity to another
    public static void switchActivities(Activity context, Class activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
        context.finish();


    }


    // Method to check email is in proper format or not
    public static boolean isValidEmail(CharSequence email) {
        return email != null && emails.matcher(email).matches();
    }

// to get Scaled bitmap Image
    public static Bitmap getScaledBitmap(Bitmap bitmap){
        return Bitmap.createScaledBitmap( bitmap, 620, 580, true);
    }


    //
    public static Bitmap generateBitmap(Activity activity, Intent data)  {
        Uri uri = data.getData();
        try {

            return getScaledBitmap( MediaStore.Images.Media.getBitmap(activity.getContentResolver(),  uri));


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    // Converting BitMap Image to Bytre Array
    public static  byte[] generatetByteArray(Bitmap bitmap) {
        if(bitmap!=null){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            return bos.toByteArray();
        }
        else{
            return null;
        }

    }


    //Reversing Back Byte Array to BitMap
    public  static Bitmap getBitmap(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap , 0, bitmap.length);
    }

}
