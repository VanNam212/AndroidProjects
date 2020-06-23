package com.example.dbman_song;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBSong extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;

    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "DBSong";

    // Tên bảng: Word.
    private static final String TABLE_Song = "Song";

    private static final String COLUMN_NAME_SONG = "Name_Song";
    private static final String COLUMN_NAME_SINGER = "Name_Singer";
    private static final String COLUMN_TIME = "Time";
    private static final String COLUMN_ID = "ID";

    public DBSong(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_Song + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME_SONG + " TEXT, "
                + COLUMN_NAME_SINGER + " TEXT, " + COLUMN_TIME + " TEXT " + ")";
        db.execSQL(script);
//        createDefaultNotesIfNeed();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Song);

        // Và tạo lại.
        onCreate(db);
    }

    // Nếu trong bảng Note chưa có dữ liệu,
    // Trèn vào mặc định 2 bản ghi.
    public void createDefaultNotesIfNeed() {
        int count = this.getSongsCount();
        if (count == 0) {
            Song baihat1 = new Song("Phút cuối", "Bằng Kiều", "3:27", 1);
            Song baihat2 = new Song("Bông Hồng Thủy Tinh", "Bức Tường", "4:18", 2);
            Song baihat3 = new Song("Hà Nội mùa thu", "Mỹ Linh", "4:11", 3);
            Song baihat4 = new Song("Bà tôi", "Tùng Dương", "3:51", 4);
            Song baihat5 = new Song("Gót hồng", "Quang Dũng", "4:01", 5);
            Song baihat6 = new Song("Đêm đông", "Bằng Kiều", "4:12", 6);

            this.addSong(baihat1);
            this.addSong(baihat2);
            this.addSong(baihat3);
            this.addSong(baihat4);
            this.addSong(baihat5);
            this.addSong(baihat6);
        }
    }
    public void addSong(Song word) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, word.getID());
        values.put(COLUMN_NAME_SONG, word.getTen_BaiHat());
        values.put(COLUMN_NAME_SINGER, word.getTen_CaSi());
        values.put(COLUMN_TIME, word.getThoiLuong());
        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_Song, null, values);
        // Đóng kết nối database.
        db.close();
    }
    public int getSongsCount() {

        String countQuery = "SELECT * FROM " + TABLE_Song;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public ArrayList<Song> getAllSong() {

        ArrayList<Song> wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_Song;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Song word = new Song();
                word.setID(cursor.getInt(0));
                word.setTen_BaiHat(cursor.getString(1));
                word.setTen_CaSi(cursor.getString(2));
                word.setThoiLuong(cursor.getString(3));

                wordList.add(word);
            } while (cursor.moveToNext());
        }

        // return note list
        return wordList;
    }
    public void deleteSong(Song word) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Song, COLUMN_ID + " = ?",
                new String[]{String.valueOf(word.getID())});
        db.close();
    }
    public int updateSong(Song song, int id) {
//        Log.i(TAG, "DBWord.updateWord ... " + word.getOriginal_Text());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, song.getID());
        values.put(COLUMN_NAME_SONG, song.getTen_BaiHat());
        values.put(COLUMN_NAME_SINGER, song.getTen_CaSi());
        values.put(COLUMN_TIME, song.getThoiLuong());

        // updating row
        return db.update(TABLE_Song, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}