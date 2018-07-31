// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.favoriteplaces;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentFavoritePlacesList_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentFavoritePlacesList target;

  private View view2131755403;

  @UiThread
  public FragmentFavoritePlacesList_ViewBinding(final FragmentFavoritePlacesList target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstFavoritePlaces = Utils.findRequiredViewAsType(source, R.id.lstFavoritePlaces, "field 'mLstFavoritePlaces'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnAddFavoritePlace, "method 'onViewClicked'");
    view2131755403 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentFavoritePlacesList target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstFavoritePlaces = null;

    view2131755403.setOnClickListener(null);
    view2131755403 = null;

    super.unbind();
  }
}
