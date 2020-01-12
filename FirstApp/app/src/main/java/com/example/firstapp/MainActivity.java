package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    EditText t1, t2;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.text1);
        t2 = findViewById(R.id.text2);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int so1 = Integer.parseInt(t1.getText().toString());
                    int so2 = Integer.parseInt(t2.getText().toString());
                    int tong = so1 + so2;
                    textView.setText(tong + "");
                } catch (Exception e) {
                    textView.setText("Nhap sai kieu du lieu");
                }
            }
        });

    }
}
