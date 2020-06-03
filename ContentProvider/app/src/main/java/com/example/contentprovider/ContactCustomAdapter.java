package com.example.contentprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactCustomAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<ContactModel>contactArrayList;
    private CustomFilter filter;
    private ArrayList<ContactModel>filterList;

    public ContactCustomAdapter(Context context, ArrayList<ContactModel> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
        this.filterList=contactArrayList;
    }

    @Override
    public int getCount() {
        return contactArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.activity_single_row_contact,null);

        }
        ContactModel contact=(ContactModel) getItem(position);
        if (contact!=null){
            //Anh xa gan giatri
            TextView tvID=convertView.findViewById(R.id.tvID);
            tvID.setText(contact.getId()+"");
            TextView tvName=convertView.findViewById(R.id.tvName);
            tvName.setText(contact.getContactName());
            TextView tvPhoneNumbers=convertView.findViewById(R.id.tvPhoneNumbers);
            tvPhoneNumbers.setText(contact.getPhoneNumber());
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter==null){
            filter=new CustomFilter();
        }
        return filter;
    }
    private class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                constraint=constraint.toString().toUpperCase();
                ArrayList<ContactModel>filters=new ArrayList<>();
                for (int i=0;i<filterList.size();i++){
                    if(filterList.get(i).getContactName().toUpperCase().contains(constraint)){
                        ContactModel p= new ContactModel(filterList.get(i).getId(),filterList.get(i).getContactName(),filterList.get(i).getPhoneNumber());
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            contactArrayList=(ArrayList<ContactModel>)results.values;
            notifyDataSetChanged();
        }
    }
}