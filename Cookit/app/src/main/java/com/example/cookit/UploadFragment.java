package com.example.cookit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.StringValue;

import java.io.File;
import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String userID;
    private EditText Et_category,Et_recipeName,Et_recipeInfo,Et_recipeIngredient,Et_step1,Et_step2,Et_step3,Et_step4,Et_step5,Et_step6,Et_step7,Et_step8,Et_step9,Et_step10;
    private Spinner spinner;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Button btn_coverUpload,btn_upload,btn_step1,btn_step2,btn_step3,btn_step4,btn_step5,btn_step6,btn_step7,btn_step8,btn_step9,btn_step10,btn_toImageUpload;
    private int clickCount=0;
    private ImageView coverPicture,imgView_step1,imgView_step2,imgView_step3,imgView_step4,imgView_step5,imgView_step6,imgView_step7,imgView_step8,imgView_step9,imgView_step10;
    private Uri imageUri;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userReference,recipeReference,reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String randomKey = UUID.randomUUID().toString();
    private Map<String,Object> Map = new HashMap<>();
    private Map<String,Object> test = new HashMap<>();
    private static final String  TAG = "debug";
    private Uri[] uriSet = new Uri[11];



    public UploadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadFragment newInstance(String recipeCover,String param1, String param2) {
        UploadFragment fragment = new UploadFragment();
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
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        imgView_step1 = view.findViewById(R.id.imageStep1);
        imgView_step2 = view.findViewById(R.id.imageStep2);
        imgView_step3 = view.findViewById(R.id.imageStep3);
        imgView_step4 = view.findViewById(R.id.imageStep4);
        imgView_step5 = view.findViewById(R.id.imageStep5);
        imgView_step6 = view.findViewById(R.id.imageStep6);
        imgView_step7 = view.findViewById(R.id.imageStep7);
        imgView_step8 = view.findViewById(R.id.imageStep8);
        imgView_step9 = view.findViewById(R.id.imageStep9);
        imgView_step10 = view.findViewById(R.id.imageStep10);
        btn_step1 = view.findViewById(R.id.btn_uploadStep1);
        btn_step2 = view.findViewById(R.id.btn_uploadStep2);
        btn_step3 = view.findViewById(R.id.btn_uploadStep3);
        btn_step4 = view.findViewById(R.id.btn_uploadStep4);
        btn_step5 = view.findViewById(R.id.btn_uploadStep5);
        btn_step6 = view.findViewById(R.id.btn_uploadStep6);
        btn_step7 = view.findViewById(R.id.btn_uploadStep7);
        btn_step8 = view.findViewById(R.id.btn_uploadStep8);
        btn_step9 = view.findViewById(R.id.btn_uploadStep9);
        btn_step10 = view.findViewById(R.id.btn_uploadStep10);
        btn_coverUpload = view.findViewById(R.id.btn_Recipe_cover);
        coverPicture = view.findViewById(R.id.recipe_Cover);
        spinner = view.findViewById(R.id.uploadChooseType);
        btn_upload = view.findViewById(R.id.btn_upload);
        Et_recipeInfo = view.findViewById(R.id.uploadRecipeIntro);
        Et_recipeIngredient = view.findViewById(R.id.uploadIngredient);
        Et_recipeName = view.findViewById(R.id.uploadRecipeName);
        Et_step1 = view.findViewById(R.id.uploadStep1);
        Et_step2 = view.findViewById(R.id.uploadStep2);
        Et_step3 = view.findViewById(R.id.uploadStep3);
        Et_step4 = view.findViewById(R.id.uploadStep4);
        Et_step5 = view.findViewById(R.id.uploadStep5);
        Et_step6 = view.findViewById(R.id.uploadStep6);
        Et_step7 = view.findViewById(R.id.uploadStep7);
        Et_step8 = view.findViewById(R.id.uploadStep8);
        Et_step9 = view.findViewById(R.id.uploadStep9);
        Et_step10 = view.findViewById(R.id.uploadStep10);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //TODO:session機制
        /**Start **/
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser LoginUser = mAuth.getCurrentUser();
        if (LoginUser == null || !LoginUser.isEmailVerified()) {
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.LinearLayout_nav, new LoginFragment()).commit();
        }
        /** End **/

        //TODO:上傳食譜
        /**Start**/
        final ProgressDialog pd = new ProgressDialog(getActivity());
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setTitle("上傳食譜中");
                pd.show();
                String recipeName = Et_recipeName.getText().toString().trim();
                String recipeInfo = Et_recipeInfo.getText().toString().trim();
                String recipeIngredient = Et_recipeIngredient.getText().toString().trim();
                String step1 = Et_step1.getText().toString().trim();
                String step2 = Et_step2.getText().toString().trim();
                String step3 = Et_step3.getText().toString().trim();
                String step4 = Et_step4.getText().toString().trim();
                String step5 = Et_step5.getText().toString().trim();
                String step6 = Et_step6.getText().toString().trim();
                String step7 = Et_step7.getText().toString().trim();
                String step8 = Et_step8.getText().toString().trim();
                String step9 = Et_step9.getText().toString().trim();
                String step10 = Et_step10.getText().toString().trim();
                int calorie = 0;
                String imageStep1 = "";
                String imageStep2 = "";
                String imageStep3 = "";
                String imageStep4 = "";
                String imageStep5 = "";
                String imageStep6="";
                String imageStep7="";
                String imageStep8="";
                String imageStep9="";
                String imageStep10="";
                String checked = "no";
                if(recipeName.isEmpty()){
                    Et_recipeName.setError("需填入食譜名稱");
                    Et_recipeName.requestFocus();
                    pd.dismiss();
                    return;
                } else if(recipeInfo.isEmpty()){
                    Et_recipeInfo.setError("需填入食譜簡介");
                    Et_recipeInfo.requestFocus();
                    pd.dismiss();
                    return;
                } else if(recipeIngredient.isEmpty()){
                    Et_recipeIngredient.setError("需填入食譜原料");
                    Et_recipeIngredient.requestFocus();
                    pd.dismiss();
                    return;
                } else if(step1.isEmpty()){
                    Et_step1.setError("最少填入一個步驟");
                    Et_step1.requestFocus();
                    pd.dismiss();
                    return;
                }

                String[] categoryArray = getResources().getStringArray(R.array.type);
                int indexOfCategoryArray = spinner.getSelectedItemPosition();
                String category =  categoryArray[indexOfCategoryArray];
                if(category.equals("meat")){
                    category = "葷食";
                }else if (category.equals("vegetarian")){
                    category = "素食";
                }
                reference = FirebaseDatabase.getInstance().getReference();
                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();
                userReference = FirebaseDatabase.getInstance().getReference("Users");
                recipeReference = FirebaseDatabase.getInstance().getReference("Recipes");
                String purl = "";
                //String purl="https://www.google.com.tw/images/branding/googlelogo/2x/googlelogo_color_160x56dp.png";
                String finalCategory = category;
                userReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User userProfile = snapshot.getValue(User.class);
                                if (userProfile != null) {
                                    String name = userProfile.fullname.trim();
                                    Recipe recipe = new Recipe(name, finalCategory, recipeName, recipeInfo, recipeIngredient,
                                            step1, step2, step3, step4, step5, step6, step7, step8, step9, step10, purl, userID
                                            ,imageStep1,imageStep2,imageStep3,imageStep4,imageStep5,imageStep6,imageStep7,imageStep8,imageStep9,imageStep10,checked,calorie,clickCount);
                                    FirebaseDatabase.getInstance().getReference("Recipes")
                                            .child(randomKey).setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                pd.dismiss();
                                                Toast.makeText(getActivity(), "上傳成功", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                            }
                    });
                }
        });
        /**End **/
        //TODO:上傳圖片
        /**Start**/
        //TODO:Step1圖片上傳
        btn_step1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(1);
            }
        });
        //TODO:Step2圖片上傳
        btn_step2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(2);
            }
        });
        //TODO:Step3圖片上傳
        btn_step3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(3);
            }
        });
        //TODO:Step4圖片上傳
        btn_step4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(4);
            }
        });
        //TODO:Step5圖片上傳
        btn_step5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(5);
            }
        });
        //TODO:Step6圖片上傳
        btn_step6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(6);
            }
        });
        //TODO:Step7圖片上傳
        btn_step7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(7);
            }
        });
        //TODO:Step8圖片上傳
        btn_step8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(8);
            }
        });
        //TODO:Step9圖片上傳
        btn_step9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(9);
            }
        });
        //TODO:Step10圖片上傳
        btn_step10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(10);
            }
        });
        //TODO:上傳封面圖片
        btn_coverUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture(0);
            }
        });
        /**End **/
        return view;
    }

    private void choosePicture(int code){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        if(code == 0){
            startActivityForResult(intent,0);
        }else if(code == 1){
            startActivityForResult(intent,1);
        }else if(code == 2){
            startActivityForResult(intent,2);
        }else if(code == 3){
            startActivityForResult(intent,3);
        }else if(code == 4){
            startActivityForResult(intent,4);
        }else if(code == 5){
            startActivityForResult(intent,5);
        }else if(code == 6) {
            startActivityForResult(intent, 6);
        }else if(code == 7) {
            startActivityForResult(intent, 7);
        }else if(code == 8) {
            startActivityForResult(intent, 8);
        }else if(code == 9) {
            startActivityForResult(intent, 9);
        }else if(code == 10) {
            startActivityForResult(intent, 10);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 0 && resultCode == RESULT_OK && data.getData() != null ){
            imageUri = data.getData();
            coverPicture.setImageURI(imageUri);
            String Path="recipeCover";
            //uriSet[0] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        }else if(requestCode == 1 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri = data.getData();
            imgView_step1.setImageURI(imageUri);
            String Path="imageStep1";
            //uriSet[1] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        }else if(requestCode == 2 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri =data.getData();
            imgView_step2.setImageURI(imageUri);
            String Path="imageStep2";
            //uriSet[2] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        }else if(requestCode == 3 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri =data.getData();
            imgView_step3.setImageURI(imageUri);
            String Path="imageStep3";
            //setImageMap(Map,Path,imageUri);
            //uriSet[3] = imageUri;
            uploadPicture(Path);
        }else if(requestCode == 4 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri =data.getData();
            imgView_step4.setImageURI(imageUri);
            String Path="imageStep4";
            //setImageMap(Map,Path,imageUri);
            //uriSet[4] = imageUri;
            uploadPicture(Path);
        }else if(requestCode == 5 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri =data.getData();
            imgView_step5.setImageURI(imageUri);
            String Path="imageStep5";
            //uriSet[5] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        } else if(requestCode == 6 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri =data.getData();
            imgView_step6.setImageURI(imageUri);
            String Path="imageStep6";
            //uriSet[5] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        } else if(requestCode == 7 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri =data.getData();
            imgView_step7.setImageURI(imageUri);
            String Path="imageStep7";
            //uriSet[5] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        }
        else if(requestCode == 8 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri =data.getData();
            imgView_step8.setImageURI(imageUri);
            String Path="imageStep8";
            //uriSet[5] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        }else if(requestCode == 9 &&resultCode == RESULT_OK && data.getData() !=null){
            imageUri =data.getData();
            imgView_step9.setImageURI(imageUri);
            String Path="imageStep9";
            //uriSet[5] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        }else if(requestCode == 10 &&resultCode == RESULT_OK && data.getData() !=null) {
            imageUri = data.getData();
            imgView_step10.setImageURI(imageUri);
            String Path = "imageStep10";
            //uriSet[5] = imageUri;
            //setImageMap(Map,Path,imageUri);
            uploadPicture(Path);
        }
    }
    private void setImageMap(Map<String,Object> hashMaps,String path,Uri imageUri){
        hashMaps.put(path,String.valueOf(imageUri));
        this.Map = hashMaps;
    }
    private void uploadPicture(String path) {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("上傳圖片中");
        pd.show();
        String recipeName = Et_recipeName.getText().toString().trim();
        if(recipeName.isEmpty()){
            Et_recipeName.setError("需填入食譜名稱");
            Et_recipeName.requestFocus();
            pd.dismiss();
            return;
        }
        final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference riversRef = storageReference.child("images"+"/"+UID+"/"+randomKey+"/"+path);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Date currentTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8")).getTime();
                                DatabaseReference imageStore = FirebaseDatabase.getInstance().getReference().child("images"+"/"+randomKey);
                                Map<String,Object> hashMap = new HashMap<>();
                                hashMap.put(String.valueOf(path), String.valueOf(uri));
                                imageStore.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                         pd.dismiss();
                                         Snackbar.make(getActivity().findViewById(android.R.id.content),"上傳成功",Snackbar.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(),"上傳失敗",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("進度: "+(int) progressPercent+ "%");
                    }
                });


    }

}