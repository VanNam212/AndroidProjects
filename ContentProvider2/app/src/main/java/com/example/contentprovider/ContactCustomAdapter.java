package com.example.contentprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import com.example.contentprovider.R;

public class ContactCustomAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<Contact> contactslist ;
    private CustomFilter filter ;
    private ArrayList<Contact> filterList;

    public ContactCustomAdapter(Context context, ArrayList<Contact> contactslist, ArrayList<Contact> filterList) {
        this.context = context;
        this.contactslist = contactslist;
        this.filterList = filterList;
    }

    @Override
    public int getCount() {
        return contactslist.size();
    }

    @Override
    public Object getItem(int position) {
        return contactslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater= LayoutInflater.from(context);
            view = inflater.inflate(R.layout.activity_single_row_contact,null);
        }
        Contact contact = (Contact) getItem(position);
        if(contact != null){
            //anh xa + Gan gia tri
            TextView txtID = (TextView) view.findViewById(R.id.textviewID);
            txtID.setText(contact.getId() + "");

            TextView txtHoTen = (TextView) view.findViewById(R.id.textviewName);
            txtHoTen.setText(contact.getName()+"");

            TextView txtPhoneNumber = (TextView) view.findViewById(R.id.textviewPhoneNumbers);
            txtPhoneNumber.setText(contact.getPhoneNumbers()+"");
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
        {
            filter = new CustomFilter();
        }
        return filter;
    }
    private class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length()>0){
                //CONSTRAINT TO UPPER
                constraint = constraint.toString().toUpperCase();
                ArrayList<Contact> filters  = new ArrayList<Contact>();
                //get specific items
                for (int i =0 ; i<filterList.size() ; i++)
                {
                    if (filters.get(i).getName().toUpperCase().contains(constraint)){
                        Contact p = new Contact(filterList.get(i).getId(),filterList.get(i).getName(),filterList.get(i).getPhoneNumbers());
                        filters.add(p);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }else
            {
                results.count = filterList.size();
                results.values= filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactslist = (ArrayList<Contact>)results.values;
            notifyDataSetChanged();
        }
    }

}
