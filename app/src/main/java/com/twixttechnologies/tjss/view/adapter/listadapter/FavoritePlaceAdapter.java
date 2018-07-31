package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.FavoritePlace;
import com.twixttechnologies.tjss.view.activity.EditFavPlaceActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 07-08-17.
 */
public class FavoritePlaceAdapter extends RecyclerView.Adapter<FavoritePlaceAdapter.FavoritePlaceViewHolder> {


    private List<FavoritePlace> items;

    public FavoritePlaceAdapter(List<FavoritePlace> items) {
        this.items = items;
    }

    @Override
    public FavoritePlaceViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_favorite_place_item, parent, false);
        return new FavoritePlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritePlaceViewHolder holder, int position) {
        FavoritePlace item = items.get(position);
        if (item == null) return;

        if (holder.mLblFavPlaceName != null) {
            holder.mLblFavPlaceName.setText(item.name);
        }
        if (holder.mLblFavPlaceAddress != null) {
            holder.mLblFavPlaceAddress.setText(item.address);
        }

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class FavoritePlaceViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.lblFavPlaceName)
        AppCompatTextView mLblFavPlaceName;
        @Nullable
        @BindView(R.id.lblFavPlaceAddress)
        AppCompatTextView mLblFavPlaceAddress;

        FavoritePlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onClick(View view) {
            int index = getAdapterPosition();
            if (index < 0) return;
            FavoritePlace favoritePlace = items.get(index);
            if (favoritePlace == null) return;
            Intent editOrDelete = new Intent(view.getContext(), EditFavPlaceActivity.class);
            editOrDelete.putExtra("favPlace", favoritePlace);
            view.getContext().startActivity(editOrDelete);
        }


    }

}