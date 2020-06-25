package com.example.example4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public DatabaseHelper(Context context)
    {
        super(context, "Details.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE form(name VARCHAR, address VARCHAR, email VARCHAR primary key, pnumber VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists form");
    }

    //Delete
    public boolean deleteEmployee(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        long insss =  db.delete("form", "email" + " = ?", new String[] {email});
        if(insss==-1) return false;
        else return true;
        //db.close();
    }

    public boolean updateEmployee(String name,String address,String email,String pNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("pNumber", pNumber);

        long inss =  db.update("form", contentValues, "email" + " = ?", new String[] {email});
        if(inss==-1) return false;
        else return true;
    }

    //Inserting values in Database
    public boolean insert(String name, String  address, String email, String pnumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("email", email);
        contentValues.put("pnumber", pnumber);

        long ins = db.insert("form", null, contentValues);
        if(ins==-1) return false;
        else return true;
    }

    // code to get all contacts in a list view
    public List<Employee> getAllEmployees()
    {
        List<Employee> EmployeeList = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM form";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Employee contact = new Employee();
                contact.setName(cursor.getString(0));
                contact.setAddress(cursor.getString(1));
                contact.setEmail(cursor.getString(2));
                contact.setPnumber(cursor.getString(3));
                EmployeeList.add(contact);
            } while (cursor.moveToNext());
        }
        return EmployeeList;
    }

    //Checking if email exists
    public Boolean chkemail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from form where email=?", new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }

}
