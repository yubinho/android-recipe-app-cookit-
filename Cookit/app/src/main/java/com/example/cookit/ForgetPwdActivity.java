package com.example.cookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPwdActivity extends AppCompatActivity {
    private Button btn_reset_email;
    private EditText EditEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        EditEmail = findViewById(R.id.ResetPwd);
        btn_reset_email = findViewById(R.id.btn_forgetPwd);
        mAuth = FirebaseAuth.getInstance();
        btn_reset_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EditEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    EditEmail.setError("需填入信箱");
                    EditEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    EditEmail.setError("請確認信箱格式");
                    EditEmail.requestFocus();
                    return;
                }
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            AlertDialog.Builder AlertDialog = new AlertDialog.Builder(ForgetPwdActivity.this);
                            AlertDialog.setTitle("系統訊息");
                            AlertDialog.setMessage("更改密碼連結已送出,請至信箱進行確認並更改,並使用新密碼進行登入");
                            AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ForgetPwdActivity.this,MainActivity.class);
                                    intent.putExtra("id",2);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog.setCancelable(false);
                            AlertDialog.show();

                        }else {
                            AlertDialog.Builder AlertDialog = new AlertDialog.Builder(ForgetPwdActivity.this);
                            AlertDialog.setTitle("系統訊息");
                            AlertDialog.setMessage("系統內部錯誤,請再重新輸入一次");
                            AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ForgetPwdActivity.this,ForgetPwdActivity.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog.setCancelable(false);
                            AlertDialog.show();
                        }
                    }
                });
            }
        });


    }
}