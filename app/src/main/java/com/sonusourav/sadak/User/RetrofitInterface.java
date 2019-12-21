package com.sonusourav.sadak.User;

import com.sonusourav.sadak.Utils.ResponseClass;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface RetrofitInterface {

  @Multipart
  @POST
  Call<ResponseBody> uploadImage(@Url String Url, @Part MultipartBody.Part image,
      @Header("Authorization") String auth);

  @FormUrlEncoded
  @POST("updatepassword")
  Call<ResponseBody> updatepassword(@Header("Authorization") String auth,
      @Field("password") String newPass);

  @FormUrlEncoded
  @POST("/users/fcmtoken")
  Call<ResponseClass> sendFCMToken(@Header("Authorization") String auth,
      @Field("fcmToken") String fcmToken);

  @GET("users/picnameemail")
  Call<UserClass> getPicNameEmail(@Header("Authorization") String header);
}