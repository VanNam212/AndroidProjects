package com.example.ontap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBContact extends SQLiteOpenHelper {
    // Phiên bản
    private static final int DATABASE_VERSION = 1;

    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "DBContact";

    // Tên bảng: Word.
    private static final String TABLE_CONTACT = "Contact";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME_CONTACT = "Name";
    private static final String COLUMN_TEL = "Phone";
    private static final String COLUMN_CHECK = "Check";

    public DBContact(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_CONTACT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME_CONTACT + " TEXT, "
                + COLUMN_TEL + " TEXT" + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);

        // Và tạo lại.
        onCreate(db);
    }

    //thêm dữ liệu mặc định cho database
    public void createDefaultNotesIfNeed() {
        int count = this.getSongsCount();
        if (count == 0) {
            Contact song = new Contact(1, "Nguyen Van A", "1234");
            Contact song2 = new Contact(2, "Bui Van B", "1234");
            Contact song3 = new Contact(3, "Nguyen Van C", "1234");

            this.addSong(song);
            this.addSong(song2);
            this.addSong(song3);

        }
    }

    public void addSong(Contact word) {
//        Log.i(TAG, "DBWord.addNote ... " + word.getOriginal_Text());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, word.getId());
        values.put(COLUMN_NAME_CONTACT, word.getName());
        values.put(COLUMN_TEL, word.getPhone());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_CONTACT, null, values);
        // Đóng kết nối database.
        db.close();
    }

    public int getSongsCount() {
//        Log.i(TAG, "DBWord.getWordsCount ... ");

        String countQuery = "SELECT * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public void deleteWord(Contact word) {
//        Log.i(TAG, "DBWord.updateWord ... " + word.getOriginal_Text());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, COLUMN_ID + " = ?",
                new String[]{String.valueOf(word.getId())});
        db.close();
    }

    public ArrayList<Contact> getAllSongs() {
//        Log.i(TAG, "DBWord.getAllWords ... ");

        ArrayList<Contact> wordList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Contact word = new Contact();
                word.setId(cursor.getInt(0));
                word.setName(cursor.getString(1));
                word.setPhone(cursor.getString(2));

                // Thêm vào danh sách.
                wordList.add(word);
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}
