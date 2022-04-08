package com.example.lesson11_backgroundtask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;
    private ArrayList<User> userArrayList;
    private FloatingActionButton addButton;
    private EditText nameEdittext, emailEdittext;
    private String[] gender = {"Male","Female"};
    private String[] status = {"Active","Inactive"};
    private Spinner genderSpinner, statusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        userArrayList = new ArrayList<>();
        userListAdapter = new UserListAdapter(userArrayList, new ItemClickListener() {
            @Override
            public void onClick(User user) {
                onClickGoToDetail(user);
            }

            @Override
            public void deleteUserClick(int id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("DELETE?");
                builder.setMessage("Do you want to delete this user?");
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUser(id);
                    }
                });
                builder.create();
                builder.show();

            }

            @Override
            public void updateUserClick(int id, User user) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("UPDATE?");
                builder.setMessage("Do you want to update this user?");
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateUser(id, user);
                    }
                });
                builder.create();
                builder.show();
            }
        });
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiClient.getAPI().getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                userArrayList.addAll(response.body());
                userListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final  View addUserView = getLayoutInflater().inflate(R.layout.add_user_layout, null);
                nameEdittext = addUserView.findViewById(R.id.name_edittext);
                emailEdittext= addUserView.findViewById(R.id.email_edittext);
                genderSpinner = addUserView.findViewById(R.id.gender_spinner);
                statusSpinner = addUserView.findViewById(R.id.status_spinner);

                ArrayAdapter genderSp = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, gender);
                genderSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                genderSpinner.setAdapter(genderSp);

                ArrayAdapter statusSp = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, status);
                statusSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                statusSpinner.setAdapter(statusSp);
                builder.setView(addUserView);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = nameEdittext.getText().toString().trim();
                        String email = emailEdittext.getText().toString().trim();
                        String gender = genderSpinner.getSelectedItem().toString().trim();
                        String status = statusSpinner.getSelectedItem().toString().trim();



                        User user = new User(name, email, gender, status);
                        ApiClient.getAPI().addUser(user).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                userArrayList.add(response.body());
                               // userListAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Add user successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.create();
                builder.show();


            }
        });
    }

    private void updateUser(int id, User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final  View updateUserView = getLayoutInflater().inflate(R.layout.update_user_layout, null);
        nameEdittext = updateUserView.findViewById(R.id.name_edittext);
        emailEdittext= updateUserView.findViewById(R.id.email_edittext);
        genderSpinner = updateUserView.findViewById(R.id.gender_spinner);
        statusSpinner = updateUserView.findViewById(R.id.status_spinner);


        ArrayAdapter genderSp = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, gender);
        genderSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderSp);

        ArrayAdapter statusSp = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, status);
        statusSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusSp);

        nameEdittext.setText(user.getName());
        emailEdittext.setText(user.getEmail());
        builder.setView(updateUserView);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameEdittext.getText().toString().trim();
                String email = emailEdittext.getText().toString().trim();
                String gender = genderSpinner.getSelectedItem().toString().trim();
                String status = statusSpinner.getSelectedItem().toString().trim();

                User user = new User(name, email, gender, status);
                ApiClient.getAPI().updateUser(id, user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(MainActivity.this, "Update successfully", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());


                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });
        builder.create();
        builder.show();
    }

    private void deleteUser(int id) {
        Call<Void> call = ApiClient.getAPI().deleteUser(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //Toast.makeText(MainActivity.this, response.code()+"t", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void findId(){
        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.add_button);
    }

    private void onClickGoToDetail(User user){
        Intent intent = new Intent(MainActivity.this, DetailUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}