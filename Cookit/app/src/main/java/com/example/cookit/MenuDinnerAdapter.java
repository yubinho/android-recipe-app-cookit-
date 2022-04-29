package com.example.cookit;

import android.app.Activity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MenuDinnerAdapter extends FirebaseRecyclerAdapter<Menu, MenuDinnerAdapter.myviewholder> {
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Activity activity;
    public MenuDinnerAdapter(@NonNull FirebaseRecyclerOptions<Menu> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Menu model) {
        holder.recipeName.setText(model.getRecipeName());
        holder.time.setText(model.getDate());
        holder.category.setText(model.getCategory());
        Glide.with(holder.recipeUrl.getContext()).load(model.getPurl()).into(holder.recipeUrl);
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
        //TODO:刪除
        holder.recipeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.recipeDelete.getContext());
                alertDialog.setTitle("系統訊息");
                alertDialog.setMessage("確定要刪除嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            FirebaseDatabase.getInstance().getReference("customMenu").child(userId).child("晚餐")
                                    .child(getRef(position).getKey()).removeValue();
                            activity.recreate();
                        }catch (IndexOutOfBoundsException e) {
                            e.getMessage();
                        }
                    }
                });
                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
    }

    @NonNull
    @Override
    public MenuDinnerAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_singlerow,parent,false);
        return new MenuDinnerAdapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView recipeUrl,recipeDelete;
        TextView recipeName,time,category;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            recipeUrl = itemView.findViewById(R.id.srMenu_recipeIcon);
            recipeName = itemView.findViewById(R.id.srMenu_recipe_name);
            time = itemView.findViewById(R.id.srMenu_time);
            category = itemView.findViewById(R.id.srMenu_category);
            recipeDelete = itemView.findViewById(R.id.srMenu_delete);
        }
    }
}
