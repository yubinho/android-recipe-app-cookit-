package com.example.cookit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.Map;


public class RecipeEditDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private double calorie;
    private String name,recipeName,category,purl,recipeInfo,recipeIngredient,step1,step2,step3,step4,step5,step6,step7,step8,step9,step10;
    private String imageStep1,imageStep2,imageStep3,imageStep4,imageStep5,imageStep6,imageStep7,imageStep8,imageStep9,imageStep10;

    public RecipeEditDetailFragment() {
        // Required empty public constructor
    }
    public RecipeEditDetailFragment(String name,String recipeName,String category,String purl,String recipeInfo,String recipeIngredient
            ,String step1,String step2,String step3,String step4,String step5,String step6,String step7,String step8,String step9,String step10
            ,String imageStep1,String imageStep2,String imageStep3,String imageStep4,String imageStep5,String imageStep6,String imageStep7,String imageStep8,String imageStep9,String imageStep10,double calorie) {
        this.name = name;
        this.recipeName = recipeName;
        this.category = category;
        this.purl = purl;
        this.recipeInfo = recipeInfo;
        this.calorie = calorie;
        this.recipeIngredient = recipeIngredient;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
        this.step5 = step5;
        this.step6 = step6;
        this.step7 = step7;
        this.step8 = step8;
        this.step9 = step9;
        this.step10 = step10;
        this.imageStep1 = imageStep1;
        this.imageStep2 = imageStep2;
        this.imageStep3 = imageStep3;
        this.imageStep4 = imageStep4;
        this.imageStep5 = imageStep5;
        this.imageStep6 = imageStep6;
        this.imageStep7 = imageStep7;
        this.imageStep8 = imageStep8;
        this.imageStep9 = imageStep9;
        this.imageStep10 = imageStep10;
    }
    // TODO: Rename and change types and number of parameters
    public static RecipeEditDetailFragment newInstance(String param1, String param2) {
        RecipeEditDetailFragment fragment = new RecipeEditDetailFragment();
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
        View view =  inflater.inflate(R.layout.fragment_recipe_edit_detail, container, false);
        ImageView imageHolder = view.findViewById(R.id.imageHolder);
        TextView nameHolder = view.findViewById(R.id.nameHolder);
        TextView recipeNameHolder = view.findViewById(R.id.recipeNameHolder);
        TextView categoryHolder = view.findViewById(R.id.categoryHolder);
        TextView recipeInfoHolder = view.findViewById(R.id.recipeInfoHolder);
        TextView calorieHolder = view.findViewById(R.id.calorieHolder);
        TextView recipeIngredientHolder = view.findViewById(R.id.recipeIngredientHolder);
        TextView step1Holder = view.findViewById(R.id.step1Holder);
        TextView step2Holder = view.findViewById(R.id.step2Holder);
        TextView step3Holder = view.findViewById(R.id.step3Holder);
        TextView step4Holder = view.findViewById(R.id.step4Holder);
        TextView step5Holder = view.findViewById(R.id.step5Holder);
        TextView step6Holder = view.findViewById(R.id.step6Holder);
        TextView step7Holder = view.findViewById(R.id.step7Holder);
        TextView step8Holder = view.findViewById(R.id.step8Holder);
        TextView step9Holder = view.findViewById(R.id.step9Holder);
        TextView step10Holder = view.findViewById(R.id.step10Holder);
        ImageView imageStep1Holder = view.findViewById(R.id.imageStep1Holder);
        ImageView imageStep2Holder = view.findViewById(R.id.imageStep2Holder);
        ImageView imageStep3Holder = view.findViewById(R.id.imageStep3Holder);
        ImageView imageStep4Holder = view.findViewById(R.id.imageStep4Holder);
        ImageView imageStep5Holder = view.findViewById(R.id.imageStep5Holder);
        ImageView imageStep6Holder = view.findViewById(R.id.imageStep6Holder);
        ImageView imageStep7Holder = view.findViewById(R.id.imageStep7Holder);
        ImageView imageStep8Holder = view.findViewById(R.id.imageStep8Holder);
        ImageView imageStep9Holder = view.findViewById(R.id.imageStep9Holder);
        ImageView imageStep10Holder = view.findViewById(R.id.imageStep10Holder);
        nameHolder.setText(name);
        recipeNameHolder.setText(recipeName);
        categoryHolder.setText(category);
        recipeInfoHolder.setText(recipeInfo);
        calorieHolder.setText(String.valueOf(calorie));
        recipeIngredientHolder.setText(recipeIngredient);
        step1Holder.setText(step1);
        step2Holder.setText(step2);
        step3Holder.setText(step3);
        step4Holder.setText(step4);
        step5Holder.setText(step5);
        step6Holder.setText(step6);
        step7Holder.setText(step7);
        step8Holder.setText(step8);
        step9Holder.setText(step9);
        step10Holder.setText(step10);
        Glide.with(getContext()).load(purl).into(imageHolder);
        Glide.with(getContext()).load(imageStep1).into(imageStep1Holder);
        Glide.with(getContext()).load(imageStep2).into(imageStep2Holder);
        Glide.with(getContext()).load(imageStep3).into(imageStep3Holder);
        Glide.with(getContext()).load(imageStep4).into(imageStep4Holder);
        Glide.with(getContext()).load(imageStep5).into(imageStep5Holder);
        Glide.with(getContext()).load(imageStep6).into(imageStep6Holder);
        Glide.with(getContext()).load(imageStep7).into(imageStep7Holder);
        Glide.with(getContext()).load(imageStep8).into(imageStep8Holder);
        Glide.with(getContext()).load(imageStep9).into(imageStep9Holder);
        Glide.with(getContext()).load(imageStep10).into(imageStep10Holder);


        return view;
    }



}