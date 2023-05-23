package com.example.helloworld;


import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import java.util.List;

public class ContactRepository {

    private ContactDao contactDao;

    private LiveData<List<Contact>> allContacts;

    ContactRepository(Context context) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(context);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    public void insert(Contact contact) {
//        new InsertAsyncTask().execute(contact);
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.insert(contact);
        });
    }


    public void update(Contact contact) {
//        new UpdateAsyncTask().execute(contact);
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.update(contact);
        });
    }

    public void delete(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.delete(contact);
        });
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<List<Contact>> findContacts(String toString) {
        return contactDao.findContacts(toString);
    }
}