package com.example.edumate.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OcrSpaceService {
    @GET("parse/image")
    Call<ResponseBody> uploadImageUrlToOcrSpace(
            @Header("apikey") String apiKey,
            @Query("url") String imageUrl
    );

}


