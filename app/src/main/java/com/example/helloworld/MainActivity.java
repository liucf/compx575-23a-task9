package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayAdapter<Contact> adapter;
    private ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Setup Adapter
        contactListView = findViewById(R.id.contactsListView);
        if (contactListView != null) {
            adapter = new ArrayAdapter<Contact>(this,
                    android.R.layout.simple_list_item_1, contacts);
            contactListView.setAdapter(adapter);
            contactListView.setOnItemClickListener(this);

//            savedInstanceState.getParcelableArrayList("contacts").size()
//            if (savedInstanceState != null) {
        }

        if(savedInstanceState != null && savedInstanceState.getParcelableArrayList("contacts") != null) {
            for (Parcelable contact : savedInstanceState.getParcelableArrayList("contacts")) {
                contacts.add((Contact) contact);
            }
        } else {
            // Add some Contacts
            contacts.add(new Contact("Joe Bloggs", "joe@bloggs.co.nz",
                    "021123456"));
            contacts.add(new Contact("Jane Doe", "jane@doe.co.nz",
                    "022123456"));
        }
    }

    public void saveContact(View view) {
        EditText nameField = findViewById(R.id.name);
        String name = nameField.getText().toString();

        EditText emailField = findViewById(R.id.email);
        String email = emailField.getText().toString();

        EditText mobileField = findViewById(R.id.mobile);
        String mobile = mobileField.getText().toString();
        Toast.makeText(this, "Saved contact for " + name + ", Email: " + email + ", Mobile: " + mobile,
                Toast.LENGTH_SHORT).show();

        boolean contactExists = false;
        for (int i = 0; i < contacts.size(); i++) {
            Contact existingContact = contacts.get(i);
            if (existingContact.toString().equals(name.toString())) {
                // Update the existing contact's information
                existingContact.mobile = mobile;
                existingContact.email = email;
                contactExists = true;
                break;
            }
        }

        // If the contact doesn't already exist, add it to the ArrayList
        if (!contactExists) {
            contacts.add(new Contact(name, email, mobile));
        }
        if(adapter!=null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Contact contact = (Contact) adapterView.getAdapter().getItem(i);
        Toast.makeText(adapterView.getContext(), "Clicked " + contact.name + ", Email: " + contact.email + ", Mobile: " + contact.mobile,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle savedState) {
        savedState.putParcelableArrayList("contacts", contacts);
        super.onSaveInstanceState(savedState);
    }
}