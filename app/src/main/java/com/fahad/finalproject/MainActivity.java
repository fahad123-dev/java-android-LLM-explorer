package com.fahad.finalproject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMusicPlayer();

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.llmRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create list of LLMs
        List<LLM> llmList = new ArrayList<>();
        llmList.add(new LLM(getString(R.string.chatgpt), getString(R.string.chatgpt_desc),
                R.drawable.chatgpt, "https://example.com/chatgpt.png"));
        llmList.add(new LLM(getString(R.string.bard), getString(R.string.bard_desc),
                R.drawable.bard, "https://example.com/bard.png"));
        llmList.add(new LLM(getString(R.string.deepseek), getString(R.string.deepseek_desc),
                R.drawable.deepseek, "https://example.com/deepseek.png"));
        llmList.add(new LLM(getString(R.string.llama), getString(R.string.llama_desc),
                R.drawable.llama, "https://example.com/llama.png"));
        llmList.add(new LLM(getString(R.string.qwen), getString(R.string.qwen_desc),
                R.drawable.qwen, "https://example.com/qwen.png"));
        llmList.add(new LLM(getString(R.string.bangla_llm), getString(R.string.bangla_llm_desc),
                R.drawable.bangla_llm, "https://example.com/bangla_llm.png"));

        // Set adapter
        LLMAdapter adapter = new LLMAdapter(this, llmList);
        recyclerView.setAdapter(adapter);
    }

    private void setupMusicPlayer() {
        // Initialize media player
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // Setup music toggle button
        ImageButton musicButton = findViewById(R.id.musicToggleButton);
        musicButton.setOnClickListener(v -> toggleMusic());
    }

    private void toggleMusic() {
        ImageButton musicButton = findViewById(R.id.musicToggleButton);

        if (isMusicPlaying) {
            mediaPlayer.pause();
            musicButton.setImageResource(R.drawable.ic_music_off);
        } else {
            mediaPlayer.start();
            musicButton.setImageResource(R.drawable.ic_music_on);
        }
        isMusicPlaying = !isMusicPlaying;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}