package com.altimetric.album;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.altimetric.album.databinding.ActivityAlbumBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    AlbumAdapter adapter;
    ActivityAlbumBinding binding;
    LinearLayoutManager linearLayoutManager;
    TextView filterItemCountTV;
    List<AlbumModel> album = new ArrayList<>();
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlbumViewModel model = ViewModelProviders.of(this).get(AlbumViewModel.class);
        binding = DataBindingUtil.setContentView(AlbumActivity.this, R.layout.activity_album);
        binding.setLifecycleOwner(this);
        binding.setAlbumViewModel(model);
        binding.recyclerView.setHasFixedSize(true);

        model.getAlbums().observe(this, new Observer<List<AlbumModel>>() {
            @Override
            public void onChanged(@Nullable List<AlbumModel> albumList) {
                album = albumList;
                Collections.sort(album, new AlbumModel.OrderByReleaseDate());
                adapter = new AlbumAdapter(AlbumActivity.this, albumList);
                linearLayoutManager = new LinearLayoutManager(AlbumActivity.this);
                binding.recyclerView.setLayoutManager(linearLayoutManager);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(AlbumActivity.this));
                binding.recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_filter);

        View actionView = menuItem.getActionView();
        filterItemCountTV = actionView.findViewById(R.id.filter_badge);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search by artist or track name");
        searchView.setOnQueryTextListener(this);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_filter: {
                showFilterDialog();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        if (searchText.length() > 0) {
            List<AlbumModel> modelData = adapter.filter(searchText);
            binding.recyclerView.setAdapter(new AlbumAdapter(this, modelData));
            new AlbumAdapter(this, modelData).notifyDataSetChanged();
        } else {
            adapter = new AlbumAdapter(this, album);
            binding.recyclerView.setLayoutManager(linearLayoutManager);
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    private void showFilterDialog() {
        final Dialog dialog = new Dialog(AlbumActivity.this);
        if (dialog.getWindow() != null)
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_filter_selection);

        Button cancel = dialog.findViewById(R.id.cancel_button);
        Button submit = dialog.findViewById(R.id.submit_button);
        radioGroup = dialog.findViewById(R.id.radio_group);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = dialog.findViewById(selectedId);

        if(str.equalsIgnoreCase("Release Date"))
            radioGroup.check(R.id.release_date);
        else if(str.equalsIgnoreCase("Track Name"))
            radioGroup.check(R.id.track_name);
        else if(str.equalsIgnoreCase("Artist Name"))
            radioGroup.check(R.id.artist_name);
        else if(str.equalsIgnoreCase("Track Price"))
            radioGroup.check(R.id.track_price);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = dialog.findViewById(selectedId);
                if(radioButton.getText().toString().equalsIgnoreCase("Release Date")) {
                    Collections.sort(album, new AlbumModel.OrderByReleaseDate());
                    adapter = new AlbumAdapter(AlbumActivity.this, album);
                    linearLayoutManager = new LinearLayoutManager(AlbumActivity.this);
                    binding.recyclerView.setLayoutManager(linearLayoutManager);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(AlbumActivity.this));
                    binding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    str = "Release Date";
                } else if(radioButton.getText().toString().equalsIgnoreCase("Track Name")) {
                    Collections.sort(album, new AlbumModel.OrderByTrackName());
                    adapter = new AlbumAdapter(AlbumActivity.this, album);
                    linearLayoutManager = new LinearLayoutManager(AlbumActivity.this);
                    binding.recyclerView.setLayoutManager(linearLayoutManager);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(AlbumActivity.this));
                    binding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    str = "Track Name";
                } else if(radioButton.getText().toString().equalsIgnoreCase("Artist Name")) {
                    Collections.sort(album, new AlbumModel.OrderByArtistName());
                    adapter = new AlbumAdapter(AlbumActivity.this, album);
                    linearLayoutManager = new LinearLayoutManager(AlbumActivity.this);
                    binding.recyclerView.setLayoutManager(linearLayoutManager);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(AlbumActivity.this));
                    binding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    str = "Artist Name";
                } else if(radioButton.getText().toString().equalsIgnoreCase("Track Price")) {
                    Collections.sort(album, new AlbumModel.OrderByTrackPrice());
                    Collections.reverse(album);
                    adapter = new AlbumAdapter(AlbumActivity.this, album);
                    linearLayoutManager = new LinearLayoutManager(AlbumActivity.this);
                    binding.recyclerView.setLayoutManager(linearLayoutManager);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(AlbumActivity.this));
                    binding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    str = "Track Price";
                }
                dialog.dismiss();
            }
        });

        dialog.show();

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
