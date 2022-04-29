package com.example.cookit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class MessageBoardActivity extends AppCompatActivity {
    private String recipeKey,userId,recipeName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Button btn_message;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);
        recipeKey = getIntent().getStringExtra("recipeKey");
        recipeName = getIntent().getStringExtra("recipeName");
        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(user != null){
            userId = user.getUid();
        }
        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("message").orderByChild("recipeKey").equalTo(recipeKey),Message.class)
                        .build();
        messageAdapter = new MessageAdapter(options,userId);
        recyclerView.setAdapter(messageAdapter);

        //TODO:留言
        btn_message = findViewById(R.id.btn_leaveMessage);
        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    LayoutInflater inflater = MessageBoardActivity.this.getLayoutInflater();
                    String randomKey = UUID.randomUUID().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MessageBoardActivity.this);
                    View dialogView = inflater.inflate(R.layout.message_dialog,null);
                    builder.setTitle("請在以下留言")
                            .setView(dialogView)
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            User userProfile = snapshot.getValue(User.class);
                                            if(userProfile != null){
                                                String FullName = userProfile.fullname;
                                                EditText editText = dialogView.findViewById(R.id.messageDialog);
                                                String comment = editText.getText().toString().trim();
                                                if(!(comment == null ||comment.equals(""))){
                                                    Message message = new Message(comment,userId,FullName,recipeKey,recipeName);
                                                    FirebaseDatabase.getInstance().getReference("message").child(randomKey).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(MessageBoardActivity.this,"已新增留言",Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.setCancelable(false);
                    builder.show();
                }else {
                    Toast.makeText(MessageBoardActivity.this,"請先登入",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        if(messageAdapter != null){
            messageAdapter.startListening();
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        messageAdapter.stopListening();
    }
}