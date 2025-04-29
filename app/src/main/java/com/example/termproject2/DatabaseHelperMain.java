package com.example.termproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelperMain extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "databasemain";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelperMain(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseMain.Curry._CREATE0); // 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        sqLiteDatabase.execSQL(DatabaseMain.Curry._DELETE0);
        onCreate(sqLiteDatabase);
    }

    void insertRecord(String image, String name, String info, int price) {

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMain.Curry.MENUIMAGE, image);
        values.put(DatabaseMain.Curry.MENUNAME, name);
        values.put(DatabaseMain.Curry.MENUINFO, info);
        values.put(DatabaseMain.Curry.PRICE, price);

        db.insert(DatabaseMain.Curry._TABLENAME0, null, values);
    }
    void updateRecord(int id, String name, String info, int price) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseMain.Curry.MENUNAME, name);
        values.put(DatabaseMain.Curry.MENUINFO, info);
        values.put(DatabaseMain.Curry.PRICE, price);

        db.update(DatabaseMain.Curry._TABLENAME0,  values, DatabaseMain.Curry._ID + "=" + id,null);
    }

    void deleteRecord(int id) {
        SQLiteDatabase db = getReadableDatabase();

        db.delete(DatabaseMain.Curry._TABLENAME0, DatabaseMain.Curry._ID + "=" + id, null);
    }


    public Cursor readRecordOrderByID() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                DatabaseMain.Curry.MENUIMAGE,
                DatabaseMain.Curry.MENUNAME,
                DatabaseMain.Curry.MENUINFO,
                DatabaseMain.Curry.PRICE
        };

        String sortOrder = DatabaseMain.Curry._ID + " ASC";

        Cursor cursor = db.query(
                DatabaseMain.Curry._TABLENAME0,   // The table to query
                projection,   // The array of columns to return (pass null to get all)
                null,   // where 문에 필요한 column
                null,   // where 문에 필요한 value
                null,   // group by를 적용할 column
                null,   // having 절
                sortOrder   // 정렬 방식
        );

        return cursor;
    }
}