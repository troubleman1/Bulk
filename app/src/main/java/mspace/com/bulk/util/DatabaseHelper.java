package mspace.com.bulk.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import mspace.com.bulk.model.User;

/**
 * Created by root on 4/7/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SMS";

    private static final String TABLE_USERS = "USERS";
    private static final String ID = "ID";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String PASSWORD = "PASSWORD";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS USERS(" +
                "ID INTEGER AUTO INCREMENT PRIMARY KEY," +
                "FIRSTNAME TEXT," +
                "LASTNAME TEXT," +
                "PASSWORD TEXT" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        onCreate(db);
    }

    public void addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRSTNAME, user.getFirstName());
        values.put(LASTNAME, user.getLastName());
        values.put(PASSWORD, user.getPassword());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{ID, FIRSTNAME, LASTNAME, PASSWORD}
                , ID + "= ?", new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null) {
            cursor.moveToFirst();
            User u = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            return u;
        }
        return null;
    }

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();
        String sql = "select * from " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);


        if (cursor.moveToFirst()) {
            do {
                User u = new User();
                u.setUserId(Integer.parseInt(cursor.getString(0)));
                u.setFirstName(cursor.getString(1));
                u.setLastName(cursor.getString(2));
                u.setPassword(cursor.getString(3));
                userList.add(u);
            } while (cursor.moveToNext());
        }


        return userList;
    }

    public int getUserCount() {
        String sql = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRSTNAME, user.getFirstName());
        values.put(LASTNAME, user.getLastName());
        values.put(PASSWORD, user.getPassword());
        return db.update(TABLE_USERS, values, ID + "= ?", new String[]{String.valueOf(user.getUserId())});

    }

    public boolean userLogin(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{ID, FIRSTNAME, LASTNAME, PASSWORD}
                , ID + "= ?", new String[]{String.valueOf(user.getUserId())}, null, null, null, null
        );
        if (cursor != null) {
            return true;
        }
        return false;
    }

    public void deleteUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, ID + "= ? ", new String[]{String.valueOf(user.getUserId())});
    }
}
