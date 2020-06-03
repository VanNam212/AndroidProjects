package com.example.thintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewHoTen;
    TextView textViewNS;
    TextView textViewTong;
    TextView textViewKhuVuc;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("Xem tong diem");

        //anh xa
        imageView = (ImageView) findViewById(R.id.imageView);
        textViewHoTen = (TextView) findViewById(R.id.textViewHoTen);
        textViewNS = (TextView) findViewById(R.id.textViewNS);
        textViewTong = (TextView) findViewById(R.id.textViewTong);
        textViewKhuVuc = (TextView) findViewById(R.id.textViewKhuVuc);
        back = (Button) findViewById(R.id.buttonChuyen);

        int tong = 0;

        //gan gia tri
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageView.setImageResource(bundle.getInt("image"));
            textViewHoTen.setText(bundle.getString("name"));
            textViewNS.setText(bundle.getString("dob"));
            int toan = Integer.parseInt(bundle.getString("toan"));
            int van = Integer.parseInt(bundle.getString("van"));
            textViewKhuVuc.setText(bundle.getString("home_town"));
            if (textViewKhuVuc.getText().toString().equals("Khu vuc 1")) {
                tong = toan + van + 1;
            } else {
                tong = toan + van + 2;
            }
            textViewTong.setText(tong + "");
        }

        //
        final int finalTong = tong;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("mark", finalTong+"");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
