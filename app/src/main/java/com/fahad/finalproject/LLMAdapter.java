package com.fahad.finalproject;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;


public class LLMAdapter extends RecyclerView.Adapter<LLMAdapter.LLMViewHolder> {

    private Context context;
    private List<LLM> llmList;

    public LLMAdapter(Context context, List<LLM> llmList) {
        this.context = context;
        this.llmList = llmList;
    }

    @NonNull
    @Override
    public LLMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_llm, parent, false);
        return new LLMViewHolder(view);
    }

    
    @Override
    public void onBindViewHolder(@NonNull LLMViewHolder holder, int position) {
        LLM llm = llmList.get(position);
        holder.llmNameTextView.setText(llm.getName());




        // Load image with Glide
        if (llm.getImageUrl() != null && !llm.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(llm.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.llmImageView);
        } else {
            holder.llmImageView.setImageResource(llm.getImageResId());
        }



        holder.itemView.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("llm", llm);
                context.startActivity(intent);
            } catch (Exception e) {
                Log.e("LLMAdapter", "Error starting DetailActivity", e);
                Toast.makeText(context, "Error opening details", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return llmList.size();
    }

    public static class LLMViewHolder extends RecyclerView.ViewHolder {
        ImageView llmImageView;


        TextView llmNameTextView;
        

        public LLMViewHolder(@NonNull View itemView) {
            super(itemView);
            llmImageView = itemView.findViewById(R.id.llmImageView);
            llmNameTextView = itemView.findViewById(R.id.llmNameTextView);


        }
    }
}