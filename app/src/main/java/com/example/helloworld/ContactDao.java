package com.example.helloworld;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * from contact_table ORDER BY name")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * from contact_table WHERE name LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%' OR mobile LIKE '%' || :query || '%'")
    LiveData<List<Contact>> findContacts(String query);
}
