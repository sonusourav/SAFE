package com.sonusourav.sadak.api;

import com.sonusourav.sadak.dao.postContractor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
  @GET("/documents")
  Call<ApiResponse> getPosts();

  @GET("/2.2/search/advanced?pagesize=30&order=desc&sort=activity&site=stackoverflow")
  Call<ApiResponse> searchQuestions(@Query("q") String questionTitle);


  @Multipart
  @POST("/documents")
  Call<PostResponse> addPosts(@Part("docTitle") RequestBody docTitle,
      @Part("length") RequestBody length,
      @Part("width") RequestBody width,
      @Part("material") RequestBody material,
      @Part("location") RequestBody location,
      @Part MultipartBody.Part file,
      @Header("Authorization") String auth
  );
}