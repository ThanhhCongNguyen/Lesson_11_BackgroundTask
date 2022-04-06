package com.example.lesson11_backgroundtask;

import android.view.View;

public interface ItemClickListener {
    void onClick(User user);
    void deleteUserClick(int id);
    void updateUserClick(int id, User user);
}
