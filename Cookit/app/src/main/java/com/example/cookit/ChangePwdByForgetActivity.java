package com.example.cookit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//abort
public class ChangePwdByForgetActivity extends AppCompatActivity {
    private Button btn_ChangePwdByForget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd_by_forget);
        btn_ChangePwdByForget = findViewById(R.id.btn_ChangePwdByForget);
        btn_ChangePwdByForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(ChangePwdByForgetActivity.this);
                AlertDialog.setTitle("系統訊息");
                AlertDialog.setMessage("更改成功,請使用新密碼重新登入");
                AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ChangePwdByForgetActivity.this,MainActivity.class);
                        intent.putExtra("id",2);
                        startActivity(intent);
                    }
                });
                AlertDialog.setCancelable(false);
                AlertDialog.show();
            }
        });
    }
}