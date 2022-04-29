package com.example.cookit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyMessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String recipeKey;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId ="";
    MyMessageAdapter messageAdapter;
    RecyclerView recyclerView;


    public MyMessageFragment() {
        // Required empty public constructor
    }

    public MyMessageFragment(String recipeKey){
        this.recipeKey = recipeKey;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyMessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyMessageFragment newInstance(String param1, String param2) {
        MyMessageFragment fragment = new MyMessageFragment();
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
        View view =  inflater.inflate(R.layout.fragment_my_message, container, false);
        recyclerView = view.findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(user != null){
            userId = user.getUid();
        }

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("message").orderByChild("userId").equalTo(userId),Message.class)
                        .build();
        messageAdapter = new MyMessageAdapter(options,userId);
        recyclerView.setAdapter(messageAdapter);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(messageAdapter != null){
            messageAdapter.startListening();
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        messageAdapter.stopListening();
    }
}