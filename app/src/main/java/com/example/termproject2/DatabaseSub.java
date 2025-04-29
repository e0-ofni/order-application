package com.example.termproject2;

import android.provider.BaseColumns;

public final class DatabaseSub {
    public static final class SubMenu implements BaseColumns {
        public static final String MENUIMAGE = "menuimage";
        public static final String MENUNAME = "menuname";
        public static final String PRICE = "price";
        public static final String _TABLENAME0 = "subtable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +MENUIMAGE+" text not null , "
                +MENUNAME+" text not null , "
                +PRICE+" integer not null);";
        public static final String _DELETE0 =
                "DROP TABLE IF EXISTS " + _TABLENAME0;
    }
}
