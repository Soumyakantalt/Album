package com.altimetric.album;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.altimetric.album.utils.APIService;
import com.altimetric.album.utils.ApiUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumViewModel extends ViewModel {

    private MutableLiveData<List<AlbumModel>> albumList;
    AlbumModel albumModel;
    List<AlbumModel> albumModelList = new ArrayList<>();
    public MutableLiveData<Boolean> progress = new MutableLiveData<>();

    //we will call this method to get the data
    public LiveData<List<AlbumModel>> getAlbums() {
        //if the list is null
        if (albumList == null) {
            albumList = new MutableLiveData<List<AlbumModel>>();
            //we will load it asynchronously from server in this method
            progress.postValue(true);
            loadAlbums();
        }

        //finally we will return the list
        return albumList;
    }

    //This method is using Retrofit to get the JSON data from URL
    private void loadAlbums() {

        APIService apiService = ApiUtils.getRetrofit().create(APIService.class);
        apiService.getAlbumData("all").enqueue(new Callback<ResultModel>() {

            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                Log.e("success----->>>>>", "response.code()  " + response.code());
                progress.postValue(false);
                if (response.isSuccessful()) {
                    ResultModel resultModel = new ResultModel(response.body().getResults());
                    for(int i=0; i<resultModel.getResults().size(); i++) {
                        albumModel = new AlbumModel(resultModel.getResults().get(i).getArtistName(), resultModel.getResults().get(i).getTrackName(), resultModel.getResults().get(i).getCollectionName(), resultModel.getResults().get(i).getCollectionPrice(), resultModel.getResults().get(i).getTrackPrice(), resultModel.getResults().get(i).getReleaseDate(), resultModel.getResults().get(i).getArtworkUrl100());
                        albumModelList.add(albumModel);
                    }
                    albumList.setValue(albumModelList);
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                progress.postValue(false);
            }
        });

    }
}
