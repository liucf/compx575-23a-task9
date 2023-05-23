package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Contact contact = extras.getParcelable("contact");


            EditText nameField = findViewById(R.id.name);
            nameField.setText(contact.name);

            EditText emailField = findViewById(R.id.email);
            emailField.setText(contact.email);

            EditText mobileField = findViewById(R.id.mobile);
            mobileField.setText(contact.mobile);

            Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
            btnSubmit.setText("Update Contact");

            Button btnDelete = (Button) findViewById(R.id.btnSubmitDelete);
            btnDelete.setVisibility(View.VISIBLE);

        }
    }

    public void saveContact( View view ) {
        EditText nameField = findViewById(R.id.name);
        String name = nameField.getText().toString();

        EditText emailField = findViewById(R.id.email);
        String email = emailField.getText().toString();

        EditText mobileField = findViewById(R.id.mobile);
        String mobile = mobileField.getText().toString();
        Toast.makeText(this, "Saved contact for " + name + ", Email: " + email + ", Mobile: " + mobile,
                Toast.LENGTH_SHORT).show();

        Contact contact = new Contact(name, email, mobile);
        Intent intent = new Intent();
        intent.putExtra("contact", contact);
        intent.putExtra("action", "save");
        setResult(RESULT_OK, intent);

        finish();
    }

    public void deleteContact( View view ) {

        EditText nameField = findViewById(R.id.name);
        String name = nameField.getText().toString();

        EditText emailField = findViewById(R.id.email);
        String email = emailField.getText().toString();

        EditText mobileField = findViewById(R.id.mobile);
        String mobile = mobileField.getText().toString();


        Contact contact = new Contact(name, email, mobile);

        Intent intent = new Intent();
        intent.putExtra("contact", contact);
        intent.putExtra("action", "delete");
        setResult(RESULT_OK, intent);

        finish();
    }


}