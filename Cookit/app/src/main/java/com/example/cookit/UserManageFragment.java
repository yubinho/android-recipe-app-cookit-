package com.example.cookit;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserManageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button btn_fridge,btn_menu,btn_UserChangePwd,btn_logout,btn_userSetting,btn_changeLanguage,btn_history,btn_myMessage;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    public UserManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserManageFragment newInstance(String param1, String param2) {
        UserManageFragment fragment = new UserManageFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_manage, container, false);
        btn_fridge = view.findViewById(R.id.btn_user_fridge);
        btn_userSetting = view.findViewById(R.id.btn_user_setting);
        btn_menu = view.findViewById(R.id.btn_user_menu);
        btn_UserChangePwd = view.findViewById(R.id.btn_user_change_pwd);
        btn_logout = view.findViewById(R.id.btn_user_logout);
        btn_history = view.findViewById(R.id.btn_browse_history);
        btn_myMessage = view.findViewById(R.id.btn_user_comment);
        setListeners();
        //TODO:取出資料庫資料並印出
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        final TextView greetingTextView = (TextView) view.findViewById(R.id.greeting);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String FullName = userProfile.fullname;
                    greetingTextView.setText("Welcome "+FullName+"!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled:");
            }
        });
        //End
        //TODO:切換語言
        loadLocale();
        btn_changeLanguage = view.findViewById(R.id.btn_changeLanguage);
        btn_changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
        btn_myMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.LinearLayout_nav,new MyMessageFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }
    private class Onclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_user_setting:
                    intent = new Intent(getActivity(),ChangeUserDataActivity.class);
                    break;
                case R.id.btn_user_fridge:
                    intent = new Intent(getActivity(),FridgeActivity.class);
                    break;
                case R.id.btn_user_menu:
                    intent = new Intent(getActivity(),MenuActivity.class);
                    break;
                case R.id.btn_user_change_pwd:
                    intent = new Intent(getActivity(),ChangePwdActivity.class);
                    break;
                case R.id.btn_browse_history:
                    intent = new Intent(getActivity(),RecipeHistoryActivity.class);
                    break;
                case R.id.btn_user_logout:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("系統訊息");
                    alertDialog.setMessage("確定要登出嗎?");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            fragmentManager = getActivity().getSupportFragmentManager();
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.LinearLayout_nav, new LoginFragment()).commit();
                            dialog.dismiss();
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
            if(intent != null) {
                startActivity(intent);
            }
        }
    }
    private void setListeners(){
        Onclick onclick = new Onclick();
        btn_fridge.setOnClickListener(onclick);
        btn_menu.setOnClickListener(onclick);
        btn_UserChangePwd.setOnClickListener(onclick);
        btn_logout.setOnClickListener(onclick);
        btn_userSetting.setOnClickListener(onclick);
        btn_history.setOnClickListener(onclick);
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