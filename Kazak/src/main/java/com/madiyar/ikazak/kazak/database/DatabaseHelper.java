package com.madiyar.ikazak.kazak.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.madiyar.ikazak.kazak.App;
import com.madiyar.ikazak.kazak.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by madiyar on 11/23/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_MAKAL = "MakalList";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TAG = "tag";
    public static final String COLUMN_POINT = "point";
    public static final String COLUMN_CONTENT = "content";

    public static final String TABLE_CAT = "MakalCategory";
    public static final String COLUMN_ID_C = "_id";
    public static final String COLUMN_TAG_C = "tag";
    public static final String COLUMN_CONTENT_C = "content";

    public static final String TABLE_WRITER_LIST  = "WriterList";
    public static final String COLUMN_ID_WL = "id";
    public static final String COLUMN_TAG_WL = "tag";
//    public static final String COLUMN_POINT_WL = "point";
    public static final String COLUMN_CONTENT_WL = "name";
    public static final String COLUMN_BIOGRAPHY_WL = "biography";

    public static final String TABLE_WRITER_LYRICS_LIST= "WriterLyricsList";
    public static final String COLUMN_ID_WLL = "id";
    public static final String COLUMN_TAG_WLL = "tag";
    public static final String COLUMN_LYRIC_WLL = "point";
    public static final String COLUMN_CONTENT_WLL = "content";


    public static final String TABLE_GAME = "GameNational";
    public static final String COLUMN_ID_GAME = "id";
    public static final String COLUMN_TAG_GAME = "tag";
    public static final String COLUMN_NAME_GAME = "name";
    public static final String COLUMN_CONTENT_GAME = "content";


    public static final String TABLE_TRADITION= "TraditionList";
    public static final String COLUMN_ID_TRADITION = "id";
    public static final String COLUMN_TAG_TRADITION = "tag";
    public static final String COLUMN_TRADITION_TITLE = "title";
    public static final String COLUMN_TRADITION_CONTENT = "content";


    public static final String TABLE_TRADITION_CAT= "TraditionCategory";
    public static final String COLUMN_ID_TRADITION_CAT = "id";
    public static final String COLUMN_TAG_TRADITION_CAT = "tag";
    public static final String COLUMN_TRADITION_CAT_CONTENT = "content";

    public static final String COLUMN_TAG_TRADITION_VIEW = "tag";

    private Context mycontext;
    private static String DB_NAME = App.getContext().getResources().getString(R.string.database);
//    private static String DB_NAME = "database_kk.sqlite";
    public SQLiteDatabase myDataBase;
    private String DB_PATH = "/com.madiyar.ikazak.kazak/databases/";

//    public static  String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS WriterLyricsList";

    public DatabaseHelper(Context context)  throws IOException {
        super(context,DB_NAME,null,2);
        this.mycontext=context;
        boolean dbexist = checkDatabase();
        if (dbexist) {
            System.out.println("Database exists");
            openDatabase();
        } else {
            System.out.println("Database doesn't exist");
            createDatabase();
        }
    }

    public void createDatabase() throws IOException {
        boolean dbexist = checkDatabase();
        if(dbexist) {
            System.out.println(" Database created exists.");
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
//                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }
    private void copyDatabase() throws IOException {
        InputStream myinput = mycontext.getAssets().open(DB_NAME);
        OutputStream myoutput = new FileOutputStream("/data/data//com.madiyar.ikazak.kazak/databases/"+DB_NAME);
//        database_kk.sqlite

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }
    private void openDatabase() {
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
