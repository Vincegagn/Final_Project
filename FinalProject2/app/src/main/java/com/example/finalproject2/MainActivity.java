package com.example.finalproject2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button btnFetch, btnSave, btnViewSaved;
    private ImageView imageView;
    private TextView textDescription, textUrl, textDate;
    private DatabaseHelper dbHelper;

    private static final String API_KEY = "DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d";
    private static final String BASE_URL = "https://api.nasa.gov/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        datePicker = findViewById(R.id.datePicker);
        btnFetch = findViewById(R.id.btnFetch);
        btnSave = findViewById(R.id.btnSave);
        btnViewSaved = findViewById(R.id.btnViewSaved);
        imageView = findViewById(R.id.imageView);
        textDescription = findViewById(R.id.textDescription);
        textUrl = findViewById(R.id.textUrl);
        textDate = findViewById(R.id.textDate);

        btnSave.setVisibility(View.GONE);  // Initially hide the save button

        btnFetch.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            String date = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
            fetchImage(date);
        });

        btnSave.setOnClickListener(v -> {
            String date = textDate.getText().toString();
            String imageUrl = imageView.getTag().toString();
            String explanation = textDescription.getText().toString();
            SavedImage savedImage = new SavedImage(date, imageUrl, "", explanation);
            dbHelper.addImage(savedImage);
        });

        btnViewSaved.setOnClickListener(v -> {
            List<SavedImage> savedImages = dbHelper.getAllImages();
            // Implement your logic to display the saved images
        });
    }

    private void fetchImage(String date) {
        String url = "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + date;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NasaApiService service = retrofit.create(NasaApiService.class);
        Call<NasaResponse> call = service.getApod(API_KEY, date);

        call.enqueue(new Callback<NasaResponse>() {
            @Override
            public void onResponse(Call<NasaResponse> call, Response<NasaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NasaResponse nasaResponse = response.body();
                    Glide.with(MainActivity.this)
                            .load(nasaResponse.getUrl())
                            .into(imageView);
                    textDescription.setText(nasaResponse.getExplanation());
                    textUrl.setText(nasaResponse.getUrl());
                    textDate.setText(nasaResponse.getDate());
                    imageView.setTag(nasaResponse.getUrl());

                    findViewById(R.id.initialLayout).setVisibility(View.GONE);
                    findViewById(R.id.resultLayout).setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.VISIBLE);  // Show the save button
                }
            }

            @Override
            public void onFailure(Call<NasaResponse> call, Throwable t) {
                textDescription.setText("Failed to fetch image");
            }
        });
    }
}
