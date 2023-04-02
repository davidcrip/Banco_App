package com.example.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class clsBank extends SQLiteOpenHelper {

    String tblclient = "CREATE TABLE client(" +
            "idclient text primary key, " +
            "fullname text, " +
            "email text, " +
            "password text)";

    String tblacount = "CREATE TABLE acount(" +
            "numacount text primary key, " +
            "idclient text, " +
            "dateacount text, " +
            "balance int)";

    String tbltransaction = "CREATE TABLE transaction1(" +
            "idtransaction integer primary key autoincrement, " +
            "numacount text, " +
            "dateacount text, " +
            "hour text, " +
            "tipetrans text, " +
            "value int)";


    public clsBank(@Nullable Context context,
                   @Nullable String name,
                   @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase dbBank) {
        dbBank.execSQL(tblclient);
        dbBank.execSQL(tblacount);
        dbBank.execSQL(tbltransaction);

    }

    @Override
    public void onUpgrade(SQLiteDatabase dbBank, int oldVersion, int newVersion) {
        dbBank.execSQL("DROP TABLE client");
        dbBank.execSQL(tblclient);
        dbBank.execSQL("DROP TABLE acount");
        dbBank.execSQL(tblacount);
        dbBank.execSQL("DROP TABLE transaction1");
        dbBank.execSQL(tbltransaction);
    }
}
