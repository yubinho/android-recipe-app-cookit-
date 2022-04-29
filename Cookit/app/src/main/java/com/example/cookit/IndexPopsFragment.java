package com.example.cookit;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexPopsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexPopsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_toNews;
    private ImageButton imgBtn_recipe;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    RecyclerView recyclerView;
    IndexPopsAdapter indexPopsAdapter;

    public IndexPopsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndexPopsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndexPopsFragment newInstance(String param1, String param2) {
        IndexPopsFragment fragment = new IndexPopsFragment();
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
        View view = inflater.inflate(R.layout.fragment_index_pops, container, false);
        // TODO:從熱門排行fragment跳至最新動態fragment
        btn_toNews = view.findViewById(R.id.btn_indexNews);
        btn_toNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager=getActivity().getSupportFragmentManager();
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.LinearLayout_nav,new IndexNewsFragment()).commit();
                getActivity().recreate();
            }
        });
        recyclerView = view.findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Recipes").orderByChild("order"),Recipe.class)
                        .build();
        indexPopsAdapter = new IndexPopsAdapter(options);
        recyclerView.setAdapter(indexPopsAdapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        indexPopsAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        indexPopsAdapter.stopListening();
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
        indexPopsAdapter = new IndexPopsAdapter(options);
        indexPopsAdapter.startListening();
        recyclerView.setAdapter(indexPopsAdapter);
    }
}