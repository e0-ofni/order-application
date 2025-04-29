package com.example.termproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DatabaseHelperSub extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "databasesub";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelperSub(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseSub.SubMenu._CREATE0); // 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        sqLiteDatabase.execSQL(DatabaseSub.SubMenu._DELETE0);
        onCreate(sqLiteDatabase);
    }

    void insertRecord(String image, String name, int price) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseSub.SubMenu.MENUIMAGE, image);
        values.put(DatabaseSub.SubMenu.MENUNAME, name);
        values.put(DatabaseSub.SubMenu.PRICE, price);

        db.insert(DatabaseSub.SubMenu._TABLENAME0, null, values);
    }

    void updateRecord(int id, String name, int price) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseSub.SubMenu.MENUNAME, name);
        values.put(DatabaseSub.SubMenu.PRICE, price);

        db.update(DatabaseSub.SubMenu._TABLENAME0, values, DatabaseSub.SubMenu._ID + "=" + id, null);
    }

    void deleteRecord(int id) {
        SQLiteDatabase db = getReadableDatabase();

        db.delete(DatabaseSub.SubMenu._TABLENAME0, DatabaseSub.SubMenu._ID + "=" + id, null);
    }


    public Cursor readRecordOrderByID() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                DatabaseSub.SubMenu.MENUIMAGE,
                DatabaseSub.SubMenu.MENUNAME,
                DatabaseSub.SubMenu.PRICE
        };

        String sortOrder = DatabaseSub.SubMenu._ID + " ASC";

        Cursor cursor = db.query(
                DatabaseSub.SubMenu._TABLENAME0,   // The table to query
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