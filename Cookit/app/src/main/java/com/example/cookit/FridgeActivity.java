package com.example.cookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FridgeActivity extends AppCompatActivity {

    private Button btn_toLeftover,btn_clearFridge;
    private ImageButton btn_recipe;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private TextView leftOverHolder;
    private RecyclerView recyclerView;
    private FridgeAdapter fridgeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        getSupportActionBar().setTitle("Cookit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO:推薦食譜
        recyclerView = findViewById(R.id.fridgeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(FridgeActivity.this));
        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Recipes").orderByChild("checked").equalTo("yes"),Recipe.class)
                        .build();
        fridgeAdapter = new FridgeAdapter(options,this);
        recyclerView.setAdapter(fridgeAdapter);
        //TODO:清空冰箱
        btn_clearFridge = findViewById(R.id.btn_clearFridge);
        btn_clearFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FridgeActivity.this);
                alertDialog.setTitle("系統訊息");
                alertDialog.setMessage("確定要清空您的冰箱嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("leftOver","");
                        FirebaseDatabase.getInstance().getReference("Fridge").child(userId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                                FridgeActivity.this.recreate();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        //TODO:跳轉輸入剩餘食材
        btn_toLeftover = findViewById(R.id.btn_toLeftover);
        btn_toLeftover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(FridgeActivity.this,LeftOverActivity.class);
                startActivity(intent);
            }
        });
        //TODO:顯示目前食材
        leftOverHolder = findViewById(R.id.fridge);
        FirebaseDatabase.getInstance().getReference("Fridge").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Fridge fridge = snapshot.getValue(Fridge.class);
                if(fridge != null){
                    String leftOver = fridge.leftOver.trim();
                    leftOverHolder.setText(leftOver);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        fridgeAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        fridgeAdapter.stopListening();
    }
}