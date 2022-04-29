package com.example.cookit;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageAdapter.myviewholder> {

    String userId;
    public MessageAdapter(@NonNull FirebaseRecyclerOptions<Message> options,String userId) {
        super(options);
        this.userId = userId;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageAdapter.myviewholder holder, int position, @NonNull Message model) {
        holder.publisher.setText(model.getUserName()+" : ");
        holder.comment.setText(model.getMessage());
        if(model.getUserId().equals(userId)){
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.delete.getContext());
                    alertDialog.setTitle("系統訊息");
                    alertDialog.setMessage("確定要刪除嗎?");
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                FirebaseDatabase.getInstance().getReference("message")
                                        .child(getRef(position).getKey()).removeValue();
                            }catch (IndexOutOfBoundsException e) {
                                e.getMessage();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            });
        }
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message_singlerow,parent,false);
        return new MessageAdapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView publisher,comment;
        ImageButton delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            publisher = itemView.findViewById(R.id.sr_message_publisher);
            comment = itemView.findViewById(R.id.sr_message);
            delete = itemView.findViewById(R.id.ic_messageDelete);
        }
    }
}
