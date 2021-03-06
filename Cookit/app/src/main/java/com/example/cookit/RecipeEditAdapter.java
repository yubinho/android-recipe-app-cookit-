package com.example.cookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RecipeEditAdapter extends FirebaseRecyclerAdapter<Recipe, RecipeEditAdapter.myviewholder> {

    public RecipeEditAdapter(@NonNull FirebaseRecyclerOptions<Recipe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Recipe model) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        holder.recipeName.setText(model.getRecipeName());
        holder.publisher.setText(model.getName());
        holder.category.setText(model.getCategory());
        Glide.with(holder.recipeUrl.getContext()).load(model.getPurl()).into(holder.recipeUrl);
        holder.recipeUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.LinearLayout_nav,new RecipeEditDetailFragment(model.getName(),model.getRecipeName(),model.getCategory(), model.getPurl(),model.getRecipeInfo(),model.getRecipeIngredient(),
                                model.getStep1(),model.getStep2(),model.getStep3(),model.getStep4(),model.getStep5(),model.getStep6(),model.getStep7(),model.getStep8(),model.getStep9(),model.getStep10()
                                ,model.getImageStep1(),model.getImageStep2(),model.getImageStep3(),model.getImageStep4(),model.getImageStep5()
                                ,model.getImageStep6(),model.getImageStep7(),model.getImageStep8(),model.getImageStep9(),model.getImageStep10(),model.getCalorie()))
                        .addToBackStack(null).commit();
            }
        });
        //TODO:??????
        holder.recipeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.recipeUrl.getContext())
                        .setContentHolder(new ViewHolder(R.layout.recipe_edit_dialog_content))
                        .setExpanded(true)
                        .create();
                View myView = dialogPlus.getHolderView();

                FirebaseDatabase.getInstance().getReference("Ingredient").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        /****???????????????****/
                        double calorie = 0.0;
                        Ingredient ingredientData = snapshot.getValue(Ingredient.class);
                        String[] ingredients = model.getRecipeIngredient().split(",");
                        for(String ingredient : ingredients){
                            int ingredientCal = 0;
                            String[] ingredientIndex = ingredient.split(":");
                            String ingredientName = ingredientIndex[0];
                            String ingredientWeight = ingredientIndex[1];
                            if(!ingredientWeight.equals("??????")){
                                int ingredientInt = Integer.valueOf(ingredientWeight.replace("g",""));
                                double ingredientDouble = Double.valueOf(ingredientWeight.replace("g",""));
                                double magnification = ingredientInt/100+ingredientDouble%100/100;
                                switch (ingredientName){
                                    case "????????????":
                                        ingredientCal = ingredientData.cabbage;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.chickenBone;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.fishStock;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.clams;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.snapperFillet;
                                        break;
                                    case "???":
                                        ingredientCal = ingredientData.shrimp;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.celery;
                                        break;
                                    case "????????????":
                                        ingredientCal = ingredientData.soySauce;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.soySauce;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.riceWine;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.pepper;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.cornstarch;
                                        break;
                                    case "????????????":
                                        ingredientCal = ingredientData.beanSauce;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.pork;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.tofu;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.chili;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.sesameOil;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.bitterGround;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.saltedEgg;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.blackVinegar;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.strawMushroom;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.enokiMushroom;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.fungus;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.soySauce;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.riceWine;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.pork;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.carrot;
                                        break;
                                    case "?????????":
                                        ingredientCal = ingredientData.potato;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.beanDrum;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.chive;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.driedTofu;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.sugar;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.salt;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.egg;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.tomato;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.eggplant;
                                        break;
                                    case "???":
                                        ingredientCal = ingredientData.shallots;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.shallots;
                                        break;
                                    case "???":
                                        ingredientCal = ingredientData.ginger;
                                        break;
                                    case "???":
                                        ingredientCal = ingredientData.garlic;
                                        break;
                                    case "??????":
                                        ingredientCal = ingredientData.garlic;
                                        break;
                                }
                                calorie += ingredientCal*magnification;
                            }
                        }
                        double finalCalorie = calorie;
                        /*********************/
                        Spinner spinner = myView.findViewById(R.id.recipeEditChooseType);
                        ImageView recipeCoverHolder = myView.findViewById(R.id.imageCoverHolder);
                        ImageView recipeImageStep1Holder = myView.findViewById(R.id.imageStep1Holder);
                        ImageView recipeImageStep2Holder = myView.findViewById(R.id.imageStep2Holder);
                        ImageView recipeImageStep3Holder = myView.findViewById(R.id.imageStep3Holder);
                        ImageView recipeImageStep4Holder = myView.findViewById(R.id.imageStep4Holder);
                        ImageView recipeImageStep5Holder = myView.findViewById(R.id.imageStep5Holder);
                        ImageView recipeImageStep6Holder = myView.findViewById(R.id.imageStep6Holder);
                        ImageView recipeImageStep7Holder = myView.findViewById(R.id.imageStep7Holder);
                        ImageView recipeImageStep8Holder = myView.findViewById(R.id.imageStep8Holder);
                        ImageView recipeImageStep9Holder = myView.findViewById(R.id.imageStep9Holder);
                        ImageView recipeImageStep10Holder = myView.findViewById(R.id.imageStep10Holder);
                        EditText recipeName = myView.findViewById(R.id.updateRecipeName);
                        TextView recipeCategory = myView.findViewById(R.id.recipeCurrentType);
                        EditText recipeInfo = myView.findViewById(R.id.updateRecipeInfo);
                        TextView ingredientCalorie = myView.findViewById(R.id.updateRecipeCalorie);
                        EditText recipeIngredient = myView.findViewById(R.id.updateRecipeIngredient);
                        EditText recipeStep1 = myView.findViewById(R.id.updateStep1);
                        EditText recipeStep2 = myView.findViewById(R.id.updateStep2);
                        EditText recipeStep3 = myView.findViewById(R.id.updateStep3);
                        EditText recipeStep4 = myView.findViewById(R.id.updateStep4);
                        EditText recipeStep5 = myView.findViewById(R.id.updateStep5);
                        EditText recipeStep6 = myView.findViewById(R.id.updateStep6);
                        EditText recipeStep7 = myView.findViewById(R.id.updateStep7);
                        EditText recipeStep8 = myView.findViewById(R.id.updateStep8);
                        EditText recipeStep9 = myView.findViewById(R.id.updateStep9);
                        EditText recipeStep10 = myView.findViewById(R.id.updateStep10);
                        Button submit = myView.findViewById(R.id.btn_confirmUpdate);
                        String checked = model.getChecked();
                        recipeCategory.setText(model.getCategory());
                        recipeName.setText(model.getRecipeName());
                        recipeInfo.setText(model.getRecipeInfo());
                        ingredientCalorie.setText(String.valueOf(finalCalorie));
                        recipeIngredient.setText(model.getRecipeIngredient());
                        recipeStep1.setText(model.getStep1());
                        recipeStep2.setText(model.getStep2());
                        recipeStep3.setText(model.getStep3());
                        recipeStep4.setText(model.getStep4());
                        recipeStep5.setText(model.getStep5());
                        recipeStep6.setText(model.getStep6());
                        recipeStep7.setText(model.getStep7());
                        recipeStep8.setText(model.getStep8());
                        recipeStep9.setText(model.getStep9());
                        recipeStep10.setText(model.getStep10());
                        Glide.with(holder.recipeUrl.getContext()).load(model.getPurl()).into(recipeCoverHolder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep1()).into(recipeImageStep1Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep2()).into(recipeImageStep2Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep3()).into(recipeImageStep3Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep4()).into(recipeImageStep4Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep5()).into(recipeImageStep5Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep6()).into(recipeImageStep6Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep7()).into(recipeImageStep7Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep8()).into(recipeImageStep8Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep9()).into(recipeImageStep9Holder);
                        Glide.with(holder.recipeUrl.getContext()).load(model.getImageStep10()).into(recipeImageStep10Holder);
                        dialogPlus.show();
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,Object> map = new HashMap<>();
                                String[] categoryArray = myView.getResources().getStringArray(R.array.type);
                                int indexOfCategoryArray = spinner.getSelectedItemPosition();
                                String category =  categoryArray[indexOfCategoryArray];
                                map.put("recipeName",recipeName.getText().toString());
                                map.put("recipeInfo",recipeInfo.getText().toString());
                                map.put("calorie", finalCalorie);
                                map.put("recipeIngredient",recipeIngredient.getText().toString());
                                map.put("step1",recipeStep1.getText().toString());
                                map.put("step2",recipeStep2.getText().toString());
                                map.put("step3",recipeStep3.getText().toString());
                                map.put("step4",recipeStep4.getText().toString());
                                map.put("step5",recipeStep5.getText().toString());
                                map.put("step6",recipeStep6.getText().toString());
                                map.put("step7",recipeStep7.getText().toString());
                                map.put("step8",recipeStep8.getText().toString());
                                map.put("step9",recipeStep9.getText().toString());
                                map.put("step10",recipeStep10.getText().toString());
                                map.put("checked",checked);
                                map.put("category",category);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.recipeUpdate.getContext());
                                alertDialog.setTitle("????????????");
                                alertDialog.setMessage("???????????????????");
                                alertDialog.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference("Recipes")
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }
                                });
                                alertDialog.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        //TODO:??????
        holder.recipeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.recipeDelete.getContext());
                alertDialog.setTitle("????????????");
                alertDialog.setMessage("???????????????????");
                alertDialog.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            FirebaseDatabase.getInstance().getReference("Recipes")
                                    .child(getRef(position).getKey()).removeValue();
                        }catch (IndexOutOfBoundsException e) {
                            e.getMessage();
                        }
                    }
                });
                alertDialog.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_edit_singlerow,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView recipeUrl,recipeUpdate,recipeDelete;
        TextView recipeName,publisher,category;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            recipeUrl = itemView.findViewById(R.id.sr_recipeUrl);
            recipeName = itemView.findViewById(R.id.sr_recipe_name);
            publisher = itemView.findViewById(R.id.sr_publisher);
            category = itemView.findViewById(R.id.sr_category);
            recipeUpdate = itemView.findViewById(R.id.imgView_recipeUpdate);
            recipeDelete = itemView.findViewById(R.id.imgView_recipeDelete);

        }
    }
}
