package com.example.thisinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBThiSinh extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "DBThiSinh";

    // Tên bảng: Word.
    private static final String TABLE_THISINH = "ThiSinh";

    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_TOAN = "Toan";
    private static final String COLUMN_LY = "Ly";
    private static final String COLUMN_HOA = "Hoa";
    private static final String COLUMN_ID = "ID";

    public DBThiSinh(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_THISINH + "("
                + COLUMN_ID + " TEXT Primary key, " + COLUMN_NAME + " TEXT, "
                + COLUMN_TOAN + " DOUBLE, " + COLUMN_LY + " DOUBLE, " + COLUMN_HOA + " DOUBLE " + ")";
        db.execSQL(script);
//        createDefaultNotesIfNeed();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THISINH);

        // Và tạo lại.
        onCreate(db);
    }
    // Nếu trong bảng Note chưa có dữ liệu,
    // Trèn vào mặc định 2 bản ghi.
    public void createDefaultNotesIfNeed() {
        int count = this.getThiSinhsCount();
        if (count == 0) {
            ThiSinh thiSinh1 = new ThiSinh("GHA01111","Vũ Trường An",8.6,7.6,9);
            ThiSinh thiSinh2 = new ThiSinh("GHA01112","Lê Hải Hà",6.4,8.5,7);
            ThiSinh thiSinh3 = new ThiSinh("GHA01113","Lê Định Đức",7,6,8.5);
            ThiSinh thiSinh4 = new ThiSinh("GHA01114","Mai Văn Đức",9.8,7.5,8);
            ThiSinh thiSinh5 = new ThiSinh("GHA01115","Hà Thị Thu",8.8,9,5);
            ThiSinh thiSinh6 = new ThiSinh("GHA01116","Nguyễn Hoàng Anh",7.8,8.5,6);

            this.addThiSinh(thiSinh1);
            this.addThiSinh(thiSinh2);
            this.addThiSinh(thiSinh3);
            this.addThiSinh(thiSinh4);
            this.addThiSinh(thiSinh5);
            this.addThiSinh(thiSinh6);


        }
    }
    public void addThiSinh(ThiSinh word) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, word.getSoBaoDanh());
        values.put(COLUMN_NAME, word.getHoTen());
        values.put(COLUMN_TOAN, word.getToan());
        values.put(COLUMN_LY, word.getLy());
        values.put(COLUMN_HOA, word.getHoa());
        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_THISINH, null, values);
        // Đóng kết nối database.
        db.close();
    }
    private int getThiSinhsCount() {
        String countQuery = "SELECT * FROM " + TABLE_THISINH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public ArrayList<ThiSinh> getAllThiSinh() {
        ArrayList<ThiSinh> wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_THISINH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ThiSinh word = new ThiSinh();
                word.setSoBaoDanh(cursor.getString(0));
                word.setHoTen(cursor.getString(1));
                word.setToan(cursor.getDouble(2));
                word.setLy(cursor.getDouble(3));
                word.setHoa(cursor.getDouble(4));

                wordList.add(word);
            } while (cursor.moveToNext());
        }

        // return note list
        return wordList;
    }
    public void deleteSong(ThiSinh word) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_THISINH, COLUMN_ID + " = ?",
                new String[]{String.valueOf(word.getSoBaoDanh())});
        db.close();
    }
    public int updateSong(ThiSinh song, String id) {
//        Log.i(TAG, "DBWord.updateWord ... " + word.getOriginal_Text());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, song.getSoBaoDanh());
        values.put(COLUMN_NAME, song.getHoTen());
        values.put(COLUMN_TOAN, song.getToan());
        values.put(COLUMN_LY, song.getLy());
        values.put(COLUMN_HOA, song.getHoa());

        // updating row
        return db.update(TABLE_THISINH, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
