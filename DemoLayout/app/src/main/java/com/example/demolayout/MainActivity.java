package com.example.demolayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    String[] array = {"Asus", "Dell", "Lenovo"};
    TextView textView2;
    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2 = (TextView) findViewById(R.id.textView2);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                array);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner1.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinner1.setOnItemSelectedListener(new MyProcessEvent());

    }

    //Class tạo sự kiện
    private class MyProcessEvent implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            textView2.setText(array[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            textView2.setText("");
        }
    }
}
