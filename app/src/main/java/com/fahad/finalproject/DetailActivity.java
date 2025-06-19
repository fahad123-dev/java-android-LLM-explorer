package com.fahad.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailActivity extends AppCompatActivity {

    private LLM llm;
    private RatingBar ratingBar;
    private TextView currentRatingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.d("DetailActivity", "Received intent: " + getIntent());
        if (getIntent().hasExtra("llm")) {
            Log.d("DetailActivity", "Has LLM extra");
        } else {
            Log.e("DetailActivity", "No LLM extra found in intent");
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize views first
        ImageView llmImageView = findViewById(R.id.llmDetailImageView);
        TextView llmNameTextView = findViewById(R.id.llmNameTextView);
        TextView llmDescriptionTextView = findViewById(R.id.llmDescriptionTextView);
        ratingBar = findViewById(R.id.ratingBar);
        currentRatingTextView = findViewById(R.id.currentRatingTextView);

        // Enable back button in toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get LLM object from intent with proper error handling
        try {
            if (getIntent() != null && getIntent().hasExtra("llm")) {
                llm = (LLM) getIntent().getSerializableExtra("llm");
            } else {
                Toast.makeText(this, "Error: No data found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set LLM data with proper null checks
        if (llm != null) {
            // Set text content
            llmNameTextView.setText(llm.getName() != null ? llm.getName() : "Unknown");
            llmDescriptionTextView.setText(llm.getDescription() != null ? llm.getDescription() : "No description available");

            // Load image - prioritize local resource if available
            try {
                if (llm.getImageResId() != 0) {
                    // Load from local drawable
                    Glide.with(this)
                            .load(llm.getImageResId())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(llmImageView);
                } else if (llm.getImageUrl() != null && !llm.getImageUrl().isEmpty()) {
                    // Load from URL
                    Glide.with(this)
                            .load(llm.getImageUrl())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(llmImageView);
                } else {
                    // Default image if no source available
                    llmImageView.setImageResource(R.drawable.ic_launcher_background);
                }
            } catch (Exception e) {
                llmImageView.setImageResource(R.drawable.ic_launcher_background);
            }

            // Set toolbar title
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(llm.getName());
            }

            // Load saved rating
            loadRating();
        } else {
            Toast.makeText(this, "Error: Invalid data", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Set up rating bar listener
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser && llm != null) {
                    saveRating(rating);
                    updateRatingText(rating);
                }
            }
        });
    }

    private void loadRating() {
        if (llm != null && llm.getName() != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("LLMRatings", MODE_PRIVATE);
            float savedRating = sharedPreferences.getFloat(llm.getName(), 0);
            if (savedRating > 0) {
                ratingBar.setRating(savedRating);
                updateRatingText(savedRating);
            }
        }
    }
    private void saveRating(float rating) {
        if (llm != null && llm.getName() != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("LLMRatings", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(llm.getName(), rating);
            editor.apply();
        }
    }

    private void updateRatingText(float rating) {
        currentRatingTextView.setText(String.format("(%.1f/5.0)", rating));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}