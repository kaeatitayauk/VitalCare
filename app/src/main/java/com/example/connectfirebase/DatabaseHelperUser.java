package com.example.connectfirebase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperUser extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseHelperUser.db";
    private  static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAMBER = "namber_table";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_Age = "Age"; // Lastmeter
    public static final String COLUMN_Gender = "Gender"; // Unit
    public static final String COLUMN_BPM = "BPM"; // Total_pay
    private static DatabaseHelperUser sqliteHelper;



    public DatabaseHelperUser(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public static synchronized DatabaseHelperUser getInstance(Context c){
        if(sqliteHelper == null){
            sqliteHelper = new DatabaseHelperUser(c.getApplicationContext());
        }
        return sqliteHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAMBER+"(ID integer PRIMARY KEY AUTOINCREMENT,Date text,Age text,Gender text,BPM text )");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAMBER);
        onCreate(db);
    }
    public boolean insertData(String Date, String Age, String Gender, String BPM){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE,Date);
        contentValues.put(COLUMN_Age,Age);
        contentValues.put(COLUMN_Gender,Gender);
        contentValues.put(COLUMN_BPM,BPM);
        long result = db.insert(TABLE_NAMBER,null,contentValues);
        if(result == -1){
            return false;
        } else{
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAMBER,null);
        return res;
    }
}



