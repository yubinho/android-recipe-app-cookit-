package com.example.cookit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.Inet4Address;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Button btn_login, btn_ToReg, btn_ToRegForget,btn_changeLanguage;
    private EditText EditEmail, EditPwd;
    private String mParam1;
    private String mParam2;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //loadLocale();
        EditEmail = view.findViewById(R.id.login_email);
        EditPwd = view.findViewById(R.id.login_pwd);
        btn_ToReg = view.findViewById(R.id.btn_Login_Reg);
        btn_ToRegForget = view.findViewById(R.id.btn_Reg_forgetPwd);
        btn_login = view.findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser LoginUser = mAuth.getCurrentUser();//資料庫驗證
        setListeners();
        //TODO:session機制
        if(LoginUser != null && LoginUser.isEmailVerified()){
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.LinearLayout_nav, new UserManageFragment()).commit();;
        }
        //TODO:登入後端


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EditEmail.getText().toString().trim();
                String pwd = EditPwd.getText().toString().trim();
                if (email.isEmpty()) {
                    EditEmail.setError("需填入信箱");
                    EditEmail.requestFocus();
                    return ;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    EditEmail.setError("請確認信箱格式");
                    EditEmail.requestFocus();
                    return ;


                }
                if (pwd.isEmpty()) {
                    EditPwd.setError("需填入密碼");
                    EditPwd.requestFocus();
                    return ;


                }
                if (pwd.length() < 6) {
                    EditPwd.setError("密碼長度最少為6個字元");
                    EditPwd.requestFocus();
                    return ;
                }
                mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(getActivity());
                                AlertDialog.setTitle("系統訊息");
                                AlertDialog.setMessage("登入成功,按下確定跳轉至會員專區");
                                AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        transaction = fragmentManager.beginTransaction();
                                        transaction.replace(R.id.LinearLayout_nav, new UserManageFragment()).commit();
                                    }
                                });
                                AlertDialog.setCancelable(false);
                                AlertDialog.show();
                            } else {
                                user.sendEmailVerification();
                                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(getActivity());
                                AlertDialog.setTitle("系統訊息");
                                AlertDialog.setMessage("登入失敗,請確認信箱是否已進行認證");
                                AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        transaction = fragmentManager.beginTransaction();
                                        transaction.replace(R.id.LinearLayout_nav, new LoginFragment()).commit();
                                    }
                                });
                                AlertDialog.setCancelable(false);
                                AlertDialog.show();
                            }
                        }else{
                            AlertDialog.Builder AlertDialog = new AlertDialog.Builder(getActivity());
                            AlertDialog.setTitle("系統訊息");
                            AlertDialog.setMessage("登入失敗,請確認信箱密碼有無錯誤");
                            AlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    fragmentManager = getActivity().getSupportFragmentManager();
                                    transaction = fragmentManager.beginTransaction();
                                    transaction.replace(R.id.LinearLayout_nav, new LoginFragment()).commit();
                                }
                            });
                            AlertDialog.setNegativeButton("忘記密碼", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent;
                                    intent = new Intent(getActivity(), ForgetPwdActivity.class);
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
        //TODO:切換語言
        loadLocale();
        btn_changeLanguage = view.findViewById(R.id.btn_changeLanguage);
        btn_changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

        return view;
    }
//TODO:按鈕頁面跳轉
    private class Onclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_Login_Reg:
                    intent = new Intent(getActivity(), RegisterActivity.class);
                    break;
                case R.id.btn_Reg_forgetPwd:
                    intent = new Intent(getActivity(), ForgetPwdActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    private void setListeners() {
        Onclick onclick = new Onclick();
        btn_ToReg.setOnClickListener(onclick);
        btn_ToRegForget.setOnClickListener(onclick);
    }
    private void showChangeLanguageDialog(){
        final String[] languageList = {"中文","English"};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("選擇語言");
        alertDialogBuilder.setSingleChoiceItems(languageList, (int) -1., new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    setLocale("ch");
                    getActivity().recreate();
                }else if(which == 1){
                    setLocale("en");
                    getActivity().recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config,
                getActivity().getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings",getActivity().MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }
}