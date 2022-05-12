package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, role TEXT)");
           MyDB.execSQL("create Table News(name TEXT primary key, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists News");
    }

    public Boolean insertDB(String username, String password, String role){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("role", role);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    public Boolean CheckAll(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public String RoleDB(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select role from users where username = ? and password = ?", new String[] {username,password});
        if (cursor.moveToFirst())
            return cursor.getString(0);
        return null;
    }
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from users",null);
    }
    public Boolean insertNews(String name, String description){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("description",description);
        long result = DB.insert("News",null,contentValues);
        return  result != -1;
    }
    public Boolean updateNews(String name, String description){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("description",description);
        long result = DB.update("News",contentValues,"name = ?", new String[] {name});
        return  result != -1;
    }

    public Boolean deleteNews(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        long result = DB.delete("News","name = ?",new String[]{name});
        return  result != -1;
    }

    public Cursor getDataNews(){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from News",null);
    }
}
