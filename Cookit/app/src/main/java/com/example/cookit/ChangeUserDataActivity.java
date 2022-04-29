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
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserDataActivity extends AppCompatActivity {

    private TextView currentName;
    private EditText newName;
    private Button btn_ResetUserName;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_data);
        currentName = findViewById(R.id.currentUserName);
        newName = findViewById(R.id.resetUserName);
        btn_ResetUserName = findViewById(R.id.btn_ResetUserName);
        userID =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    currentName.setText(userProfile.fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_ResetUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                String userName = newName.getText().toString().trim();
                if(userName.isEmpty()){
                    newName.setError("需輸入暱稱");
                    newName.requestFocus();
                }
                map.put("fullname",userName);
                FirebaseDatabase.getInstance().getReference("Users").child(userID).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(ChangeUserDataActivity.this);
                                AlertDialog.setTitle("系統訊息");
                                AlertDialog.setMessage("更改成功");
                                AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ChangeUserDataActivity.this, MainActivity.class);
                                        intent.putExtra("id",3);
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog.setCancelable(false);
                                AlertDialog.show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

    }
}
