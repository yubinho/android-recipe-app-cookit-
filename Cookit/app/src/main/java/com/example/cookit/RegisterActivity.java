package com.example.cookit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private Activity context = this;
    private Button btn_Register, btn_toLogin, btn_toForget;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private EditText EditText_email, EditText_pwd, EditText_name;
    private FirebaseAuth mAuth;
    private static final String ACTIVITY_TAG = "LogDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_Register = findViewById(R.id.btn_register);
        btn_toForget = findViewById(R.id.btn_reg_forgetPwd);
        btn_toLogin = findViewById(R.id.btn_toLogin);
        EditText_email = findViewById(R.id.Re_email);
        EditText_pwd = findViewById(R.id.Re_pwd);
        EditText_name = findViewById(R.id.user_name);
        mAuth = FirebaseAuth.getInstance(); //資料庫驗證

        //註冊後台
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EditText_email.getText().toString().trim();
                String pwd = EditText_pwd.getText().toString().trim();
                String FullName = EditText_name.getText().toString().trim();
                if (FullName.isEmpty()) {
                    EditText_name.setError("需填入暱稱");
                    EditText_name.requestFocus();
                    return;

                }else if (email.isEmpty()) {
                    EditText_email.setError("需填入信箱");
                    EditText_email.requestFocus();
                    return;


                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    EditText_email.setError("請確認信箱格式");
                    EditText_email.requestFocus();
                    return;


                } else if (pwd.isEmpty()) {
                    EditText_pwd.setError("需填入密碼");
                    EditText_pwd.requestFocus();
                    return;


                } else if (pwd.length() < 6) {
                    EditText_pwd.setError("密碼長度最少為6個字元");
                    EditText_pwd.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(FullName, email);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                user.sendEmailVerification();
                                                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(RegisterActivity.this);
                                                AlertDialog.setTitle("系統訊息");
                                                AlertDialog.setMessage("註冊成功,請至信箱進行驗證,按下確定返回會員登入");
                                                AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //Activity to loginFragment
                                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                        intent.putExtra("id", 2);
                                                        startActivity(intent);
                                                    }
                                                });
                                                AlertDialog.setCancelable(false);
                                                AlertDialog.show();
                                            } else {
                                               //Toast.makeText(RegisterActivity.this, "失敗", Toast.LENGTH_LONG).show();
                                                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(RegisterActivity.this);
                                                AlertDialog.setTitle("系統訊息");
                                                AlertDialog.setMessage("系統內部錯誤,請再重新註冊一次");
                                                AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // back
                                                        Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                                AlertDialog.setCancelable(false);
                                                AlertDialog.show();
                                            }
                                        }
                                    });
                                } else {
//                                  Toast.makeText(RegisterActivity.this, "fail", Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder AlertDialog = new AlertDialog.Builder(RegisterActivity.this);
                                    AlertDialog.setTitle("系統訊息");
                                    AlertDialog.setMessage("註冊失敗,信箱已被註冊");
                                    AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // back
                                            Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    AlertDialog.setNegativeButton("忘記密碼", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent;
                                            intent = new Intent(RegisterActivity.this, ForgetPwdActivity.class);
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

        btn_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });
        btn_toForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, ForgetPwdActivity.class);
                startActivity(intent);
            }
        });
    }
}




