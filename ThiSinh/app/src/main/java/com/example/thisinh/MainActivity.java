package com.example.thisinh;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity<FloatingActonButton> extends AppCompatActivity {
    EditText editText;
    ListView listView;
    FloatingActionButton floatingActionButton;

    DBThiSinh dbThiSinh = new DBThiSinh(this);
    ArrayList<ThiSinh> thiSinhs;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listview);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        dbThiSinh.createDefaultNotesIfNeed();

        thiSinhs = dbThiSinh.getAllThiSinh();
        Collections.sort(thiSinhs);
        customAdapter = new CustomAdapter(this, thiSinhs);

        listView.setAdapter(customAdapter);

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
        //Đăng ký menu cho listview
        registerForContextMenu(listView);
        //Thêm
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("id","");
                startActivityForResult(intent, 1);
            }
        });
    }

    //Thêm và sửa
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1 || requestCode == 2) && resultCode == RESULT_OK) {
            thiSinhs = dbThiSinh.getAllThiSinh();
            customAdapter = new CustomAdapter(this, thiSinhs);

            listView.setAdapter(customAdapter);
        }
    }

    //menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle("Action");
        menu.add(0, 1, 0, "Sửa");
        menu.add(0, 2, 1, "Xóa");

    }

    //chọn item trong menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        final Song song = (Song) this.listView.getItemAtPosition(info.position);
        final ThiSinh thiSinh = (ThiSinh) this.listView.getAdapter().getItem(info.position);
        //sửa, chuyển dữ liệu thí sinh được chọn sang form sửa
        if (item.getItemId() == 1) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.putExtra("id", thiSinh.getSoBaoDanh());
            intent.putExtra("name", thiSinh.getHoTen());
            intent.putExtra("toan", thiSinh.getToan() + "");
            intent.putExtra("ly", thiSinh.getLy() + "");
            intent.putExtra("hoa", thiSinh.getHoa() + "");
            startActivityForResult(intent, 2);
        } else if (item.getItemId() == 2) {
            //xóa, chuyển thí sinh được chọn sang form xóa
            Delete(thiSinh);
        }
        return true;
    }

    //xóa thí sinh
    public void Delete(final ThiSinh song) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận");
        //tính tổng điểm của thí sinh được chọn
        double tong_diem = song.tong_diem();
        //lấy ra danh sách thí sinh trong database
        thiSinhs = dbThiSinh.getAllThiSinh();
        //danh sách chứa những thí sinh có tổng điểm nhỏ hơn tổng điểm được chọn
        final ArrayList<ThiSinh> thiSinhs1 = new ArrayList<>();
        //tìm những thí sinh có tổng điểm nhỏ hơn tổng điểm được chọn
        for (int i = 0; i < thiSinhs.size(); i++) {
            if (thiSinhs.get(i).tong_diem() < tong_diem) {
                //thêm vào danh sách
                thiSinhs1.add(thiSinhs.get(i));
            }
        }
        builder.setMessage("Bạn muốn xóa " + thiSinhs1.size() + "TS dưới " + tong_diem + " điểm?");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //xóa những thí sinh có tổng điểm nhỏ hơn tổng điểm được chọn
                for (ThiSinh thiSinh : thiSinhs1) {
                    dbThiSinh.deleteSong(thiSinh);
                }
                //cập nhật lại listview
                thiSinhs = dbThiSinh.getAllThiSinh();
                customAdapter = new CustomAdapter(MainActivity.this, thiSinhs);

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
