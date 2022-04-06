package com.example.lesson11_backgroundtask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    private ArrayList<User> userArrayList;
    private ItemClickListener itemClickListener;

    public UserListAdapter( ArrayList<User> userArrayList, ItemClickListener itemClickListener) {
        this.userArrayList = userArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_item, parent, false);
        return new UserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {

        User user = userArrayList.get(position);
        holder.nameTextView.setText("Name: ".concat(user.getName()));
        holder.emailTextView.setText("Email: ".concat(user.getEmail()));

        holder.itemViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(user);
            }


        });

        holder.deleteAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.deleteUserClick(user.getId());
            }
        });

        holder.editAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.updateUserClick(user.getId(), user);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }



    class UserListViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, emailTextView;
        LinearLayout itemViewUser;
        ImageView editAction, deleteAction;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_textview);
            emailTextView = itemView.findViewById(R.id.email_textview);
            itemViewUser = itemView.findViewById(R.id.itemView);
            editAction = itemView.findViewById(R.id.edit_action);
            deleteAction = itemView.findViewById(R.id.delete_action);

        }
    }
}
