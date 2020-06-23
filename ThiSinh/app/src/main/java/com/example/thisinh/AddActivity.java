package com.example.thisinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    EditText editTextid;
    EditText editTexthoten;
    EditText editTexttoan;
    EditText editTextly;
    EditText editTexthoa;
    Button buttonAdd;
    Button buttonBack;
    DBThiSinh dbThiSinh = new DBThiSinh(this);
    ArrayList<ThiSinh> thiSinhs = new ArrayList<>();
    static String id1;
    static int add = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editTextid = findViewById(R.id.editText2);
        editTexthoten = findViewById(R.id.editText3);
        editTexttoan = findViewById(R.id.editText4);
        editTextly = findViewById(R.id.editText5);
        editTexthoa = findViewById(R.id.editText6);
        buttonAdd = findViewById(R.id.button);
        buttonBack = findViewById(R.id.button2);
        thiSinhs = dbThiSinh.getAllThiSinh();

        buttonAdd.setText("Thêm");
        Intent intent = getIntent();
        id1 = intent.getStringExtra("id");
        if (id1.equals("")) {
            buttonAdd.setText("Thêm");
            add = 1;
        } else {
            editTextid.setText(id1);
            editTexthoten.setText(intent.getStringExtra("name"));
            editTexttoan.setText(intent.getStringExtra("toan"));
            editTextly.setText(intent.getStringExtra("ly"));
            editTexthoa.setText(intent.getStringExtra("hoa"));
            buttonAdd.setText("Sửa");
            add = 2;
        }
//        try {
//            Intent intent = getIntent();
//            //nhận dữ liệu bên sửa và set cho các edit text
//            id1 = intent.getStringExtra("id");
//            editTextid.setText(id1);
//            editTexthoten.setText(intent.getStringExtra("name"));
//            editTexttoan.setText(intent.getStringExtra("toan"));
//            editTextly.setText(intent.getStringExtra("ly"));
//            editTexthoa.setText(intent.getStringExtra("hoa"));
//            buttonAdd.setText("Sửa");
//            add = 2;
//        } catch (Exception e) {
//            buttonAdd.setText("Thêm");
//            add = 1;
//        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra người dùng có nhập đủ dữ liệu không
                if (!editTextid.getText().toString().trim().equals("") && !editTexthoten.getText().toString().trim().equals("")
                        && !editTexttoan.getText().toString().trim().equals("") && !editTextly.getText().toString().trim().equals("")
                        && !editTexthoa.getText().toString().trim().equals("")) {
                    String id = editTextid.getText().toString().trim();
                    String hoten = editTexthoten.getText().toString().trim();

                    try {
                        double toan = Double.parseDouble(editTexttoan.getText().toString().trim());
                        double ly = Double.parseDouble(editTextly.getText().toString().trim());
                        double hoa = Double.parseDouble(editTexthoa.getText().toString().trim());
                        //kiểm tra phạm vi của điểm
                        if (toan >= 0 && toan <= 10 && ly >= 0 && ly <= 10 && hoa >= 0 && hoa <= 10) {
                            //kiểm tra số báo danh có bị trùng
                            boolean ktra = true;
                            for (int i = 0; i < thiSinhs.size(); i++) {
                                if (thiSinhs.get(i).getSoBaoDanh() == id) {
                                    ktra = false;
                                    break;
                                }
                            }
                            if (ktra == true) {
                                //nếu đúng hết
                                if (add == 1) {
                                    //thêm thí sinh vào database
                                    dbThiSinh.addThiSinh(new ThiSinh(id, hoten, toan, ly, hoa));
                                    setResult(RESULT_OK);
                                    finish();   //đóng form
                                }
                                if (add == 2) {
                                    //sửa thí sinh trong database
                                    dbThiSinh.updateSong(new ThiSinh(id, hoten, toan, ly, hoa), id1);
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            } else {
                                //hiện thông báo
                                Toast.makeText(AddActivity.this, "Mã thí sinh đã có", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddActivity.this, "Nhập sai điểm", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

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
