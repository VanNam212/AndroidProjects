package com.example.ontap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Contact> contacts;
    ArrayList<Contact> contactArrayList;
    private CustomFilter filter;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        contactArrayList = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contacts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.activity_item_contact, null);
        }
        final Contact person = (Contact) getItem(position);
        if (person != null) {
            TextView textViewName = (TextView) view.findViewById(R.id.textView);
            textViewName.setText(person.getName());
            TextView textViewTel = (TextView) view.findViewById(R.id.textView2);
            textViewTel.setText(person.getPhone());
            CheckBox checkBox = view.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    person.setCheck(isChecked);
                }
            });
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
                ArrayList<Contact> filters = new ArrayList<>();
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getName().toUpperCase().contains(constraint)) {
                        Contact person = new Contact(contacts.get(i).getId(), contacts.get(i).getName(), contacts.get(i).getPhone(), contacts.get(i).isCheck());
                        filters.add(person);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = contacts.size();
                results.values = contacts;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactArrayList = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }
    }
}
