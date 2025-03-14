package com.example.loginapplication.Retrofit;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface myNode {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email);

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("name") String name,
                                 @Field("password") String password);

}