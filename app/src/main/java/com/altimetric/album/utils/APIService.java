package com.altimetric.album.utils;

import com.altimetric.album.AlbumModel;
import com.altimetric.album.ResultModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIService {

    @Headers("Content-Type: application/json")
    @GET("search")
    Call<ResultModel> getAlbumData(@Query("term") String all);
}
