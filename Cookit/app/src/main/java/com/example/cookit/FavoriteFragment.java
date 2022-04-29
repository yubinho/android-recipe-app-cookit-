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
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton btn_favorite;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private String userId;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser LoginUser = mAuth.getCurrentUser();
    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        //TODO:session機制

        if(LoginUser == null){
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.LinearLayout_nav, new LoginFragment()).commit();;
        }
        if(LoginUser != null){
            userId = LoginUser.getUid();
            recyclerView = view.findViewById(R.id.favoriteRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            FirebaseRecyclerOptions<Recipe> options =
                    new FirebaseRecyclerOptions.Builder<Recipe>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("favorite").child(userId),Recipe.class)
                            .build();
            favoriteAdapter = new FavoriteAdapter(options);
            recyclerView.setAdapter(favoriteAdapter);
        }




        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        if(LoginUser != null){
            favoriteAdapter.startListening();
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        if(LoginUser != null){
            favoriteAdapter.stopListening();
        }
    }

}