package com.example.dbman_song;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbman_song.CustomAdapter;
import com.example.dbman_song.DBSong;
import com.example.dbman_song.R;
import com.example.dbman_song.Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ListView listView;
    FloatingActionButton floatingActionButton;

    DBSong dbSong = new DBSong(this);
    ArrayList<Song> songs;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("DBMan");
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listview);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        dbSong.createDefaultNotesIfNeed();

        songs = dbSong.getAllSong();
        customAdapter = new CustomAdapter(this, songs);

        listView.setAdapter(customAdapter);

        //Cancel
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Comfirm");
//                builder.setMessage("Are you sure to delete?");
//                builder.setCancelable(false);
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Song song = (Song) listView.getAdapter().getItem(position);
//                        //Song song = songs[0].get(position);
//                        dbSong.deleteSong(song);
//                        songs[0] = dbSong.getAllSong();
//                        customAdapter[0] = new CustomAdapter(MainActivity.this, songs[0]);
//
//                        listView.setAdapter(customAdapter[0]);
//
//                    }
//                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//
//            }
//
//        });
        registerForContextMenu(listView);

        //Thêm
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
//                intent.putExtra("id","");
                startActivityForResult(intent, 1);
            }
        });
        //Filter
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    //Thêm và sửa
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1 || requestCode == 2) && resultCode == RESULT_OK) {
            songs = dbSong.getAllSong();
            customAdapter = new CustomAdapter(this, songs);

            listView.setAdapter(customAdapter);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle("Action");
        menu.add(0, 1, 0, "Sửa");
        menu.add(0, 2, 1, "Xóa");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        final Song song = (Song) this.listView.getItemAtPosition(info.position);
        final Song song = (Song) this.listView.getAdapter().getItem(info.position);
        if (item.getItemId() == 1) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.putExtra("id", song.getID() + "");
            intent.putExtra("baihat", song.getTen_BaiHat());
            intent.putExtra("casi", song.getTen_CaSi());
            intent.putExtra("time", song.getThoiLuong());
            startActivityForResult(intent, 2);
        } else if (item.getItemId() == 2) {
            Delete(song);
        }
        return true;
    }

    public void Delete(final Song song) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Comfirm");
        builder.setMessage("Are you sure to delete?");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Song song = (Song) listView.getAdapter().getItem(position);
                //Song song = songs[0].get(position);
                dbSong.deleteSong(song);
                songs = dbSong.getAllSong();
                customAdapter = new CustomAdapter(MainActivity.this, songs);

                listView.setAdapter(customAdapter);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
