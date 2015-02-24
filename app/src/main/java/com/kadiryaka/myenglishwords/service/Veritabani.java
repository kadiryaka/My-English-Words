package com.kadiryaka.myenglishwords.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kadiryaka on 10.01.15.
 */
public class Veritabani extends SQLiteOpenHelper {

    private static final String VERITABANI = "KelimeVeritabani";
    private static final int SURUM = 1;

    public Veritabani(Context cont) {
        super(cont,VERITABANI,null,SURUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE gruplar (grup_id INTEGER PRIMARY KEY AUTOINCREMENT, grup_adi TEXT)");
        db.execSQL("CREATE TABLE kelimeler (kelime_id INTEGER PRIMARY KEY AUTOINCREMENT, turkce TEXT, ingilizce TEXT, grup_id TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST gruplar");
        db.execSQL("DROP TABLE IF EXIST kelimeler");
        onCreate(db);
    }


}
