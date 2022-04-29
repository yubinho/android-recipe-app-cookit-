package com.example.cookit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class IndexPopsAdapter extends FirebaseRecyclerAdapter<Recipe, IndexPopsAdapter.myviewholder> {
    int clickCount,order;
    int orderMax = 999999999;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID;
    String check = "yes";
    public IndexPopsAdapter(@NonNull FirebaseRecyclerOptions<Recipe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Recipe model) {
        if(user != null){
            userID = user.getUid();
        }
        if(model.getChecked().equals(check)){
            holder.recipeName.setText(model.getRecipeName());
            holder.publisher.setText(model.getName());
            holder.category.setText(model.getCategory());
            Glide.with(holder.recipeUrl.getContext()).load(model.getPurl()).into(holder.recipeUrl);
            String recipeKey = getRef(position).getKey();
            holder.recipeUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    //TODO:點擊率
                    Map<String,Object> map = new HashMap<>();
                    clickCount = model.getClickCount() + 1;
                    order = orderMax - clickCount;
                    map.put("clickCount",clickCount);
                    map.put("order",order);
                    FirebaseDatabase.getInstance().getReference("Recipes")
                            .child(getRef(position).getKey()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                    //TODO:歷史紀錄
                    if(user != null){
                        Recipe recipe = new Recipe(model.getName(), model.getCategory(), model.getRecipeName(), model.getRecipeInfo(), model.getRecipeIngredient(),
                                model.getStep1(), model.getStep2(), model.getStep3(), model.getStep4(), model.getStep5(), model.getStep6(), model.getStep7(), model.getStep8(), model.getStep9(), model.getStep10(), model.getPurl(), userID
                                ,model.getImageStep1(),model.getImageStep2(),model.getImageStep3(),model.getImageStep4(),model.getImageStep5(),model.getImageStep6(),model.getImageStep7(),model.getImageStep8(),model.getImageStep9(),model.getImageStep10(),model.getChecked(),model.getCalorie(),clickCount);
                        FirebaseDatabase.getInstance().getReference("History").child(userID)
                                .child(getRef(position).getKey()).setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }



                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.LinearLayout_nav,new RecipeDetailFragment(model.getName(),model.getRecipeName(),model.getCategory(), model.getPurl(),model.getRecipeInfo(),model.getRecipeIngredient(),
                                    model.getStep1(),model.getStep2(),model.getStep3(),model.getStep4(),model.getStep5(),model.getStep6(),model.getStep7(),model.getStep8(),model.getStep9(),model.getStep10()
                                    ,model.getImageStep1(),model.getImageStep2(),model.getImageStep3(),model.getImageStep4(),model.getImageStep5()
                                    ,model.getImageStep6(),model.getImageStep7(),model.getImageStep8(),model.getImageStep9(),model.getImageStep10(),model.getCalorie(),clickCount,recipeKey))
                            .addToBackStack(null).commit();
                }
            });
        }

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowlayout,parent,false);
        return new IndexPopsAdapter.myviewholder(view);
    }


    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView recipeUrl;
        TextView recipeName,publisher,category;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            recipeUrl = itemView.findViewById(R.id.sr_recipeUrl);
            recipeName = itemView.findViewById(R.id.sr_recipe_name);
            publisher = itemView.findViewById(R.id.sr_publisher);
            category = itemView.findViewById(R.id.sr_category);
        }
    }
}
