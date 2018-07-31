// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.favoriteplaces;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentAddFavoritePlace_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentAddFavoritePlace target;

  @UiThread
  public FragmentAddFavoritePlace_ViewBinding(FragmentAddFavoritePlace target, View source) {
    super(target, source);

    this.target = target;

    target.mTxtFavPlaceName = Utils.findRequiredViewAsType(source, R.id.txtFavPlaceName, "field 'mTxtFavPlaceName'", TextInputEditText.class);
    target.mTxtFavPlaceAddress = Utils.findRequiredViewAsType(source, R.id.txtFavPlaceAddress, "field 'mTxtFavPlaceAddress'", AutoCompleteTextView.class);
    target.mLblRadius = Utils.findRequiredViewAsType(source, R.id.lblRadius, "field 'mLblRadius'", AppCompatTextView.class);
    target.mSkRadius = Utils.findRequiredViewAsType(source, R.id.skRadius, "field 'mSkRadius'", AppCompatSeekBar.class);
  }

  @Override
  public void unbind() {
    FragmentAddFavoritePlace target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtFavPlaceName = null;
    target.mTxtFavPlaceAddress = null;
    target.mLblRadius = null;
    target.mSkRadius = null;

    super.unbind();
  }
}
