package com.kadiryaka.myenglishwords.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.widget.Toast;

import com.kadiryaka.myenglishwords.R;
import com.kadiryaka.myenglishwords.entity.Grup;
import com.kadiryaka.myenglishwords.entity.Kelime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kadiryaka on 10.01.15.
 */
public class VeritabaniIslemleri {

    public Boolean grupKayitEkle (String grupAdi, Veritabani veritabani, Context context) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (eklenenGrupVarMi(veritabani, grupAdi)) {
            Toast toast = Toast.makeText(context, R.string.grupvar,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return false;
        } else {
            contentValues.put("grup_adi", grupAdi);
            try {
                db.insertOrThrow("gruplar",null,contentValues);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

    }

    public Boolean eklenenGrupVarMi (Veritabani veritabani, String grup) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM gruplar where grup_adi = ?",new String[] {grup});
        int satirSayisi = cursor.getCount();
        if (satirSayisi == 0)
            return false;
        else
            return true;
    }

    public List<Grup> gruplariGetir(Veritabani veritabani) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM gruplar", null);
        List<Grup> grupList = new ArrayList<Grup>();
        if (cursor.moveToFirst()) {
            do {
                Grup grup = new Grup();
                grup.setId(Integer.parseInt(cursor.getString(0)));
                grup.setName(cursor.getString(1));
                Cursor cursor2 = db.rawQuery("SELECT * FROM kelimeler where grup_id = ?", new String[] {grup.getName()});
                grup.setKelimeSayisi(cursor2.getCount());
                grupList.add(grup);
            } while (cursor.moveToNext());
        }
        return grupList;
    }

    public Boolean kelimeEkle (String turkce, String ingilizce, String grupAdi, Veritabani veritabani, Context context) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("grup_id", grupAdi);
        contentValues.put("turkce", turkce);
        contentValues.put("ingilizce", ingilizce);

        try {
            db.insertOrThrow("kelimeler",null,contentValues);
            Toast toast = Toast.makeText(context, R.string.kayitbasarili,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return true;
        } catch (Exception ex) {
            Toast toast = Toast.makeText(context, R.string.kayithatali,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return false;
        }
    }

    public List<Kelime> kelimeGetirTumunu (Veritabani veritabani) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM kelimeler", null);
        List<Kelime> kelimeList = new ArrayList<Kelime>();
        if (cursor.moveToFirst()) {
            do {
                Kelime kelime = new Kelime();
                kelime.setId(Integer.parseInt(cursor.getString(0)));
                kelime.setTurkce(cursor.getString(1));
                kelime.setIngilizce(cursor.getString(2));
                kelime.setGrupAdi(cursor.getString(3));
                kelimeList.add(kelime);
            } while (cursor.moveToNext());
        }
        return kelimeList;
    }

    public List<Kelime> kelimeGetirGrubaGore (Veritabani veritabani, String grup) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM kelimeler where grup_id = ?",new String[] {grup});
        List<Kelime> kelimeList = new ArrayList<Kelime>();
        if (cursor.moveToFirst()) {
            do {
                Kelime kelime = new Kelime();
                kelime.setId(Integer.parseInt(cursor.getString(0)));
                kelime.setTurkce(cursor.getString(1));
                kelime.setIngilizce(cursor.getString(2));
                kelime.setGrupAdi(cursor.getString(3));
                kelimeList.add(kelime);
            } while (cursor.moveToNext());
        }
        return kelimeList;
    }

    public void tabloyuKompleSil (Context context) {
        context.deleteDatabase("KelimeVeritabani");
    }

    public void gruplariVeElemanlariniSil (Veritabani veritabani, String grup) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        db.execSQL("DELETE FROM gruplar where grup_adi = ?",new String[] {grup});
        db.execSQL("DELETE FROM kelimeler where grup_id = ?",new String[] {grup});
    }

    public Boolean kelimeSilIdGore (Veritabani veritabani, Integer id) {
        try {
            SQLiteDatabase db = veritabani.getWritableDatabase();
            db.execSQL("DELETE FROM kelimeler where kelime_id = ?",new Integer[] {id});
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public Boolean veritabaniBosMu (Veritabani veritabani) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM kelimeler",null);
        int satirSayisi = cursor.getCount();
        if (satirSayisi == 0)
            return true;
        else
            return false;
    }

    public Boolean grupBosMu (Veritabani veritabani) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM gruplar",null);
        int satirSayisi = cursor.getCount();
        if (satirSayisi == 0)
            return true;
        else
            return false;
    }

    public Boolean gruptaElemanVarMi (Veritabani veritabani, String grup) {
        SQLiteDatabase db = veritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM kelimeler where grup_id = ?",new String[] {grup});
        int satirSayisi = cursor.getCount();
        if (satirSayisi == 0)
            return true;
        else
            return false;
    }
}
