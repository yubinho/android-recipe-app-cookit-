package com.example.cookit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class FridgeAdapter extends FirebaseRecyclerAdapter<Recipe, FridgeAdapter.myviewholder> {
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String userFridge,leftOver;
    private Activity activity;

    public FridgeAdapter(@NonNull FirebaseRecyclerOptions<Recipe> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull FridgeAdapter.myviewholder holder, int position, @NonNull Recipe model) {

        FirebaseDatabase.getInstance().getReference("Fridge").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isMatched = false;
                Fridge fridge = snapshot.getValue(Fridge.class);
                if(fridge != null){
                    leftOver = fridge.leftOver.trim();
                    ArrayList<String> fridgeList = new ArrayList<>();
                    ArrayList<String> ingredientList = new ArrayList<>();
                    //TODO:目前冰箱食材
                    if(!(leftOver.equals("") || leftOver == null || leftOver.equals("null"))){
                        String[] fridgeSplit = leftOver.split(",");
                        for(String list : fridgeSplit) {
                            String fridgeIngredientName = list.split(":")[0];
                            fridgeList.add(fridgeIngredientName);
                        }
                    }
                    //TODO:食譜食材
                    String[] ingredients = model.getRecipeIngredient().split(",");
                    for(String ingredient:ingredients){
                        String ingredientName = ingredient.split(":")[0];
                        ingredientList.add(ingredientName);
                    }
                    //TODO:判斷
                    for(String fridgeIngredient : fridgeList){
                        if(ingredientList.contains(fridgeIngredient)){
                            isMatched = true;
                        }
                    }
                    if(isMatched){
                        holder.recipeName.setText(model.getRecipeName());
                        holder.publisher.setText(model.getName());
                        holder.category.setText(model.getCategory());
                        holder.chooseButton.setImageResource(R.drawable.ic_choose);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getPurl()).into(holder.recipeUrl);
                    }else{
                        holder.recipeName.setText(model.getRecipeName());
                        holder.publisher.setText(model.getName());
                        holder.category.setText(model.getCategory());
                        holder.chooseButton.setImageResource(R.drawable.ic_check);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getPurl()).into(holder.recipeUrl);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String recipeKey = getRef(position).getKey();
        //TODO:食譜細節
        holder.recipeUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),RecipeDetailActivity.class);
                intent.putExtra("name",model.getName());
                intent.putExtra("recipeName",model.getRecipeName());
                intent.putExtra("recipeInfo",model.getRecipeInfo());
                intent.putExtra("category",model.getCategory());
                intent.putExtra("purl",model.getPurl());
                intent.putExtra("ingredient",model.getRecipeIngredient());
                intent.putExtra("step1",model.getStep1());
                intent.putExtra("step2",model.getStep2());
                intent.putExtra("step3",model.getStep3());
                intent.putExtra("step4",model.getStep4());
                intent.putExtra("step5",model.getStep5());
                intent.putExtra("step6",model.getStep6());
                intent.putExtra("step7",model.getStep7());
                intent.putExtra("step8",model.getStep8());
                intent.putExtra("step9",model.getStep9());
                intent.putExtra("step10",model.getStep10());
                intent.putExtra("imageStep1",model.getImageStep1());
                intent.putExtra("imageStep2",model.getImageStep2());
                intent.putExtra("imageStep3",model.getImageStep3());
                intent.putExtra("imageStep4",model.getImageStep4());
                intent.putExtra("imageStep5",model.getImageStep5());
                intent.putExtra("imageStep6",model.getImageStep6());
                intent.putExtra("imageStep7",model.getImageStep7());
                intent.putExtra("imageStep8",model.getImageStep8());
                intent.putExtra("imageStep9",model.getImageStep9());
                intent.putExtra("imageStep10",model.getImageStep10());
                intent.putExtra("calorie",model.getCalorie());
                intent.putExtra("clickCount",model.getClickCount());
                intent.putExtra("recipeKey",recipeKey);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });
        //TODO:刪減食材
        holder.chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Fridge").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Fridge fridge = snapshot.getValue(Fridge.class);
                        HashMap<String,Double> fridgeHashMap = new HashMap<>();
                        HashMap<String,Double> ingredientHashMap = new HashMap<>();
                        HashMap<String,Double> lackHashMap = new HashMap<>();
                        Map<String,Object> map = new HashMap<>();
                        boolean isMatch = false;
                        String leftOver = "";

                        if(fridge != null){
                            userFridge = fridge.leftOver.trim();
                        }
                        //TODO:目前冰箱食材HashMap
                        if(!(userFridge.equals("") || userFridge == null || userFridge.equals("null"))){
                            String[] fridgeList = userFridge.split(",");
                            for(String list : fridgeList){
                                String fridgeIngredientName = list.split(":")[0];
                                String fridgeIngredientWeightString = list.split(":")[1];
                                double fridgeIngredientWeight = Double.valueOf(fridgeIngredientWeightString.replace("g",""));
                                fridgeHashMap.put(fridgeIngredientName,fridgeIngredientWeight);
                            }
                        }
                        //TODO:食譜食材HashMap
                        String[] ingredients = model.getRecipeIngredient().split(",");
                        for(String ingredient:ingredients){
                            String ingredientName = ingredient.split(":")[0];
                            String ingredientWeightString = ingredient.split(":")[1];
                            if(!ingredientWeightString.equals("少許")){
                                double ingredientWeight = Double.valueOf(ingredientWeightString.replace("g",""));
                                ingredientHashMap.put(ingredientName,ingredientWeight);
                            }
                        }
                        //TODO:檢查菜單與冰箱
                        for(String ingredient:ingredientHashMap.keySet()) {
                            double ingredientWeight = ingredientHashMap.get(ingredient);
                            double fridgeIngredientWeight = 0.0;
                            if(fridgeHashMap.get(ingredient) != null){
                                fridgeIngredientWeight = fridgeHashMap.get(ingredient);
                            }
                            if (fridgeHashMap.containsKey(ingredient)){
                                if (ingredientWeight > fridgeIngredientWeight) {
                                    double lack = ingredientWeight - fridgeIngredientWeight;
                                    lackHashMap.put(ingredient, lack);
                                } else if (ingredientWeight == fridgeIngredientWeight) {
                                    fridgeHashMap.remove(ingredient);
                                } else if (ingredientWeight < fridgeIngredientWeight) {
                                    double left = fridgeIngredientWeight - ingredientWeight;
                                    fridgeHashMap.replace(ingredient,left);
                                }
                            }else{
                                if(!lackHashMap.containsKey(ingredient)) {
                                    lackHashMap.put(ingredient, ingredientWeight);
                                }
                            }
                        }
                        //TODO:判斷是否配對成功
                        if(lackHashMap.isEmpty()){
                            isMatch = true;
                        }

                        //TODO:成功配對則更新資料庫
                        if(isMatch){
                            //TODO:冰箱食材 (用於View)
                            for(String fridgeLeftOver:fridgeHashMap.keySet()){
                                leftOver += fridgeLeftOver+":"+fridgeHashMap.get(fridgeLeftOver)+"g"+",";
                            }
                            if(leftOver != null && !leftOver.equals("")) {
                                leftOver = leftOver.substring(0, leftOver.length() - 1);
                            }
                            map.put("leftOver",leftOver);
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.chooseButton.getContext());
                            alertDialog.setTitle("系統訊息");
                            alertDialog.setMessage("您當前的食材足夠製作這道料理，確定要選擇這道料理嗎?");
                            alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference("Fridge").child(userId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dialog.dismiss();
                                            activity.recreate();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                        }
                                    });

                                }
                            });
                            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }else{
                            String message = "";
                            for (String lack:lackHashMap.keySet()){
                                message += lack+":"+lackHashMap.get(lack)+"g"+",";
                            }
                            message = message.substring(0,message.length()-1);
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.chooseButton.getContext());
                            alertDialog.setTitle("系統訊息");
                            alertDialog.setMessage("你還缺少以下食材\n"+message);
                            alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fridge_check_singlerow,parent,false);
        return new myviewholder(view);
    }
    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView recipeUrl,chooseButton;
        TextView recipeName,publisher,category;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            recipeUrl = itemView.findViewById(R.id.sr_recipeUrl);
            recipeName = itemView.findViewById(R.id.sr_recipe_name);
            publisher = itemView.findViewById(R.id.sr_publisher);
            category = itemView.findViewById(R.id.sr_category);
            chooseButton = itemView.findViewById(R.id.imgView_fridgeCheck);

        }
    }

}
