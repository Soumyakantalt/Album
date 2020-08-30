package com.altimetric.album;

import com.altimetric.album.utils.CollectionName;
import com.altimetric.album.utils.ReleaseDate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Comparator;

public class AlbumModel {

    @SerializedName("artistName")
    @Expose
    private String artistName;

    @SerializedName("trackName")
    @Expose
    private String trackName;

    @SerializedName("collectionName")
    @Expose
    private String collectionName;

    @SerializedName("collectionPrice")
    @Expose
    private double collectionPrice;

    @SerializedName("trackPrice")
    @Expose
    private double trackPrice;

    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;

    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;

    boolean flag;
    boolean isSelected;

    public AlbumModel(String artistName, String trackName, String collectionName, double collectionPrice, double trackPrice, String releaseDate, String artworkUrl100) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.collectionName = collectionName;
        this.collectionPrice = collectionPrice;
        this.trackPrice = trackPrice;
        this.releaseDate = releaseDate;
        this.artworkUrl100 = artworkUrl100;
    }

    void setSelected() { }

    public AlbumModel() { }

    public AlbumModel(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public double getCollectionPrice() {
        return collectionPrice;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public static class OrderByTrackPrice implements Comparator<AlbumModel> {
        @Override
        public int compare(AlbumModel t1, AlbumModel t2) {
            return t1.getTrackPrice() > t2.getTrackPrice() ? 1 : (t1.getTrackPrice() < t2.getTrackPrice() ? -1 : 0);
        }
    }

    public static class OrderByReleaseDate implements Comparator<AlbumModel> {
        @Override
        public int compare(AlbumModel o1, AlbumModel o2) {
            return o1.getReleaseDate().compareTo(o2.getReleaseDate());
        }
    }

    public static class OrderByArtistName implements Comparator<AlbumModel> {
        @Override
        public int compare(AlbumModel o1, AlbumModel o2) {
            return o1.getArtistName().compareTo(o2.getArtistName());
        }
    }

    public static class OrderByTrackName implements Comparator<AlbumModel> {
        @Override
        public int compare(AlbumModel o1, AlbumModel o2) {
            return o1.getTrackName().compareTo(o2.getTrackName());
        }
    }
}
