package com.altimetric.album;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultModel {

    @SerializedName("results")
    @Expose
    private List<AlbumModel> results;

    public ResultModel(List<AlbumModel> results) {
        this.results = results;
    }

    public List<AlbumModel> getResults() {
        return results;
    }
}
