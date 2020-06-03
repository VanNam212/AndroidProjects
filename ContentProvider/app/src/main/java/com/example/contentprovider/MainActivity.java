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

public class MainActivity extends AppCompatActivity {

    Button btShowAllContacts,btCallLog,btMediaStore,btBookmarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Content Provider");

        btShowAllContacts=(Button) findViewById(R.id.btShowAllContact);
        btCallLog=(Button) findViewById(R.id.btCallLog);
        btMediaStore=(Button) findViewById(R.id.btMediaStore);
        btBookmarks=(Button) findViewById(R.id.btBookmarks);

        btShowAllContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Contact.class);
                startActivity(intent);
            }
        });

        btCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] projection=new String[]{
                        CallLog.Calls.DATE,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.DURATION
                };
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG)== PackageManager.PERMISSION_GRANTED){
                    //Permission is granted
                    Cursor c=getContentResolver().query(
                            CallLog.Calls.CONTENT_URI,
                            projection,
                            CallLog.Calls.DURATION+"<?",new String[]{"30"},
                            CallLog.Calls.DATE+" Asc"
                    );
                    c.moveToFirst();
                    String s="";
                    while (c.isAfterLast()==false){
                        for (int i=0;i<c.getColumnCount();i++){
                            s+=c.getString(i)+" - ";
                        }
                        s+="\n";
                        c.moveToNext();
                    }
                    c.close();
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btMediaStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[]projection={
                        MediaStore.MediaColumns.DISPLAY_NAME,
                        MediaStore.MediaColumns.DATE_ADDED,
                        MediaStore.MediaColumns.MIME_TYPE
                };
                CursorLoader loader=new CursorLoader(MainActivity.this,
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        null,null,null
                );
                Cursor c=loader.loadInBackground();
                c.moveToFirst();
                String s="";
                while (!c.isAfterLast()){
                    for (int i=0;i<c.getColumnCount();i++){
                        s+=c.getString(i)+" - ";
                    }
                    s+="\n";
                    c.moveToNext();
                }
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                c.close();
            }
        });

        btBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://browser/bookmarks");
                String[]projection=new String[]{
                        BookmarkColumns.TITLE,
                        BookmarkColumns.URL,
                };
                Cursor c=getContentResolver().query(uri,projection,null,null,null);
                c.moveToFirst();
                String s="";
                int titleIndex=c.getColumnIndex(BookmarkColumns.TITLE);
                int urlIndex=c.getColumnIndex(BookmarkColumns.URL);
                while (!c.isAfterLast()){
                    s+=c.getString(titleIndex)+" - "+c.getString(urlIndex);
                    c.moveToNext();
                }
                c.close();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
