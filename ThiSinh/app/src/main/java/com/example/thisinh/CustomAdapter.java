package com.example.thisinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<ThiSinh> personArrayList;
    private ArrayList<ThiSinh> filterList;
    private CustomFilter filter;

    public CustomAdapter(Context context, ArrayList<ThiSinh> personArrayList) {
        this.context = context;
        this.personArrayList = personArrayList;
        this.filterList = personArrayList;
    }

    @Override
    public int getCount() {
        return personArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return personArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.activity_main2, null);
        }
        ThiSinh person = (ThiSinh) getItem(position);
        if (person != null) {
            TextView textView = (TextView) view.findViewById(R.id.textView2);
            TextView textView2 = (TextView) view.findViewById(R.id.textView4);
            TextView textView5 = (TextView) view.findViewById(R.id.textView5);
            textView.setText(person.getSoBaoDanh());
            textView2.setText(person.getHoTen());
            double total = person.tong_diem();
            textView5.setText(total + "");
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<ThiSinh> filters = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getHoTen().toUpperCase().contains(constraint)) {
                        ThiSinh person = new ThiSinh(filterList.get(i).getSoBaoDanh(), filterList.get(i).getHoTen(), filterList.get(i).getToan(), filterList.get(i).getLy(), filterList.get(i).getHoa());
                        filters.add(person);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            personArrayList = (ArrayList<ThiSinh>) results.values;
            notifyDataSetChanged();
        }
    }
}
