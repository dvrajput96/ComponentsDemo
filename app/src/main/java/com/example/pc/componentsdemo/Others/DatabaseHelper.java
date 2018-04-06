package com.example.pc.componentsdemo.Others;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by pc on 12/8/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Registration.db";

    //table name
    private static final String TABLE_NAME = "student";

    //column names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_MOBILE = "user_mobile";
    private static final String COLUMN_USER_GENDER = "user_gender";
    private static final String COLUMN_USER_HOBBIES = "user_hobbies";
    private static final String COLUMN_USER_BIRTHDATE = "user_birthdate";
    private static final String COLUMN_USER_BIRTHTIME = "user_birthtime";
    private static final String COLUMN_USER_STATE = "user_state";
    private static final String COLUMN_USER_NATION = "user_nation";
    private static final String COLUMN_USER_ADDRESS = "user_address";
    private static final String COLUMN_USER_PERCENT = "user_percentage";
    private static final String COLUMN_USER_RATING = "user_rating";
    private static final String COLUMN_USER_ASWITCH = "user_switch";
    private static final String COLUMN_USER_IMAGE = "user_image";


    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_NAME + " TEXT, " +
            COLUMN_USER_PASSWORD + " TEXT, " +
            COLUMN_USER_EMAIL + " TEXT, " +
            COLUMN_USER_MOBILE + " TEXT, " +
            COLUMN_USER_GENDER + " TEXT, " +
            COLUMN_USER_HOBBIES + " TEXT, " +
            COLUMN_USER_BIRTHDATE + " TEXT, " +
            COLUMN_USER_BIRTHTIME + " TEXT, " +
            COLUMN_USER_STATE + " TEXT, " +
            COLUMN_USER_NATION + " TEXT, " +
            COLUMN_USER_ADDRESS + " TEXT, " +
            COLUMN_USER_PERCENT + " TEXT, " +
            COLUMN_USER_RATING + " TEXT, " +
            COLUMN_USER_IMAGE + " BLOB, " +
            COLUMN_USER_ASWITCH + " TEXT " + " )";

    private String DROP_USER_TABLE = " DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public boolean addUser(Users users) {

        boolean isSuccess = false;
        ContentValues values = new ContentValues();

        sqLiteDatabase = this.getWritableDatabase();
        values.put(COLUMN_USER_EMAIL, users.getEmail());
        values.put(COLUMN_USER_PASSWORD, users.getPassword());
        values.put(COLUMN_USER_MOBILE, users.getMobileno());
        values.put(COLUMN_USER_NAME, "");
        values.put(COLUMN_USER_GENDER, "");
        values.put(COLUMN_USER_HOBBIES, "");
        values.put(COLUMN_USER_BIRTHDATE, "");
        values.put(COLUMN_USER_BIRTHTIME, "");
        values.put(COLUMN_USER_STATE, "");
        values.put(COLUMN_USER_NATION, "");
        values.put(COLUMN_USER_ADDRESS, "");
        values.put(COLUMN_USER_PERCENT, "");
        values.put(COLUMN_USER_RATING, "");
        values.put(COLUMN_USER_ASWITCH, "");
        values.put(COLUMN_USER_IMAGE, "");


        int count = (int) sqLiteDatabase.insert(TABLE_NAME, null, values);
        if (count < 0) {
            isSuccess = false;
        } else {
            isSuccess = true;
        }
        sqLiteDatabase.close();

        return isSuccess;
    }

    public void updateUser(Users users, int uid) {

        ContentValues values = new ContentValues();

        sqLiteDatabase = this.getWritableDatabase();

        values.put(COLUMN_USER_NAME, users.getUsername());
        values.put(COLUMN_USER_MOBILE, users.getMobileno());
        values.put(COLUMN_USER_GENDER, users.getGender());
        values.put(COLUMN_USER_HOBBIES, users.getHobbies());
        values.put(COLUMN_USER_BIRTHDATE, users.getBirthdate());
        values.put(COLUMN_USER_BIRTHTIME, users.getBirthtime());
        values.put(COLUMN_USER_STATE, users.getState());
        values.put(COLUMN_USER_NATION, users.getNation());
        values.put(COLUMN_USER_ADDRESS, users.getAddress());
        values.put(COLUMN_USER_PERCENT, users.getSeekpercentage());
        values.put(COLUMN_USER_RATING, users.getRatingvalue());
        values.put(COLUMN_USER_ASWITCH, users.getSwitchvalue());
        values.put(COLUMN_USER_IMAGE, users.getBlob());

        sqLiteDatabase.update(TABLE_NAME, values, "user_id = ?", new String[]{String.valueOf(uid)});
        sqLiteDatabase.close();

    }


    public void updateUserPassword(Users users, String email) {

        ContentValues values = new ContentValues();

        sqLiteDatabase = this.getWritableDatabase();

        values.put(COLUMN_USER_PASSWORD, users.getPassword());

        sqLiteDatabase.update(TABLE_NAME, values, "user_email = ?", new String[]{String.valueOf(email)});
        sqLiteDatabase.close();

    }

    public Users checkEmail(String email) {

        Users users = new Users();

        try {
            sqLiteDatabase = this.getReadableDatabase();
            // boolean isUnique = false;
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_EMAIL + " = '" + email + "'";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                users.setUserid(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                /*users.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL)));*/
                cursor.close();
                return users;
            } else {
                cursor.close();
                return users;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return users;
        }
    }

    public ArrayList<Users> showUserEmail() {

        ArrayList<Users> usersArrayList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                Users users = new Users();
                users.setUserid(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID)));
                users.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL)));
                usersArrayList.add(users);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return usersArrayList;
    }

    public boolean checkEmailUnique(String email) {

        Cursor cursor = null;

        try {
            sqLiteDatabase = this.getReadableDatabase();
            // boolean isUnique = false;
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_EMAIL + " = '" + email + "'";
            cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null)
                cursor.close();
        }


    }

    public boolean checkPassword(String pass) {

        try {

            sqLiteDatabase = this.getReadableDatabase();
            // boolean isUnique = false;
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_PASSWORD + " = '" + pass + "'";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.close();
                return false;
            } else {
                cursor.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Users getUserdata(int restoredUserID) {


        sqLiteDatabase = this.getReadableDatabase();

        Users users = null;
        try {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = " + restoredUserID + " ";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    users = new Users();
                    users.setUserid(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                    users.setUsername(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NAME)));
                    users.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PASSWORD)));
                    users.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL)));
                    users.setMobileno(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_MOBILE)));
                    users.setGender(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_GENDER)));
                    users.setHobbies(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_HOBBIES)));
                    users.setBirthdate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_BIRTHDATE)));
                    users.setBirthtime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_BIRTHTIME)));
                    users.setState(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_STATE)));
                    users.setNation(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NATION)));
                    users.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ADDRESS)));
                    users.setSeekpercentage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PERCENT)));
                    users.setRatingvalue(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_RATING)));
                    users.setSwitchvalue(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ASWITCH)));
                    users.setBlob(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_IMAGE)));

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;


    }

    public boolean unMatchEmailAndPassword(String email, String pass) {
        try {

            sqLiteDatabase = this.getReadableDatabase();
            // boolean isUnique = false;
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_EMAIL + " = '" + email + "' AND " + COLUMN_USER_PASSWORD + " = '" + pass + "'";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.close();
                return false;
            } else {
                cursor.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public Users checkUser(String email, String password) {

        Users users = null;
        sqLiteDatabase = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, //Table to query
                null,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            users = new Users();
            users.setUserid(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID)));
            users.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL)));
            users.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PASSWORD)));


            cursor.close();

            return users;
        } else {
            users = new Users();

            if (checkEmailUnique(email)) {
                users.setResult(1);
                return users;
            } else if (checkPassword(password)) {
                users.setResult(2);
                return users;
            } else if (unMatchEmailAndPassword(email, password)) {
                users.setResult(3);
                return users;
            }
        }

        sqLiteDatabase.close();
        return users;
    }


}
