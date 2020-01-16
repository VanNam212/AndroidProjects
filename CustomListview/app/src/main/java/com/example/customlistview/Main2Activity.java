package com.example.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ListAdapter listAdapter;
        listView = (ListView) findViewById(R.id.listView);
        ArrayList<Student> studentArrayList = new ArrayList<>();
        studentArrayList.add(new Student("Nguyen Van Nam", 1999, R.drawable.picture1));
        studentArrayList.add(new Student("Nguyen Van Son", 1998, R.drawable.picture2));
        listAdapter = new ListAdapter(Main2Activity.this, R.layout.activity_main2, studentArrayList);
        listView.setAdapter(listAdapter);
    }
}
