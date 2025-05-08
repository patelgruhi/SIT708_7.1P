package com.example.lostfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemsListActivity extends AppCompatActivity {
    ListView listView;
    DatabaseHelper db;
    ArrayList<String> items;
    ArrayList<Long> ids;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        listView = findViewById(R.id.listView);
        db = new DatabaseHelper(this);
        items = new ArrayList<>();
        ids = new ArrayList<>();

        loadItems();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ItemsListActivity.this, ItemDetailActivity.class);
            intent.putExtra("ITEM_ID", ids.get(position));
            startActivity(intent);
        });
    }

    private void loadItems() {
        items.clear();
        ids.clear();
        Cursor cursor = db.getAllItems();
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TYPE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DESCRIPTION));
                items.add(type + ": " + description);
            } while (cursor.moveToNext());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }
} 