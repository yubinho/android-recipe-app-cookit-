package com.example.cookit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RecipeEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ImageButton imgBtn_recipe;
    private FirebaseUser user;
    private String userID;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    RecyclerView recyclerView;
    RecipeEditAdapter myAdapter;

    public RecipeEditFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static RecipeEditFragment newInstance(String param1, String param2) {
        RecipeEditFragment fragment = new RecipeEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_recipe_edit, container, false);
        //TODO:session機制
        /**Start **/
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser LoginUser = mAuth.getCurrentUser();

        if (LoginUser == null || !LoginUser.isEmailVerified()) {
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.LinearLayout_nav, new LoginFragment()).commit();
        }
        if(LoginUser != null){
            userID = LoginUser.getUid();
            if(userID.equals("3O2Wo1xf9GeRRsMsMwby2v1Icgq2")){
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.LinearLayout_nav, new RecipeCheckFragment()).commit();
            }
        }
        /** End **/
        recyclerView = view.findViewById(R.id.recipeEditRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Recipes").orderByChild("uid").equalTo(userID),Recipe.class)
                .build();
        myAdapter = new RecipeEditAdapter(options);
        recyclerView.setAdapter(myAdapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        myAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

}