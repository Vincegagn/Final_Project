package com.example.finalproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "savedImages.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_IMAGES = "images";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_HD_IMAGE_URL = "hd_image_url";
    private static final String COLUMN_EXPLANATION = "explanation";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_IMAGES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_HD_IMAGE_URL + " TEXT, " +
                COLUMN_EXPLANATION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(db);
    }

    // Method to add an image to the database
    public void addImage(SavedImage savedImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, savedImage.getDate());
        values.put(COLUMN_IMAGE_URL, savedImage.getImageUrl());
        values.put(COLUMN_HD_IMAGE_URL, savedImage.getHdImageUrl());
        values.put(COLUMN_EXPLANATION, savedImage.getExplanation());

        db.insert(TABLE_IMAGES, null, values);
        db.close();
    }

    // Method to retrieve all saved images from the database
    public List<SavedImage> getAllImages() {
        List<SavedImage> savedImages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_IMAGES, null);

        if (cursor.moveToFirst()) {
            do {
                SavedImage savedImage = new SavedImage(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HD_IMAGE_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPLANATION))
                );
                savedImages.add(savedImage);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return savedImages;
    }

    // Method to delete an image from the database by date
    public void deleteImage(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGES, COLUMN_DATE + " = ?", new String[]{date});
        db.close();
    }
}
