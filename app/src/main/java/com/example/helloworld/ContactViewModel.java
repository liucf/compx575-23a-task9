package com.example.helloworld;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository contactRepository;

    private final LiveData<List<Contact>> allContacts;

    public ContactViewModel (Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
        allContacts = contactRepository.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insert(Contact contact) { contactRepository.insert(contact); }

    public void update(Contact contact) { contactRepository.update(contact); }

    public void delete(Contact contact) { contactRepository.delete(contact); }

    public LiveData<List<Contact>> findContacts(String toString) {
        return contactRepository.findContacts(toString);
    }
}
