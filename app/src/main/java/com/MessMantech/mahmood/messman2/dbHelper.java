package com.MessMantech.mahmood.messman2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "messman";
    public static final String table_name = "messhisab";
    public static final String table_name2 = "payment";
    public static final String col1 = "ID";
    public static final String col2 = "DATE";
    public static final String col3 = "NAME";
    public static final String col4 = "MEAL_NO";
    public static final String col42 = "BAZAR_COST";
    public static final String col5 = "PAID";

    public dbHelper(Context context) {
        super( context, DATABASE_NAME, null, 4 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table " + table_name + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, NAME TEXT, MEAL_NO REAL)" );
        db.execSQL( "create table " + table_name2 + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, NAME TEXT, BAZAR_COST INTEGER,PAID INTEGER)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS '" + table_name + "'" );
        db.execSQL( "DROP TABLE IF EXISTS '" + table_name2 + "'" );
        onCreate( db );

    }

    public boolean insertdata(String date, String name, String meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        contenvalues.put( col2, date );
        contenvalues.put( col3, name );
        contenvalues.put( col4, meal );
        long res = db.insert( table_name, null, contenvalues );
        return res != -1;

    }

    public boolean insertdatapay(String date, String name, String bazar, String paid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        contenvalues.put( col2, date );
        contenvalues.put( col3, name );
        contenvalues.put( col42, bazar );
        contenvalues.put( col5, paid );
        long res = db.insertOrThrow( table_name2, null, contenvalues );
        return res != -1;

    }


    /* public Integer deletename(String name)
     {
         SQLiteDatabase db=this.getWritableDatabase();
         return db.delete( table_name,"NAME= ? ",new String[]{name} );
     }*/
    public Cursor getalldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "select * from " + table_name, null );
        return res;

    }

    public Cursor getalldatapay() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "select * from " + table_name2, null );
        return res;

    }

    public Cursor personalallmealno() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT " + col3 + ",SUM(" + col4 + ") FROM " + table_name + " group by " + col3, null );
        return res;

    }

    public float personalmealno(String a) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT SUM(" + col4 + ") FROM " + table_name + " where " + col3 + "=" + "'" + a + "'", null );
        if (res.moveToNext()) return res.getFloat( 0 );
        return 0;

    }

    public float mealno() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resm = db.rawQuery( "SELECT SUM(" + col4 + ") FROM " + table_name, null );
        if (resm.moveToNext()) return resm.getFloat( 0 );
        return 0;


    }

    public int totalbazarcost() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resm = db.rawQuery( "SELECT SUM(" + col5 + ") FROM " + table_name2, null );
        if (resm.moveToNext()) return resm.getInt( 0 );
        return 0;


    }

    public int personalbazarcost(String a) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT SUM(" + col5 + ") FROM " + table_name2 + " where " + col3 + "=" + "'" + a + "'", null );
        if (res.moveToNext()) return res.getInt( 0 );
        return 0;

    }

    public Cursor personalbazarcost() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT " + col3 + ",SUM(" + col5 + ") FROM " + table_name2 + " group by " + col3, null );
        return res;

    }

    public int totaljoma(String a) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT SUM(" + col42 + ") FROM " + table_name2 + " where " + col3 + "=" + "'" + a + "'", null );
        if (res.moveToNext()) return res.getInt( 0 );
        return 0;


    }

    public Cursor personaltotaljoma() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT " + col3 + ",SUM(" + col42 + ") FROM " + table_name2 + " group by " + col3, null );
        return res;


    }

    public int realtotaljoma() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT SUM(" + col42 + ") FROM " + table_name2, null );
        if (res.moveToNext()) return res.getInt( 0 );
        return 0;


    }

    public boolean updatedata(String id, String date, String name, String meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        contenvalues.put( col2, date );
        contenvalues.put( col3, name );
        contenvalues.put( col4, meal );
        db.update( table_name, contenvalues, "ID=?", new String[]{id} );
        return true;

    }

    public boolean updatedatapayment(String id, String date, String name, String bazar, String paid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenvalues = new ContentValues();
        contenvalues.put( col2, date );
        contenvalues.put( col3, name );
        contenvalues.put( col42, paid );
        contenvalues.put( col5, bazar );
        db.update( table_name2, contenvalues, "ID=?", new String[]{id} );
        return true;

    }

    public void deletealldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( table_name, null, null );
        db.delete( table_name2, null, null );
        db.execSQL( "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + table_name + "'" );
        db.execSQL( "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + table_name2 + "'" );
    }
}
