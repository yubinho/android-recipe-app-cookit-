package com.example.cookit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageBoardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String recipeKey,recipeName;
    private Button btn_message;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId ="";
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;



    public MessageBoardFragment() {
        // Required empty public constructor
    }
    public MessageBoardFragment(String recipeKey,String recipeName){
        this.recipeName = recipeName;
        this.recipeKey = recipeKey;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageBoardFragment newInstance(String param1, String param2) {
        MessageBoardFragment fragment = new MessageBoardFragment();
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
        View view = inflater.inflate(R.layout.fragment_message_board, container, false);
        recyclerView = view.findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_message = view.findViewById(R.id.btn_leaveMessage);
        //TODO:留言按鈕
        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    String randomKey = UUID.randomUUID().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View dialogView = inflater.inflate(R.layout.message_dialog,null);
                    builder.setTitle("請在以下留言")
                            .setView(dialogView)
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            User userProfile = snapshot.getValue(User.class);
                                            if(userProfile != null){
                                                String FullName = userProfile.fullname;
                                                EditText editText = dialogView.findViewById(R.id.messageDialog);
                                                String comment = editText.getText().toString().trim();
                                                if(!(comment == null ||comment.equals(""))){
                                                    Message message = new Message(comment,userId,FullName,recipeKey,recipeName);
                                                    FirebaseDatabase.getInstance().getReference("message").child(randomKey).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(getActivity(),"已新增留言",Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.setCancelable(false);
                    builder.show();
                }else {
                    Toast.makeText(getActivity(),"請先登入",Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(user != null){
            userId = user.getUid();
        }

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("message").orderByChild("recipeKey").equalTo(recipeKey),Message.class)
                        .build();
        messageAdapter = new MessageAdapter(options,userId);
        recyclerView.setAdapter(messageAdapter);


        return view;

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