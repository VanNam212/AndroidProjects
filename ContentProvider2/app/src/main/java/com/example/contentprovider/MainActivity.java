package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;

import com.example.contentprovider.BookMarkColumns;
import com.example.contentprovider.ContactsActivity;
import com.example.contentprovider.R;

public class MainActivity extends AppCompatActivity {
    Button buttonShowContact , buttonAccessCallLog , buttonAccessMediaStore,buttonAccessBookmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Content Provider");
        buttonShowContact = (Button) findViewById(R.id.buttonShowContact);
        buttonAccessCallLog = (Button) findViewById(R.id.buttonAccessCallLog);
        buttonAccessBookmark = (Button) findViewById(R.id.buttonAccessBookMark);
        buttonAccessMediaStore=(Button) findViewById(R.id.buttonAccessMediaStore);

        buttonShowContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ContactsActivity.class);
                startActivity(intent);
            }
        });
        buttonAccessCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] projection = new String[]{
                    CallLog.Calls.DATE,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.DURATION
                };
                if (ActivityCompat.checkSelfPermission(MainActivity.this , Manifest.permission.READ_CALL_LOG)
                        == PackageManager.PERMISSION_GRANTED){
                    Cursor c = getContentResolver().query(
                            CallLog.Calls.CONTENT_URI,
                            projection,
                            CallLog.Calls.DURATION + "<?",new String[]{"30"},
                            CallLog.Calls.DATE+" Asc"
                    );
                    c.moveToFirst();
                    String s="";
                    while (c.isAfterLast() == false){
                        for (int i = 0 ; i<c.getColumnCount();i++){
                            s+=c.getString(i) + "-";
                        }
                        s+="\n";
                        c.moveToNext();
                    }
                    c.close();
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonAccessMediaStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] projection = new String[]{
                        MediaStore.MediaColumns.DISPLAY_NAME,
                        MediaStore.MediaColumns.DATE_ADDED,
                        MediaStore.MediaColumns.MIME_TYPE
                };
                CursorLoader loader = new CursorLoader(MainActivity.this , MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null
                );
                Cursor c =loader.loadInBackground();
                String s = "";
                c.moveToFirst();
                while(c.isAfterLast()==false){
                    for (int i = 0; i < c.getColumnCount(); i++)
                        s+= c.getString(i) + " - ";
                    c.moveToFirst();
                }
                c.close();
                Toast.makeText(MainActivity.this,s, Toast.LENGTH_SHORT).show();
            }
        });

        buttonAccessBookmark.setOnClickListener(new View.OnClickListener() {
            Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
            @Override
            public void onClick(View v) {
                String [] projection = new String[]{
                        BookMarkColumns.TITLE,
                        BookMarkColumns.URL
                };
                Cursor cursor = getContentResolver().query(BOOKMARKS_URI
                        ,projection
                        ,null
                        ,null
                        ,null);
                if (cursor.getCount()==0){
                    Toast.makeText(MainActivity.this, "NULL", Toast.LENGTH_SHORT).show();
                    return;
                }
                cursor.moveToFirst();
                String s = "";
                int titleIndex = cursor.getColumnIndex(BookMarkColumns.TITLE);
                int urlIndex = cursor.getColumnIndex(BookMarkColumns.URL);
                while (!cursor.isAfterLast()){
                    s+=cursor.getString(titleIndex)+"  --  "+ cursor.getString(urlIndex);
                    cursor.moveToNext();
                }
                cursor.close();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
