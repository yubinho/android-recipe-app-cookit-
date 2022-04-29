package com.example.cookit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LeftOverActivity extends AppCompatActivity {
    private Button btn_toFridge;
    private Button btn_getLeftover;
    private Button btn_SendLeftOver;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private EditText leftOverHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_over);
        getSupportActionBar().setTitle("Cookit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        leftOverHolder = findViewById(R.id.LeftOver);
        btn_getLeftover = findViewById(R.id.btn_SendLeftover);
        btn_toFridge = findViewById(R.id.btn_toFridge);
        btn_SendLeftOver = findViewById(R.id.btn_SendLeftover);
        btn_toFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent= new Intent(LeftOverActivity.this,FridgeActivity.class);
                startActivity(intent);
            }
        });
        //TODO:輸入剩餘食材
        btn_SendLeftOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Fridge").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Fridge fridge = snapshot.getValue(Fridge.class);
                        if(fridge != null ){
                            String originLeftOver = fridge.leftOver.trim();
                            String leftOver = "";
                            if(!originLeftOver.equals("")){
                                leftOver = originLeftOver+","+leftOverHolder.getText().toString().trim();
                            }else {
                                leftOver = leftOverHolder.getText().toString().trim();
                            }
                            Map<String,Object> map = new HashMap<>();
                            map.put("leftOver",leftOver);
                            FirebaseDatabase.getInstance().getReference("Fridge").child(userId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    AlertDialog.Builder AlertDialog = new AlertDialog.Builder(LeftOverActivity.this);
                                    AlertDialog.setTitle("系統訊息");
                                    AlertDialog.setMessage("輸入成功,按確定返回我的冰箱");
                                    AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(LeftOverActivity.this,FridgeActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    AlertDialog.setCancelable(false);
                                    AlertDialog.show();
                                }
                            });
                        }else{
                            String leftOver = leftOverHolder.getText().toString().trim();
                            Fridge fridge1 = new Fridge(leftOver);
                            FirebaseDatabase.getInstance().getReference("Fridge").child(userId).setValue(fridge1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    AlertDialog.Builder AlertDialog = new AlertDialog.Builder(LeftOverActivity.this);
                                    AlertDialog.setTitle("系統訊息");
                                    AlertDialog.setMessage("輸入成功,按確定返回我的冰箱");
                                    AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(LeftOverActivity.this,FridgeActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    AlertDialog.setCancelable(false);
                                    AlertDialog.show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }
}