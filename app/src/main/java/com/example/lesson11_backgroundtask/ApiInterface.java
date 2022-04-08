package com.example.lesson11_backgroundtask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    String token = "04251f02430425e78fa81b2faee85e4a7fd00fb3e08815b72b351c8fe69de786";
    @GET("users?access-token=" + token)
    Call<ArrayList<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUsersByID(@Path("id") int id);

   // String token = "e6c4cfb6ed8d550d9f79af16c20ad499a9dcd0fd4f8659452440bd5fef926dcf";
    @POST("users?access-token=" + token)
    Call<User> addUser(@Body() User user);

    @DELETE("users/{id}?access-token="+ token)
    Call<Void> deleteUser(@Path("id") int id);

    @PUT("users/{id}?access-token="+ token)
    Call<Void> updateUser(@Path("id") int id, @Body() User user);

}
