package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ChooseCustomMenuActivity extends AppCompatActivity {

    CustomizeMenuAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_custom_menu);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String category = intent.getStringExtra("category");
        double calorieMin = intent.getDoubleExtra("minCalorie",0);
        double calorieMax = intent.getDoubleExtra("maxCalorie",0);
        recyclerView = findViewById(R.id.chooseCustomMenuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
        .setQuery(FirebaseDatabase.getInstance().getReference("Recipes").orderByChild("calorie").startAt(calorieMin).endAt(calorieMax),Recipe.class)
        .build();
        adapter = new CustomizeMenuAdapter(options,date,time,category);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}