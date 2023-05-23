package com.example.helloworld;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayAdapter<Contact> adapter;
    private ListView contactListView;
    //    private ContactRepository contactRepository;
    private ContactViewModel contactViewModel;
    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, contacts);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, contacts -> {
            // Update the cached copy of the words in the adapter.
            adapter.clear();
            adapter.addAll(contacts);
            adapter.notifyDataSetChanged();
        });

        contactListView = findViewById(R.id.contactsListView);
        if (contactListView != null) {
            contactListView.setAdapter(adapter);
            contactListView.setOnItemClickListener(this);
        }

        EditText searchField = findViewById(R.id.searchtext);
        if(searchField != null) {
            searchField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    Toast.makeText(MainActivity.this, "Searching for " + editable.toString(), Toast.LENGTH_SHORT).show();
                    if(editable.toString().isEmpty()) {
                        contactViewModel.getAllContacts().observe(MainActivity.this, contacts -> {
                            // Update the cached copy of the words in the adapter.
                            adapter.clear();
                            adapter.addAll(contacts);
                            adapter.notifyDataSetChanged();
                        });
                    }else {
                        contactViewModel.findContacts(editable.toString()).observe(MainActivity.this, contacts -> {
                            // Update the cached copy of the words in the adapter.
                            adapter.clear();
                            adapter.addAll(contacts);
                            adapter.notifyDataSetChanged();
                        });
                    }
                }
            });
        }
    }





    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Contact contact = (Contact) adapterView.getAdapter().getItem(i);
//        Toast.makeText(adapterView.getContext(), "Clicked " + contact.name + ", Email: " + contact.email + ", Mobile: " + contact.mobile,
//                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, NewContactActivity.class);

        if (contact != null) {
            intent.putExtra("contact", contact);
        }
        startActivityForResult.launch(intent);
    }

    public void showAddContact(View view) {
        Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
        startActivityForResult.launch(intent);
    }

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                    Intent data = result.getData();
                }
            }
    );


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String action = data.getStringExtra("action");
            Contact contact = data.getParcelableExtra("contact");
            if (action.equals("save")) {
                boolean contactExists = false;
                Contact existingContact = null;
                for (int i = 0; i < contacts.size(); i++) {
                    existingContact = contacts.get(i);
                    if (existingContact.name.toString().equals(contact.name.toString())) {
                        // Update the existing contact's information
                        existingContact.mobile = contact.mobile;
                        existingContact.email = contact.email;
                        contactExists = true;
                        break;
                    }
                }
                // If the contact doesn't already exist, add it to the ArrayList
                if (!contactExists) {
                    // contacts.add(new Contact(name, email, mobile));
                    contactViewModel.insert(contact);
                } else {
                    contactViewModel.update(existingContact);
                }
            } else if (action.equals("delete")) {
                boolean contactExists = false;
                Contact existingContact = null;
                for (int i = 0; i < contacts.size(); i++) {
                    existingContact = contacts.get(i);
                    if (existingContact.name.toString().equals(contact.name.toString())) {
                        // Update the existing contact's information
                        existingContact.mobile = contact.mobile;
                        existingContact.email = contact.email;
                        contactExists = true;
                        break;
                    }
                }
                // If the contact doesn't already exist, add it to the ArrayList
                if (contactExists) {
                    contactViewModel.delete(existingContact);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}