package com.dev.wellthytask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dev.wellthytask.models.DataModel;

import java.util.ArrayList;

/**
 * Created by dev on 4/3/16.
 */
public class WellthyTestDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WELLTHY_DB";
    private static final String TABLE_NAME = "wordMeaningData";

    private static final String IMAGE_ID = "id";
    private static final String WORD = "word";
    private static final String MEANING = "meaning";
    private static final String RATIO = "ratio";

    public WellthyTestDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + IMAGE_ID + " TEXT," + WORD + " TEXT,"
                + MEANING + " TEXT," + RATIO + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }


    // Insert Data
    public void insertData(DataModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMAGE_ID, model.getImage_id());
        values.put(WORD, model.getWord());
        values.put(MEANING, model.getMeaning());
        values.put(RATIO, model.getRatio());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<DataModel> getAllData(){
        ArrayList<DataModel> list = new ArrayList<>();
        list.clear();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DataModel model = new DataModel();
                model.setImage_id(cursor.getString(0));
                model.setWord(cursor.getString(1));
                model.setMeaning(cursor.getString(2));
                model.setRatio(cursor.getString(3));
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return list;
    }
}
