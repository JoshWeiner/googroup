package dvorak.app;

import android.content.ContentValues;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Storage {

    public void write(String usr, String msg){

        SQLiteOpenHelper helper = open();

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("msgID", getLatest() + 1);
        cv.put("sender", usr);
        cv.put("content", msg);
        cv.put("time", new Date().toString());

        db.insert("msgs", null, cv);

    }

    public void write(String msg){

        SQLiteOpenHelper helper = open();

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("msgID", getLatest() + 1);
        cv.put("sender", "public");
        cv.put("content", msg);
        cv.put("time", new Date().toString());

        db.insert("msgs", null, cv);

    }

    public TreeMap<Integer, String[]> read(String usr){

        SQLiteOpenHelper helper = open();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT msgID, time, content FROM msgs WHERE sender = '" + usr + "'", null);

        TreeMap<Integer, String[]> rtrnMap = new TreeMap<Integer, String[]>();
        while(c.moveToNext()){
            String[] content = new String[2];
            content[0] = c.getString(1);
            content[1] = c.getString(2);
            rtrnMap.put(c.getInt(0), content);
        }

        return rtrnMap;

    } // CAREFUL !!! (TIME IS NOT RETURNED AS DATE OBJ, MUST USE DATEFORMAT.PARSE() TO CONVERT TO DATE OBJ)

    public void erase(String usr, String msg, int ID){

        SQLiteOpenHelper helper = open();

        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM msgs WHERE sender = '" + usr + "' AND content = '" + msg + "' AND msgID = '" + ID + "'");
    }

    public void purge(){

        SQLiteOpenHelper helper = open();

        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM msgs");

    }

    public ArrayList<String> usrs(){

        SQLiteOpenHelper helper = open();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT sender FROM msgs", null);

        ArrayList<String> rtrnArr = new ArrayList<String>();
        while(c.moveToNext()){
            rtrnArr.add(c.getString(0));
        }

        return rtrnArr;

    }

    public int getLatest(){

        SQLiteOpenHelper helper = open();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT msgID FROM msgs", null);

        int mx = 0;

        while(c.moveToNext()){
            int curr = c.getInt(0);
            if(curr > mx){
                mx = curr;
            }
        }

        return mx;

    }

    public SQLiteOpenHelper open(){

        SQLiteOpenHelper helper = new SQLiteOpenHelper(null, "msgs", null, 1, null) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE msgs (msgID INTEGER PRIMARY KEY, sender TEXT, content TEXT, time TEXT)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS msgs");
                onCreate(db);
            }
        };

        return helper;

    }
}
