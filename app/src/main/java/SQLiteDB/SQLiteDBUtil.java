package SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDBUtil extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteDBUtilLog";

    public SQLiteDBUtil(Context context) {
        super(context, "Insta.db", null, 1);
        createAllTables(this.getWritableDatabase());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createAllTables(db);
       Log.d(TAG, "onCreate: All tables created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }

    private void createAllTables(SQLiteDatabase db) {

        createStoryTable(db);
        createPostTable(db);



    }

    private void createPostTable(SQLiteDatabase db) {
        try {
            String strURL = "CREATE TABLE IF NOT EXISTS story (" +
                    " `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " `title` TEXT," +
                    " `des` TEXT," +

                    " `feed_image` BLOB," +
                    " dateCreated DATETIME default current_timestamp)";
            db.execSQL(strURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createStoryTable(SQLiteDatabase db) {
        try {
            String strURL = "CREATE TABLE IF NOT EXISTS story (" +
                    " `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " `story_title` TEXT," +
                    " `stories_dis` TEXT," +
                
                    " `image` BLOB," +
                    " dateCreated DATETIME default current_timestamp)";
            db.execSQL(strURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public long insertData(String tableName, ContentValues cv) {
        long flag = 0;
        try {
            SQLiteDatabase s = this.getWritableDatabase();
            flag = s.insert(tableName, null, cv);
            //String rslt = s.
        } catch (Exception e) {
            Log.d(TAG, "insertData: Error - " + e.getMessage());
        }
        Log.d(TAG, "insertData: fag value is: " + flag);
        if (flag == -1) {
            Log.d(TAG, "insertData: Successfully inserted data in " + tableName);

        } else {
            Log.d(TAG, "insertData: Unable to insert data in " + tableName);

        }
        return flag;
    }


}

