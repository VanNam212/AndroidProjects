package com.example.customlistview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Student> {

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.activity_main, null);
        }
        Student student = getItem(position);
        if (student != null) {
            //anh xa + gan gia tri
            TextView textView = (TextView) view.findViewById(R.id.textView);
            TextView textView2 = (TextView) view.findViewById(R.id.textView2);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            textView.setText(student.getName());
            textView2.setText(student.getBirthDay() + "");
            imageView.setImageResource(student.getPicture());
        }
        return view;
    }
}
