package com.madiyar.ikazak.kazak.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by madiyar on 11/23/14.
 */
public class DataBaseSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns_makal_list = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TAG, DatabaseHelper.COLUMN_POINT, DatabaseHelper.COLUMN_CONTENT};

    //    private String[] allColumns_writer_list = { DatabaseHelper.COLUMN_ID_WL,DatabaseHelper.COLUMN_TAG_WL,DatabaseHelper.COLUMN_CONTENT_WL};
    private String[] allColumns_writer_lyric_list = {DatabaseHelper.COLUMN_ID_WLL, DatabaseHelper.COLUMN_TAG_WLL, DatabaseHelper.COLUMN_CONTENT_WLL};


    public DataBaseSource(Context context) {
        try {
            dbHelper = new DatabaseHelper(context);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Makal createMakal(String tag, String point, String content) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TAG, tag);
        values.put(DatabaseHelper.COLUMN_POINT, point);
        values.put(DatabaseHelper.COLUMN_CONTENT, content);

        long insertId = database.insert(DatabaseHelper.TABLE_MAKAL, null,
                values);

        Cursor cursor = database.query(DatabaseHelper.TABLE_MAKAL,
                allColumns_makal_list, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Makal newMakal = cursorToMakal(cursor);
        cursor.close();

        return newMakal;
    }

    private Makal cursorToWriter(Cursor cursor) {
        Makal comment = new Makal();
        comment.setId(cursor.getLong(0));//id
        comment.setTag(cursor.getString(1));//tag
        comment.setConent(cursor.getString(2));//lyric name
        comment.setPoint(cursor.getString(3));//lyric content

        return comment;
    }

    private Makal cursorToMakal(Cursor cursor) {
        Makal comment = new Makal();
        comment.setId(cursor.getLong(0));
        comment.setTag(cursor.getString(1));
        comment.setPoint(cursor.getString(2));
        comment.setConent(cursor.getString(3));

        return comment;
    }

    private Makal cursorToTradition(Cursor cursor) {
        Makal comment = new Makal();
        comment.setId(cursor.getLong(0));
        comment.setTag(cursor.getString(1));
        comment.setConent(cursor.getString(2));
        comment.setPoint(cursor.getString(3));
        return comment;
    }
    private Makal cursorToTraditionList(Cursor cursor) {
        Makal comment = new Makal();
        comment.setId(cursor.getLong(0));
        comment.setTag(cursor.getString(1));
        comment.setConent(cursor.getString(2));

        return comment;
    }

    private MakalCategory cursorToCategory(Cursor cursor) {

        MakalCategory comment = new MakalCategory();
        comment.setId(cursor.getLong(0));
        comment.setTag(cursor.getString(1));
        comment.setConent(cursor.getString(2));

        return comment;
    }
    private MakalCategory cursorToCategoryWriter(Cursor cursor) {

        MakalCategory comment = new MakalCategory();
        comment.setId(cursor.getLong(0));
        comment.setTag(cursor.getString(1));
        comment.setConent(cursor.getString(2));
        comment.setBiography(cursor.getString(3));

        return comment;
    }

    private MakalCategory cursorToGame(Cursor cursor) {

        MakalCategory comment = new MakalCategory();
        comment.setId(cursor.getLong(0));
        comment.setTag(cursor.getString(1));
        comment.setConent(cursor.getString(2));
        comment.setBiography(cursor.getString(3));

        return comment;
    }


    public void deleteAllMakal() {
        database.delete(DatabaseHelper.TABLE_MAKAL, null, null);
    }

    public Makal getMakalByTag(String id) {
        Makal makal = null;
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM makal WHERE _id = " + id + "", null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    makal = cursorToMakal(cursor);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return makal;
    }

    public Makal getMakalByPoint(String point) {
        Makal makal = null;
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery("SELECT * FROM MakalList WHERE point = '" + point + "'", null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    makal = cursorToMakal(cursor);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return makal;
    }

    public List<Makal> getAllMakal() {
        List<Makal> comments = new ArrayList<Makal>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_MAKAL, allColumns_makal_list, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Makal comment = cursorToMakal(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    public List<Makal> getMakalListByTag(String tableName, String[] all_column, String tag) {
        List<Makal> comments = null;
        if (database.isOpen()) {
            comments = new ArrayList<Makal>();
//            Cursor cursor = database.query(tableName, all_column, DatabaseHelper.COLUMN_TAG + "='" + tag + "'", null, null, null, null);
            Cursor cursor = database.query(tableName, all_column, DatabaseHelper.COLUMN_TAG_WLL + "='" + tag + "'", null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Makal makal = cursorToMakal(cursor);
                    comments.add(makal);
                    cursor.moveToNext();
                }

            }
            cursor.close();
        }

        return comments;
    }


    public List<Makal> getWriter(String tableName, String[] all_column, String tag) {
        List<Makal> comments = null;
        if (database.isOpen()) {
            comments = new ArrayList<Makal>();
//            Cursor cursor = database.query(tableName, all_column, DatabaseHelper.COLUMN_TAG + "='" + tag + "'", null, null, null, null);
            Cursor cursor = database.query(tableName, all_column, DatabaseHelper.COLUMN_TAG_WLL + "='" + tag + "'", null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Makal makal = cursorToWriter(cursor);
                    comments.add(makal);
                    cursor.moveToNext();
                }

            }
            cursor.close();
        }

        return comments;
    }

    public List<Makal> getTradition(String tableName, String[] all_column, String tag) {
        List<Makal> comments = null;
        if (database.isOpen()) {
            comments = new ArrayList<Makal>();
//            Cursor cursor = database.query(tableName, all_column, DatabaseHelper.COLUMN_TAG + "='" + tag + "'", null, null, null, null);
            Cursor cursor = database.query(tableName, all_column, DatabaseHelper.COLUMN_TAG_TRADITION_VIEW + "='" + tag + "'", null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Makal makal = cursorToTradition(cursor);
                    comments.add(makal);
                    cursor.moveToNext();
                }

            }
            cursor.close();
        }

        return comments;
    }


    public List<Makal> getMakalListByPoint(String point) {
        List<Makal> comments = null;
        if (database.isOpen()) {
            comments = new ArrayList<Makal>();
            Cursor cursor = database.query(DatabaseHelper.TABLE_MAKAL,
                    allColumns_makal_list, DatabaseHelper.COLUMN_POINT + "='" + point + "'", null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Makal makal = cursorToMakal(cursor);
                    comments.add(makal);
                    cursor.moveToNext();
                }

            }
            cursor.close();
        }

        return comments;
    }

    public List<MakalCategory> getAllCategory(String tableName, String[] allColumns) {
        List<MakalCategory> comments = new ArrayList<MakalCategory>();
        Cursor cursor = database.query(tableName, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MakalCategory comment = cursorToCategory(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    public List<MakalCategory> getAllGames(String tableName, String[] allColumns) {
        List<MakalCategory> comments = new ArrayList<MakalCategory>();
        Cursor cursor = database.query(tableName, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MakalCategory comment = cursorToGame(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    public List<MakalCategory> getWriterList(String tableName, String[] allColumns) {
        List<MakalCategory> comments = new ArrayList<MakalCategory>();
        Cursor cursor = database.query(tableName, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MakalCategory comment = cursorToCategoryWriter(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }
}
