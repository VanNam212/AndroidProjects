package com.example.contentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {

    ListView lvContact;
    EditText etContactNameFilter;
    ImageView fabAdd;

    ArrayList<ContactModel> contacts;
    ContactCustomAdapter adapter;
    public void loadContact(){
        contacts=new ArrayList<>();
        ContentResolver cr=getContentResolver();
        Cursor cur=cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        if((cur!=null ? cur.getCount():0)>0){
            while (cur!=null && cur.moveToNext()){
                //String contactDetails;
                String id=cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name=cur.getString((cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                String phoneNo="";
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))>0){
                    Cursor pCur=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?",
                            new String[]{id},null);
                    while (pCur.moveToNext()){
                        if (phoneNo!="") {
                            phoneNo = phoneNo + ", ";
                        }
                        phoneNo=phoneNo+pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    }
                    pCur.close();
                    //contactDetails=id+", "+name+": "+phoneNo;
                    ContactModel contact=new ContactModel(Integer.parseInt(id),name,phoneNo);
                    contacts.add(contact);
                }
            }
        }
        if (cur!=null){
            cur.close();
        }
        adapter=new ContactCustomAdapter(Contact.this,contacts);
        lvContact.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setTitle("Contact");
        etContactNameFilter=(EditText)findViewById(R.id.etContactNameFilter);
        lvContact=(ListView)findViewById(R.id.lvContact);
        fabAdd=(ImageView)findViewById(R.id.fabAdd);

        //////////////////
        registerForContextMenu(lvContact);
        /////////////////

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_INSERT,ContactsContract.Contacts.CONTENT_URI);
                startActivity(intent);
            }
        });
        etContactNameFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadContact();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuInformation:
                Toast.makeText(Contact.this, "Application for contact", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuBack:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextmenu,menu);
        super.onCreateContextMenu(menu,v,menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String myData;
        Intent myActivity;
        final int pos=contacts.get(info.position).getId();
        switch (item.getItemId()){
            case  R.id.menuView:
                myData="content://contacts/people/"+(pos);
                myActivity=new Intent(Intent.ACTION_VIEW, Uri.parse(myData));
                startActivity(myActivity);
                break;
            case R.id.menuEdit:
                myData="content://contacts/people/"+(pos);
                myActivity=new Intent(Intent.ACTION_EDIT, Uri.parse(myData));
                startActivity(myActivity);
                loadContact();
                break;
            case R.id.menuDelete:
//                final ArrayList ops=new ArrayList();
//                final ContentResolver cr=getContentResolver();
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(Contact.this);
                alertDialog.setTitle("Delete this contact !");
                alertDialog.setMessage("Are you sure want to delete this contact ?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList ops=new ArrayList();
                        ContentResolver cr=getContentResolver();
                        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                                .withSelection(ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ?",new String[]{pos+""}).build());
                        try{
                            cr.applyBatch(ContactsContract.AUTHORITY,ops);
                        }
                        catch (OperationApplicationException e){
                            e.printStackTrace();
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }ops.clear();
                        loadContact();
                    }
                });
                alertDialog.setNegativeButton("No",null);
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
