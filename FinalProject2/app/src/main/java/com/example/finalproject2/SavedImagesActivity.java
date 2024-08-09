package com.example.finalproject2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SavedImagesAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SavedImage> savedImages = dbHelper.getAllImages();
        adapter = new SavedImagesAdapter(savedImages, dbHelper);
        recyclerView.setAdapter(adapter);
    }
}
