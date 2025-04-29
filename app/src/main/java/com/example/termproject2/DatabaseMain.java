package com.example.termproject2;

import android.provider.BaseColumns;

public final class DatabaseMain {
    public static final class Curry implements BaseColumns {
        public static final String MENUIMAGE = "menuimage";
        public static final String MENUNAME = "menuname";
        public static final String MENUINFO = "menuinfo";
        public static final String PRICE = "price";
        public static final String _TABLENAME0 = "maintable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +MENUIMAGE+" text not null , "
                +MENUNAME+" text not null , "
                +MENUINFO+" text not null , "
                +PRICE+" integer not null);";
        public static final String _DELETE0 =
                "DROP TABLE IF EXISTS " + _TABLENAME0;
    }

}
