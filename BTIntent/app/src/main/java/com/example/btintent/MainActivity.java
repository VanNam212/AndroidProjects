package com.example.btintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextSDT;
    Button buttonCall;

    static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Intent thuc hien cuoc goi");

        editTextSDT = (EditText) findViewById(R.id.editTextSDT);
        buttonCall = (Button) findViewById(R.id.buttonCall);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = editTextSDT.getText().toString();
                if (!sdt.equals("")) {
                    String dial = "tel:" + editTextSDT.getText().toString();
                    startActivityForResult(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)), REQUEST_CALL);
                } else {
                    Toast.makeText(MainActivity.this, "Nhap so dien thoai", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
