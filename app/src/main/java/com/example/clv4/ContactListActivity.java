package com.example.clv4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder)view.getTag();
        int position = viewHolder.getAdapterPosition();
        Intent intent = new Intent(ContactListActivity.this,MainActivity.class);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initListButton();
        initMapButton();
        initSettingsButton();

        ContactDataSource ds = new ContactDataSource(this);

        ArrayList<String> names;

        String sortBy = getSharedPreferences("MyContactListPreferences",

                Context.MODE_PRIVATE).getString("sortfield", "contactname");

        String sortOrder = getSharedPreferences("MyContactListPreferences",

                Context.MODE_PRIVATE).getString("sortorder", "ASC");
        try{
            ds.open();
            names = ds.getContactName();
            ds.close();

            RecyclerView contactList = findViewById(R.id.rvContacts);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            contactList.setLayoutManager(layoutManager);
            ContactAdapter contactAdapter = new ContactAdapter(names);
            contactList.setAdapter(contactAdapter);
        }
        catch(Exception e){
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

    }

    private void initSettingsButton() {

    }

    private void initMapButton() {

    }

    private void initListButton() {

    }

}
