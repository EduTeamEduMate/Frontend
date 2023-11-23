package com.example.edumate.network;

import com.example.edumate.models.TokenResponse;
import com.example.edumate.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/signup")
    Call<User> createUser(@Body User user);

    @POST("/login") // Ensure this matches your FastAPI endpoint
    @FormUrlEncoded
    Call<TokenResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @PUT("/users/{user_id}")
    Call<User> updateUser(@Path("user_id") int userId, @Body User updatedUser);

    @PUT("/users/password/{user_id}")
    Call<User> updateUserPassword(@Path("user_id") int userId, @Body User updatedUser);
}
