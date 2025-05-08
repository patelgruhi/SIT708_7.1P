package com.example.lostfoundapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {
    TextView tvDetails;
    Button btnRemove;
    DatabaseHelper db;
    long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        tvDetails = findViewById(R.id.tvDetails);
        btnRemove = findViewById(R.id.btnRemove);
        db = new DatabaseHelper(this);

        itemId = getIntent().getLongExtra("ITEM_ID", -1);

        loadDetails();

        btnRemove.setOnClickListener(v -> {
            db.deleteItem(itemId);
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadDetails() {
        Cursor cursor = db.getItemById(itemId);
        if (cursor.moveToFirst()) {
            String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TYPE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATE));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LOCATION));
            tvDetails.setText(type + ": " + description + "\n" + date + "\n" + location);
        }
    }
} 