package com.example.cookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomizeMenuAdapter extends FirebaseRecyclerAdapter<Recipe, CustomizeMenuAdapter.myviewholder> {
    String date,time,category;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    //getRef(position).getKey()
    public CustomizeMenuAdapter(@NonNull FirebaseRecyclerOptions<Recipe> options,String date,String time,String category){
        super(options);
        this.date = date;
        this.time = time;
        this.category = category;
    }
    @Override
    protected void onBindViewHolder(@NonNull CustomizeMenuAdapter.myviewholder holder, int position, @NonNull Recipe model) {
        if(category.equals(model.getCategory())){
            holder.recipeName.setText(model.getRecipeName());
            holder.publisher.setText(model.getName());
            holder.category.setText(model.getCalorie()+"大卡");
            holder.menuChosen.setImageResource(R.drawable.ic_choose);
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
            //TODO:食譜確認
            holder.menuChosen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String randomKey = UUID.randomUUID().toString();
                    String index = date+"-"+time;
                    Map<String,Object> map = new HashMap<>();
                    double calorie = Double.valueOf(model.getCalorie());
                    map.put("name", model.getName());
                    map.put("recipeName",model.getRecipeName());
                    map.put("recipeInfo",model.getRecipeInfo());
                    map.put("calorie", calorie);
                    map.put("recipeIngredient",model.getRecipeIngredient());
                    map.put("step1",model.getStep1());
                    map.put("step2",model.getStep2());
                    map.put("step3",model.getStep3());
                    map.put("step4",model.getStep4());
                    map.put("step5",model.getStep5());
                    map.put("step6",model.getStep6());
                    map.put("step7",model.getStep7());
                    map.put("step8",model.getStep8());
                    map.put("step9",model.getStep9());
                    map.put("step10",model.getStep10());
                    map.put("purl", model.getPurl());
                    map.put("imageStep1",model.getImageStep1());
                    map.put("imageStep2",model.getImageStep2());
                    map.put("imageStep3",model.getImageStep3());
                    map.put("imageStep4",model.getImageStep4());
                    map.put("imageStep5",model.getImageStep5());
                    map.put("imageStep6",model.getImageStep6());
                    map.put("imageStep7",model.getImageStep7());
                    map.put("imageStep8",model.getImageStep8());
                    map.put("imageStep9",model.getImageStep9());
                    map.put("imageStep10",model.getImageStep10());
                    map.put("checked",model.getChecked());
                    map.put("category",model.getCategory());
                    map.put("date",date);
                    map.put("time",time);
                    map.put("clickCount",model.getClickCount());
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.menuChosen.getContext());
                    alertDialog.setTitle("系統訊息");
                    alertDialog.setMessage("客製化菜單新增成功");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference("customMenu").child(userId).child(time).child(randomKey).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                        }
                    });
                    alertDialog.show();

                }
            });
        }

    }

    @NonNull
    @Override
    public CustomizeMenuAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_choose_singlerow,parent,false);
        return new myviewholder(view);
    }
    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView recipeUrl,menuChosen;
        TextView recipeName,publisher,category;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            recipeUrl = itemView.findViewById(R.id.sr_recipeUrl);
            recipeName = itemView.findViewById(R.id.sr_recipe_name);
            publisher = itemView.findViewById(R.id.sr_publisher);
            category = itemView.findViewById(R.id.sr_category);
            menuChosen = itemView.findViewById(R.id.imgView_menuChosen);
        }
    }
}
