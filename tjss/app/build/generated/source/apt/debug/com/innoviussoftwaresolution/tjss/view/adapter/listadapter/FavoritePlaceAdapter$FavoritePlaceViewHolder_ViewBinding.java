// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavoritePlaceAdapter$FavoritePlaceViewHolder_ViewBinding implements Unbinder {
  private FavoritePlaceAdapter.FavoritePlaceViewHolder target;

  private View viewSource;

  @UiThread
  public FavoritePlaceAdapter$FavoritePlaceViewHolder_ViewBinding(final FavoritePlaceAdapter.FavoritePlaceViewHolder target,
      View source) {
    this.target = target;

    target.mLblFavPlaceName = Utils.findOptionalViewAsType(source, R.id.lblFavPlaceName, "field 'mLblFavPlaceName'", AppCompatTextView.class);
    target.mLblFavPlaceAddress = Utils.findOptionalViewAsType(source, R.id.lblFavPlaceAddress, "field 'mLblFavPlaceAddress'", AppCompatTextView.class);
    viewSource = source;
    source.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoritePlaceAdapter.FavoritePlaceViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblFavPlaceName = null;
    target.mLblFavPlaceAddress = null;

    viewSource.setOnClickListener(null);
    viewSource = null;
  }
}
