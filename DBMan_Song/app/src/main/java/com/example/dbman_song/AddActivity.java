package com.example.dbman_song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    EditText editTextid;
    EditText editTextbaihat;
    EditText editTextcasi;
    EditText editTexttime;
    Button buttonAdd;
    Button buttonBack;
    DBSong dbSong = new DBSong(this);
    ArrayList<Song> songs = new ArrayList<>();
    int id1;
    int add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("DBMan");
        editTextid = findViewById(R.id.editText2);
        editTextbaihat = findViewById(R.id.editText3);
        editTextcasi = findViewById(R.id.editText4);
        editTexttime = findViewById(R.id.editText5);
        buttonAdd = findViewById(R.id.button);
        buttonBack = findViewById(R.id.button2);
        songs = dbSong.getAllSong();

        Intent intent = getIntent();
        try {
            id1 = Integer.parseInt(intent.getStringExtra("id"));
            editTextid.setText(id1+"");
            editTextbaihat.setText(intent.getStringExtra("baihat"));
            editTextcasi.setText(intent.getStringExtra("casi"));
            editTexttime.setText(intent.getStringExtra("time"));
            buttonAdd.setText("Sửa");
            add = 2;
        } catch (Exception e) {
            buttonAdd.setText("Thêm");
            add = 1;
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextid.getText().toString().trim() != "" && editTextbaihat.getText().toString().trim() != "" && editTexttime.getText().toString().trim() != "" && editTextcasi.getText().toString().trim() != "") {
                    int id = Integer.parseInt(editTextid.getText().toString().trim());
                    String baihat = editTextbaihat.getText().toString().trim();
                    String casi = editTextcasi.getText().toString().trim();
                    String time = editTexttime.getText().toString().trim();

                    boolean ktra = true;

                    for (int i = 0; i < songs.size(); i++) {
                        if (songs.get(i).getID() == id) {
                            ktra = false;
                            break;
                        }
                    }
                    if (ktra == true) {
                        if (add == 1) {
                            dbSong.addSong(new Song(baihat, casi, time, id));
                            setResult(RESULT_OK);
                            finish();
                        }
                        if (add == 2) {
                            dbSong.updateSong(new Song(baihat, casi, time, id), id1);
                            setResult(RESULT_OK);
                            finish();
                        }
                    } else {
                        Toast.makeText(AddActivity.this, "Mã bài hát đã có", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddActivity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }

            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
