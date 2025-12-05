package com.example.g18_project_comp3074;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DBName = "G18_Project_COMP3074.db";
    public static final int version = 1;

    public static final String TABLE_NAME = "Restaurants";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_TAGS = "tags";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public static final SQLiteDatabase.CursorFactory factory = null;

    public DBHelper(@Nullable Context context) {
        super(context, DBName, factory, version);
        this.context = context; // assign context
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_RATING + " REAL, " +
                COLUMN_TAGS + " TEXT, " +
                COLUMN_NOTES + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRestaurant(String name, String phone, String tags, String notes,
                       String address, double latitude, double longitude) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_NOTES, notes);
        cv.put(COLUMN_TAGS, tags);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_LATITUDE, latitude);
        cv.put(COLUMN_LONGITUDE, longitude);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add restaurant", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Restaurant added successfully", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public boolean updateRestaurant(int id, String name, String phone, String notes,
                                    String tags, String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_RATING, 0.0);
        cv.put(COLUMN_TAGS, tags);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_LATITUDE, latitude);
        cv.put(COLUMN_LONGITUDE, longitude);
        cv.put(COLUMN_NOTES, notes);

        int rowsAffected = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }


    public Cursor getAllRestaurants() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public Cursor searchRestaurantsByName(String dbName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " LIKE ?";
        String[] args = new String[]{"%" + dbName + "%"};
        return db.rawQuery(query, args);
    }

    public boolean deleteRestaurant(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();

        if (result == 0) {
            Toast.makeText(context, "Failed to delete restaurant.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Restaurant deleted successfully.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
