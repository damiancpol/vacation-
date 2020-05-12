package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface JsonAPi {
 @POST("checklog")
    Call<Uusers> checklogin(@Body Uusers up);

    @GET("all")
     Call<List<String>> getAll();
    @POST("saveurlop")
    Call<Void> saveurlop(@Body Urlop up);

    @GET("allUsers")
    Call<List<Urlop>>geTallUsers();

    @POST("konf")
    Call<Uusers> konfiguracja(@Body Uusers uu);
    @POST("updateUser")
    Call<Void> updateUser(@Body Uusers us);
   @GET("alll")
  Call<List<Uusers>> getAlll();



}
