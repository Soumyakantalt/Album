package com.altimetric.album;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.altimetric.album.utils.DateUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    Context context;
    List<AlbumModel> albumList, itemArray;
    Set<String> selectedItems = new HashSet<>();

    public AlbumAdapter(Context context, List<AlbumModel> albumList) {
        this.context = context;
        this.albumList = albumList;
        notifyDataSetChanged();
        this.itemArray = new ArrayList<>();
        this.itemArray.addAll(albumList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.album_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            Picasso.get().load(albumList.get(position).getArtworkUrl100()).resize(100, 100).centerCrop().onlyScaleDown().into(holder.artWorkIV);
            holder.artistNameTV.setText(albumList.get(position).getArtistName());
            holder.trackNameTV.setText(albumList.get(position).getTrackName());
            holder.trackPriceTV.setText("\u0024" + " " + albumList.get(position).getTrackPrice());
            holder.releaseDateTV.setText(DateUtils.getFormattedDate(albumList.get(position).getReleaseDate()));

        holder.constraintLayout.setTag(position);
        holder.constraintLayout.setOnClickListener(v -> {
            int pos = (int) v.getTag();
            albumList.get(pos).flag = !albumList.get(pos).flag;
            notifyDataSetChanged();
        });

        holder.constraintLayout.setOnLongClickListener(v -> {
            int pos = (int) v.getTag();
            albumList.get(pos).flag = !albumList.get(pos).flag;
            notifyDataSetChanged();
            return true;
        });

        if (albumList.get(position).flag) {
            selectedItems.add(albumList.get(position).getArtistName());
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#90EE90"));
            albumList.get(position).setSelected();
        } else {
            selectedItems.remove(albumList.get(position).getArtistName());
            holder.constraintLayout.setBackgroundColor(0);
        }
    }

    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    List<AlbumModel> filter(final String text) {
        itemArray.clear();
        if (TextUtils.isEmpty(text)) {
            itemArray.addAll(albumList);
        } else {
            for (AlbumModel item : albumList) {
                if (item.getArtistName().toLowerCase().contains(text.toLowerCase()) || item.getTrackName().toLowerCase().contains(text.toLowerCase())) {
                    itemArray.add(item);
                }
            }
        }
        return itemArray;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        TextView artistNameTV, trackNameTV, trackPriceTV, releaseDateTV;
        ImageView artWorkIV;

        public ViewHolder(View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.constraint_layout);
            artWorkIV = itemView.findViewById(R.id.art_work_image);
            artistNameTV = itemView.findViewById(R.id.artist_name);
            trackNameTV = itemView.findViewById(R.id.track_name);
            trackPriceTV = itemView.findViewById(R.id.track_price);
            releaseDateTV = itemView.findViewById(R.id.release_date);
        }
    }
}

