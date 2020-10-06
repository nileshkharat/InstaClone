package SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;

import Models.StoriesModel;

public class CreateStoriesDB {

    SQLiteDBUtil sdbutil;
    private static final String TAG = "AppUserDBHelperLog";

    public CreateStoriesDB(Context context) {
        sdbutil = new SQLiteDBUtil(context);
        Log.d(TAG, "StoriesDB: Created the new SQLiteDBUtil object " + sdbutil.toString());
    }

    public long insertStories(StoriesModel user) {
        ContentValues values = new ContentValues();

        try {
            values.put("story_title", user.getStory_title());
            values.put("stories_dis", user.getStories_dis());
            values.put("stories_hastags", user.getStories_hastags());
            values.put("image", user.getImage());


            return sdbutil.insertData("story", values);
        } finally {
            sdbutil.close();
        }

    }


    public ArrayList<StoriesModel> getAllStories(){
        SQLiteDatabase liteDatabase = this.sdbutil.getReadableDatabase();
        ArrayList<StoriesModel> users = new ArrayList<>();

        try{
            Cursor cursor = liteDatabase.rawQuery("Select * from story",null);
            cursor.moveToFirst();
            while(cursor.isAfterLast()==false){
                StoriesModel user = new StoriesModel();
                user.setStory_title(cursor.getString(cursor.getColumnIndex("story_title")));
                user.setStories_dis(cursor.getString(cursor.getColumnIndex("stories_dis")));
                user.setStories_hastags(cursor.getString(cursor.getColumnIndex("stories_hastags")));
                user.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
                users.add(user);
                cursor.moveToNext();
            }


            return users;


        }catch (Exception e){
            e.printStackTrace();
        }

        return  users;

    }



}
