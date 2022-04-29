package com.example.cookit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    MenuLunchAdapter adapter;
    RecyclerView recyclerView;
    private Button btn_toCustomizeMenu,btn_toCustomizeLunch,btn_toCustomizeBreakfast,btn_toCustomizeDinner;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle(R.string.customizeMenu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        recyclerView = findViewById(R.id.customMenuRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        FirebaseRecyclerOptions<Menu> options = new FirebaseRecyclerOptions.Builder<Menu>()
//                .setQuery(FirebaseDatabase.getInstance().getReference("customMenu").child(userId),Menu.class)
//                .build();
//        adapter = new MenuAdapter(options);
//        recyclerView.setAdapter(adapter);
        //TODO:客製化早餐顯示
        btn_toCustomizeBreakfast = findViewById(R.id.btn_CustomizeBreakfast);
        btn_toCustomizeBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MenuActivity.this,MenuBreakfastActivity.class);
                startActivity(intent);
            }
        });
        //TODO:客製化午餐顯示
        btn_toCustomizeLunch = findViewById(R.id.btn_CustomizeLunch);
        btn_toCustomizeLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MenuActivity.this,MenuLunchActivity.class);
                startActivity(intent);
            }
        });
        //TODO:客製化晚餐顯示
        btn_toCustomizeDinner = findViewById(R.id.btn_CustomizeDinner);
        btn_toCustomizeDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MenuActivity.this,MenuDinnerActivity.class);
                startActivity(intent);
            }
        });
        //TODO:客製化菜單按鈕
        btn_toCustomizeMenu = findViewById(R.id.btn_toCustomizeMenu);
        btn_toCustomizeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MenuActivity.this,CustomizeMenuActivity.class);
                startActivity(intent);
            }
        });
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
