package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    RecipeHistoryAdapter recipeHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_history);
        recyclerView = findViewById(R.id.HistoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("History").child(userId),Recipe.class)
                        .build();
        recipeHistoryAdapter = new RecipeHistoryAdapter(options);
        recyclerView.setAdapter(recipeHistoryAdapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        recipeHistoryAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        recipeHistoryAdapter.stopListening();
    }

}