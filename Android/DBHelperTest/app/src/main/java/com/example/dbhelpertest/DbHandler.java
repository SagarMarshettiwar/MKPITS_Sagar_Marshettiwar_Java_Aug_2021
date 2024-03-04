package com.example.dbhelpertest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final  String DATABASE_NAME="Client_DB";
    public DbHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String query ="CREATE TABLE Client(id INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Phone TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE if EXISTS Client");
     onCreate(db);
    }
    void  addClient(model md)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",md.getName());
        values.put("Phone",md.getPhone());
        db.insert("Client",null,values);
        db.close();
    }
    public List<model> getAllClient(){

        List<model> clientlist=new ArrayList<model>();
        String selectQuery="SELECT * FROM Client";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                model md=new model();
                md.setId(Integer.parseInt(cursor.getString(0)));
                md.setName(cursor.getString(1));
                md.setPhone(cursor.getString(2));
                clientlist.add(md);
            }while (cursor.moveToNext());
        }
        return clientlist;
    }

    @SuppressLint("Range")
    public List<model> editClientshow(int id)
    {
        List<model> clientlist = new ArrayList<>();
        String query = "SELECT * FROM Client WHERE id =" + id ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                model client = new model();
                client.setName(cursor.getString(cursor.getColumnIndex("Name")));
                client.setPhone(cursor.getString(cursor.getColumnIndex("Phone")));
                client.setId(cursor.getInt(cursor.getColumnIndex("id")));
                clientlist.add(client);
            } while (cursor.moveToNext());
            cursor.close(); // Close the cursor when done
        }
        return clientlist;
    }


    public int updateClient(model md, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", md.getName());
        values.put("Phone", md.getPhone());
        return db.update("Client", values, "id=?", new String[]{String.valueOf(i)});
    }

    public void delete(int p){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Client", "id=?", new String[]{String.valueOf(p)});
        db.close();
    }
}
