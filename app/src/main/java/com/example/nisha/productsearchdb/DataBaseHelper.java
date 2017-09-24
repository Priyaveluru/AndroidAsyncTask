package com.example.nisha.productsearchdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.channels.DatagramChannel.open;

/**
 * Created by nisha on 3/31/2017.
 */

public class DataBaseHelper  {

    Context context;
    public static SQLiteDatabase db;
    public static DataController dbHelper;
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "DB_NAME.db";
    protected static final String TABLE_NAME = "PRODUCT_TABLE";
    protected static final String COLUMN1 = "ITEM_NAME";
    protected static final String COLUMN2 = "ITEM_DESCRIPTION";
    protected static final String COLUMN3 = "ITEM_PRICE";
    protected static final String COLUMN4 = "ITEM_REVIEW";
    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME
            +" ("+COLUMN1 + " TEXT NOT NULL, "+ COLUMN2+" TEXT, "+COLUMN3+" TEXT, "+ COLUMN4+" TEXT);";


    public DataBaseHelper(Context context) {

        this.context=context;
        dbHelper = new DataController(context);
    }

    public DataBaseHelper open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public static SQLiteDatabase read() {
        db = dbHelper.getReadableDatabase();
        return db;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor retrieve(String[] reqCols, String sort) {
        read();
        return db.query(TABLE_NAME, reqCols, null, null, null, null, sort);
    }

    public String search(String name){
        String rtn = "";
       String desc="";
        String itemName="";
        String itemPrice="";
        String itemReview="";
        String selectQuery = "Select ITEM_NAME,ITEM_DESCRIPTION,ITEM_PRICE,ITEM_REVIEW from " + TABLE_NAME + " WHERE "+COLUMN1+" = '"+ name + "'";

        read();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor!=null) {
            if (cursor.moveToFirst()) {
                do {

                    itemName=cursor.getString(cursor.getColumnIndex(COLUMN1));
                    desc=cursor.getString(cursor.getColumnIndex(COLUMN2));
                    itemPrice=cursor.getString(cursor.getColumnIndex(COLUMN3));
                    itemReview=cursor.getString(cursor.getColumnIndex(COLUMN4));
                    rtn=itemName+","+desc+","+itemPrice+","+itemReview;
                    Toast emptyToast = Toast.makeText(context, desc, Toast.LENGTH_LONG);
                    emptyToast.show();
                    } while (cursor.moveToNext());
            }
        }

        cursor.close();
        return rtn;
    }

    public long insert(String itemNme, String descp, String priceI, String reviewItm) throws IOException {
        open();
        ContentValues content = new ContentValues();
        content.put(COLUMN1, itemNme);
        content.put(COLUMN2, descp);
        content.put(COLUMN3, priceI);
        content.put(COLUMN4, reviewItm);
        return db.insert(TABLE_NAME, null, content);

    }

    private static class DataController extends SQLiteOpenHelper {

        public DataController(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            }
            catch(SQLiteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
            onCreate(db);
        }
    }

}
