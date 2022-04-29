package com.example.cookit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class RecipeDetailActivity extends AppCompatActivity {
    ImageView imageHolder,imageStep1Holder,imageStep2Holder,imageStep3Holder,imageStep4Holder,imageStep5Holder,imageStep6Holder,imageStep7Holder,imageStep8Holder,imageStep9Holder,imageStep10Holder;
    TextView nameHolder,recipeNameHolder,categoryHolder,recipeInfoHolder,calorieHolder,recipeIngredientHolder,clickCountHolder;
    TextView step1Holder,step2Holder,step3Holder,step4Holder,step5Holder,step6Holder,step7Holder,step8Holder,step9Holder,step10Holder;
    int clickCount;
    TextToSpeech textToSpeech;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String name,recipeName,category,purl,recipeInfo,recipeIngredient,step1,step2,step3,step4,step5,step6,step7,step8,step9,step10;
    private String imageStep1,imageStep2,imageStep3,imageStep4,imageStep5,imageStep6,imageStep7,imageStep8,imageStep9,imageStep10;
    String recipeKey;
    ImageButton btn_textToSpeech,btn_fav;
    Button btn_toMessage;
    double calorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        initView();
        getData();
        setText();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //TODO:收藏
        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checked = "yes";
                Recipe recipe = new Recipe(name, category, recipeName, recipeInfo, recipeIngredient,
                        step1, step2, step3, step4, step5, step6, step7, step8, step9, step10, purl, userId
                        ,imageStep1,imageStep2,imageStep3,imageStep4,imageStep5,imageStep6,imageStep7,imageStep8,imageStep9,imageStep10,checked,calorie,clickCount);
                FirebaseDatabase.getInstance().getReference("favorite").child(userId).child(recipeName)
                        .setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RecipeDetailActivity.this,"已新增至我的最愛",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        //TODO:語音敘述
        textToSpeech = new TextToSpeech(RecipeDetailActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.TRADITIONAL_CHINESE);
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(RecipeDetailActivity.this,"不支援此語言",Toast.LENGTH_LONG).show();
                    }else {
                        //btn_textToSpeech.setEnabled(true);
                        textToSpeech.setPitch(1.0f);
                        textToSpeech.setSpeechRate(1.0f);
                        //speak();
                    }
                }
            }
        });

        btn_textToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();

            }
        });
        //TODO:留言區
        btn_toMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetailActivity.this,MessageBoardActivity.class);
                intent.putExtra("recipeKey",recipeKey);
                intent.putExtra("recipeName",recipeName);
                startActivity(intent);
            }
        });

    }

    private void speak() {
        if(step1 != null && step1.length() != 0){
            textToSpeech.speak("步驟一 "+step1,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step2 != null && step2.length() != 0){
            textToSpeech.speak("步驟二 "+step2,TextToSpeech.QUEUE_ADD,null);;
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step3 != null && step3.length() != 0){
            textToSpeech.speak("步驟三 "+step3,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step4 != null && step4.length() != 0){
            textToSpeech.speak("步驟四 "+step4,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step5 != null && step5.length() != 0){
            textToSpeech.speak("步驟五"+step5,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step6 != null && step6.length() != 0){
            textToSpeech.speak("步驟六"+step6,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step7 != null && step7.length() != 0){
            textToSpeech.speak("步驟七"+step7,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step8 != null && step8.length() != 0){
            textToSpeech.speak("步驟八"+step8,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step9 != null && step9.length() != 0){
            textToSpeech.speak("步驟九"+step9,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
        if(step10 != null && step10.length() != 0){
            textToSpeech.speak("步驟十"+step10,TextToSpeech.QUEUE_ADD,null);
            textToSpeech.playSilentUtterance(500,TextToSpeech.QUEUE_ADD,null);
        }
    }

    public void initView(){
        btn_textToSpeech = findViewById(R.id.btn_textToSpeech);
        btn_fav = findViewById(R.id.btn_fav);
        imageHolder = findViewById(R.id.imageHolder);
        nameHolder = findViewById(R.id.nameHolder);
        recipeNameHolder = findViewById(R.id.recipeNameHolder);
        categoryHolder = findViewById(R.id.categoryHolder);
        recipeInfoHolder = findViewById(R.id.recipeInfoHolder);
        calorieHolder = findViewById(R.id.calorieHolder);
        recipeIngredientHolder = findViewById(R.id.recipeIngredientHolder);
        step1Holder = findViewById(R.id.step1Holder);
        step2Holder = findViewById(R.id.step2Holder);
        step3Holder = findViewById(R.id.step3Holder);
        step4Holder = findViewById(R.id.step4Holder);
        step5Holder = findViewById(R.id.step5Holder);
        step6Holder = findViewById(R.id.step6Holder);
        step7Holder = findViewById(R.id.step7Holder);
        step8Holder = findViewById(R.id.step8Holder);
        step9Holder = findViewById(R.id.step9Holder);
        step10Holder = findViewById(R.id.step10Holder);
        imageStep1Holder = findViewById(R.id.imageStep1Holder);
        imageStep2Holder = findViewById(R.id.imageStep2Holder);
        imageStep3Holder = findViewById(R.id.imageStep3Holder);
        imageStep4Holder = findViewById(R.id.imageStep4Holder);
        imageStep5Holder = findViewById(R.id.imageStep5Holder);
        imageStep6Holder = findViewById(R.id.imageStep6Holder);
        imageStep7Holder = findViewById(R.id.imageStep7Holder);
        imageStep8Holder = findViewById(R.id.imageStep8Holder);
        imageStep9Holder = findViewById(R.id.imageStep9Holder);
        imageStep10Holder = findViewById(R.id.imageStep10Holder);
        clickCountHolder = findViewById(R.id.clickCount);
        btn_toMessage = findViewById(R.id.btn_toMessage);
    }
    public void getData(){
        name = getIntent().getStringExtra("name");
        recipeName = getIntent().getStringExtra("recipeName");
        category = getIntent().getStringExtra("category");
        recipeInfo = getIntent().getStringExtra("recipeInfo");
        recipeIngredient = getIntent().getStringExtra("ingredient");
        purl = getIntent().getStringExtra("purl");
        step1 = getIntent().getStringExtra("step1");
        step2 = getIntent().getStringExtra("step2");
        step3 = getIntent().getStringExtra("step3");
        step4 = getIntent().getStringExtra("step4");
        step5 = getIntent().getStringExtra("step5");
        step6 = getIntent().getStringExtra("step6");
        step7 = getIntent().getStringExtra("step7");
        step8 = getIntent().getStringExtra("step8");
        step9 = getIntent().getStringExtra("step9");
        step10 = getIntent().getStringExtra("step10");
        imageStep1 = getIntent().getStringExtra("imageStep1");
        imageStep2 = getIntent().getStringExtra("imageStep2");
        imageStep3 = getIntent().getStringExtra("imageStep3");
        imageStep4 = getIntent().getStringExtra("imageStep4");
        imageStep5 = getIntent().getStringExtra("imageStep5");
        imageStep6 = getIntent().getStringExtra("imageStep6");
        imageStep7 = getIntent().getStringExtra("imageStep7");
        imageStep8 = getIntent().getStringExtra("imageStep8");
        imageStep9 = getIntent().getStringExtra("imageStep9");
        imageStep10 = getIntent().getStringExtra("imageStep10");
        calorie = getIntent().getDoubleExtra("calorie",0);
        clickCount = getIntent().getIntExtra("clickCount",0);
        //
        recipeKey = getIntent().getStringExtra("recipeKey");
    }
    public void setText(){
        nameHolder.setText(name);
        recipeNameHolder.setText(recipeName);
        categoryHolder.setText(category);
        recipeInfoHolder.setText(recipeInfo);
        calorieHolder.setText(String.valueOf(calorie));
        recipeIngredientHolder.setText(recipeIngredient);
        clickCountHolder.setText(String.valueOf(clickCount));
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
        Glide.with(this).load(purl).into(imageHolder);
        Glide.with(this).load(imageStep1).into(imageStep1Holder);
        Glide.with(this).load(imageStep2).into(imageStep2Holder);
        Glide.with(this).load(imageStep3).into(imageStep3Holder);
        Glide.with(this).load(imageStep4).into(imageStep4Holder);
        Glide.with(this).load(imageStep5).into(imageStep5Holder);
        Glide.with(this).load(imageStep6).into(imageStep6Holder);
        Glide.with(this).load(imageStep7).into(imageStep7Holder);
        Glide.with(this).load(imageStep8).into(imageStep8Holder);
        Glide.with(this).load(imageStep9).into(imageStep9Holder);
        Glide.with(this).load(imageStep10).into(imageStep10Holder);
    }

    @Override
    protected void onDestroy() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}