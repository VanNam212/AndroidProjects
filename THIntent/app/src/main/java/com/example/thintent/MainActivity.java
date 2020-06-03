package com.example.thintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextHoTen;
    EditText editTextTuoi;
    EditText editTextToan;
    EditText editTextVan;
    TextView textViewTong;
    Spinner spinnerQueQuan;
    Button pass_data;
    ImageView imageView;

    static final int Caculate_mark_request = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Thong tin thi sinh");

        //anh xa
        editTextHoTen = (EditText) findViewById(R.id.editTextHoTen);
        editTextTuoi = (EditText) findViewById(R.id.editTextNS);
        editTextToan = (EditText) findViewById(R.id.editTextDiemToan);
        editTextVan = (EditText) findViewById(R.id.editTextDiemVan);
        textViewTong = (TextView) findViewById(R.id.textViewTong);
        spinnerQueQuan = (Spinner) findViewById(R.id.spinnerQueQuan);
        pass_data = (Button) findViewById(R.id.buttonChuyen);
        imageView = (ImageView) findViewById(R.id.imageView);

        //gan gia tri
        textViewTong.setVisibility(View.INVISIBLE);
        ArrayList<String> array_khu_vuc = new ArrayList<>();
        array_khu_vuc.add("Khu vuc 1");
        array_khu_vuc.add("Khu vuc 2");

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, array_khu_vuc);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerQueQuan.setAdapter(adapter);

        imageView.setImageResource(R.drawable.gai);

        pass_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("name", editTextHoTen.getText().toString());
                intent.putExtra("dob", editTextTuoi.getText().toString());
                intent.putExtra("toan", editTextToan.getText().toString());
                intent.putExtra("van", editTextVan.getText().toString());
                intent.putExtra("home_town", spinnerQueQuan.getSelectedItem().toString());
                intent.putExtra("image", R.drawable.gai);
                startActivity(intent);
                startActivityForResult(intent, Caculate_mark_request);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Caculate_mark_request && resultCode == RESULT_OK) {
            textViewTong.setVisibility(View.VISIBLE);
            textViewTong.setText(data.getStringExtra("mark"));
            pass_data.setVisibility(View.INVISIBLE);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
