package com.evdokimoveu.openhouse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBRealtors extends SQLiteOpenHelper {

    public final static String DB_NAME_FIELD = "name";
    public final static String DB_PHONE_FIELD = "phone";
    public final static String DB_EMAIL_FIELD = "email";
    public final static String DB_SWITCH = "issendmail";
    public final static String DB_DATE = "dateadd";
    public final static String DB_ID = "id";

    public final static String DATA_BASE_NAME = "realtors.db";
    public final static String TABLE_REALTOR = "realtor";

    public DBRealtors(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_REALTOR +
                " (" +
                DB_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DB_NAME_FIELD + " VARCHAR NOT NULL, " +
                DB_PHONE_FIELD + " VARCHAR NOT NULL, " +
                DB_EMAIL_FIELD + " VARCHAR NOT NULL, " +
                DB_SWITCH + " VARCHAR NOT NULL, " +
                DB_DATE + " VARCHAR NOT NULL); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REALTOR);
        onCreate(db);
    }
}