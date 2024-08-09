package com.example.finalproject2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SavedImagesAdapter extends RecyclerView.Adapter<SavedImagesAdapter.ViewHolder> {

    private List<SavedImage> savedImages;
    private DatabaseHelper dbHelper;

    public SavedImagesAdapter(List<SavedImage> savedImages, DatabaseHelper dbHelper) {
        this.savedImages = savedImages;
        this.dbHelper = dbHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.individual_saved_images, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SavedImage savedImage = savedImages.get(position);

        holder.dateTextView.setText(savedImage.getDate());
        holder.explanationTextView.setText(savedImage.getExplanation());
        Glide.with(holder.itemView.getContext()).load(savedImage.getImageUrl()).into(holder.imageView);

        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteImage(savedImage.getDate());
            savedImages.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, savedImages.size());
        });
    }

    @Override
    public int getItemCount() {
        return savedImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView dateTextView;
        public TextView explanationTextView;
        public ImageView deleteButton;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            dateTextView = view.findViewById(R.id.dateTextView);
            explanationTextView = view.findViewById(R.id.explanationTextView);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}
