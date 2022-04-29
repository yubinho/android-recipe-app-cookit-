package com.example.cookit;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FavoriteAdapter extends FirebaseRecyclerAdapter<Recipe, FavoriteAdapter.myviewholder> {
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public FavoriteAdapter(@NonNull FirebaseRecyclerOptions<Recipe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Recipe model) {
        holder.recipeName.setText(model.getRecipeName());
        holder.publisher.setText(model.getName());
        holder.category.setText(model.getCategory());
        Glide.with(holder.recipeUrl.getContext()).load(model.getPurl()).into(holder.recipeUrl);
        String recipeKey = getRef(position).getKey();
        holder.recipeUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.LinearLayout_nav,new RecipeDetailFragment(model.getName(),model.getRecipeName(),model.getCategory(), model.getPurl(),model.getRecipeInfo(),model.getRecipeIngredient(),
                                model.getStep1(),model.getStep2(),model.getStep3(),model.getStep4(),model.getStep5(),model.getStep6(),model.getStep7(),model.getStep8(),model.getStep9(),model.getStep10()
                                ,model.getImageStep1(),model.getImageStep2(),model.getImageStep3(),model.getImageStep4(),model.getImageStep5()
                                ,model.getImageStep6(),model.getImageStep7(),model.getImageStep8(),model.getImageStep9(),model.getImageStep10(),model.getCalorie(),model.getClickCount(),recipeKey))
                        .addToBackStack(null).commit();
            }
        });
        //TODO:刪除
        holder.recipeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.recipeDelete.getContext());
                alertDialog.setTitle("系統訊息");
                alertDialog.setMessage("確定要取消收藏嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            FirebaseDatabase.getInstance().getReference("favorite").child(userId)
                                    .child(getRef(position).getKey()).removeValue();
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
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_favorite_singlerow,parent,false);
        return new FavoriteAdapter.myviewholder(view);
    }
    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView recipeUrl,recipeDelete;
        TextView recipeName,publisher,category;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            recipeUrl = itemView.findViewById(R.id.sr_recipeUrl);
            recipeName = itemView.findViewById(R.id.sr_recipe_name);
            publisher = itemView.findViewById(R.id.sr_publisher);
            category = itemView.findViewById(R.id.sr_category);
            recipeDelete = itemView.findViewById(R.id.srRecipe_delete);
        }
    }
}
