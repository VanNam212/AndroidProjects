package com.example.contentprovider;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com.example.contentprovider.R;

public class ContactsActivity extends AppCompatActivity {
    ListView listViewContact ;
    EditText txtContactNameFilter;
    FloatingActionButton fabAdd;
    ArrayList<Contact> contacts;
    ContactCustomAdapter adapter;

    public void loadContacts(){
        contacts = new ArrayList<Contact>();
//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null);
        Uri uri = Uri.parse("content://contacts/people");
        CursorLoader loader = new CursorLoader(this,uri,null,null,null,null);
        Cursor cur = loader.loadInBackground();
        if ((cur != null ? cur.getCount() : 0 ) > 0 ){
            cur.moveToFirst();
            do{
                String contactDetails;
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name  = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phoneNo = "";
                int numberPhone = cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                if (Integer.valueOf(numberPhone) != 0 ){
                    Cursor pCur = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+ id,
                            null,
                            null);
                    while (pCur.moveToNext()){
                        if (phoneNo != "") phoneNo = phoneNo + " , ";
                        phoneNo = phoneNo + pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    pCur.close();
                    contactDetails = id + " , " + name + " : " + phoneNo;
                    Contact contact = new Contact(Integer.parseInt(id),name , phoneNo);
                    contacts.add(contact);
                }else{
                    contactDetails = id + " , " + name + " : " + "Not data";
                    Contact contact = new Contact(Integer.parseInt(id), name , "Null");
                    contacts.add(contact);
                }
            }while (cur.moveToNext());
        }
        if (cur != null){
            cur.close();
        }
        adapter = new ContactCustomAdapter(
                ContactsActivity.this,
                contacts,
                null
        );
        listViewContact.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
            setContentView(R.layout.activity_contacts);
            setTitle("Contact");
            txtContactNameFilter = (EditText) findViewById(R.id.edittextContactNamefilter);
            listViewContact = (ListView)findViewById(R.id.listViewContact);
            loadContacts();

            //for context menu step 2
            registerForContextMenu(listViewContact);
            //for floating action button
            fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
            //For floating action button step 2
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_INSERT ,
                            ContactsContract.Contacts.CONTENT_URI);
                    startActivity(intent);
                }
            });
            txtContactNameFilter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        protected void onResume(){
            super.onResume();
            loadContacts();
        }
        //For menu step 1
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.mainmenu,menu);
            return super.onCreateOptionsMenu(menu);
        }
        //For menu step 2
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item){
            switch (item.getItemId()){
                case R.id.menuInformation:
                    Toast.makeText(ContactsActivity.this, "Contacts appliacations", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menuBack:
                    finish();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
        //For context menu step 1
        @Override
        public void onCreateContextMenu(ContextMenu menu , View v , ContextMenu.ContextMenuInfo menuInfo){
            getMenuInflater().inflate(R.menu.contextmenu,menu);
            super.onCreateContextMenu(menu, v, menuInfo);
        }
        //For context menu step 3

        public boolean onCreteItemSelected(@NonNull MenuItem item){
            //position on the ListView
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            String myData;
            Intent myActivity;
            final int pos = contacts.get(info.position).getId();
            switch (item.getItemId()){
                case R.id.menuview:
                    myData = "context://contacts/people/" + (pos);
                    myActivity= new Intent(Intent.ACTION_VIEW , Uri.parse(myData));
                    startActivity(myActivity);
                    break;
                case R.id.menuEdit:
                    myData = "context://contacts/people/" + (pos);
                    myActivity= new Intent(Intent.ACTION_EDIT , Uri.parse(myData));
                    startActivity(myActivity);
                    break;

            }
            return super.onOptionsItemSelected(item);
        }
}

