package com.example.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Lodin.db";

    public DBHelper(Context context){
        super(context,"Login.db",null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table users(username TEXT,password TEXT,email TEXT,address Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username,String password,String email,String address){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("email",email);
        contentValues.put("address",address);
        long result = MyDB.insert("users",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean verify(String username,String password){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("select * from users where username=? and password=?",new String[] {username,password});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Cursor show(String username){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from users where username=?",new  String[] {username});
        return cursor;
    }
    public Cursor showAll(){
        SQLiteDatabase MyDB=this.getReadableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from users",null);
        return cursor;
    }
}
