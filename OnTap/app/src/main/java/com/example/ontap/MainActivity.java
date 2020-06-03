package com.example.ontap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    Button btnAdd;
    Button btnRemove;
    ArrayList<Contact> contacts;
    ContactAdapter contactAdapter;
    DBContact dbContact = new DBContact(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Thong tin danh ba");
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnRemove = (Button) findViewById(R.id.buttonRemove);

        dbContact.createDefaultNotesIfNeed();
        contacts = dbContact.getAllSongs();
        contactAdapter = new ContactAdapter(this, contacts);
        listView.setAdapter(contactAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts = dbContact.getAllSongs();
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).isCheck()) {
                        dbContact.deleteWord(contacts.get(i));
                    }
                }
            }
        });
    }
}
