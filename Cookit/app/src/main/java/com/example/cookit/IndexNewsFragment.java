package com.example.cookit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexNewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private static final String ACTIVITY_TAG="LogDemo";
    private String mParam1;
    private String mParam2;
    private Button btn_toPop;
    private ImageButton imgBtn_recipe;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private FirebaseUser user;
    private String userID;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    RecyclerView recyclerView;
    IndexNewsAdapter myadapter;

    public IndexNewsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static IndexNewsFragment newInstance(String param1, String param2) {
        IndexNewsFragment fragment = new IndexNewsFragment();
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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_index_news, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reference = FirebaseDatabase.getInstance().getReference("Users");
        if(user != null){
            userID = user.getUid();
        }
        String index = FirebaseDatabase.getInstance().getReference("test").push().getKey();
        //Query keyQuery = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                      .setQuery(FirebaseDatabase.getInstance().getReference("Recipes").orderByChild("checked").equalTo("yes"),Recipe.class)
                        .build();
        myadapter = new IndexNewsAdapter(options);
        recyclerView.setAdapter(myadapter);



        //TODO:從最新動態fragment跳至熱門排行fragment
        btn_toPop = view.findViewById(R.id.btn_indexPops);
        btn_toPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager=getActivity().getSupportFragmentManager();
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.LinearLayout_nav,new IndexPopsFragment()).commit();
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        myadapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        myadapter.stopListening();
    }
    //TODO:搜尋
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searchmenu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null){
                    processSearch(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null){
                    processSearch(newText);
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void processSearch(String s){

            FirebaseRecyclerOptions<Recipe> options =
                    new FirebaseRecyclerOptions.Builder<Recipe>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("Recipes").orderByChild("recipeName").startAt(s).endAt(s+"\uf8ff"),Recipe.class)
                            .build();
            myadapter = new IndexNewsAdapter(options);
            myadapter.startListening();
            recyclerView.setAdapter(myadapter);
    }

}
