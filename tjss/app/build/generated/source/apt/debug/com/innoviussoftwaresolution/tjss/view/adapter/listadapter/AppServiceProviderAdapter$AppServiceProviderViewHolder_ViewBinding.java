// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppServiceProviderAdapter$AppServiceProviderViewHolder_ViewBinding implements Unbinder {
  private AppServiceProviderAdapter.AppServiceProviderViewHolder target;

  private View viewSource;

  @UiThread
  public AppServiceProviderAdapter$AppServiceProviderViewHolder_ViewBinding(final AppServiceProviderAdapter.AppServiceProviderViewHolder target,
      View source) {
    this.target = target;

    target.mLblServiceProviderTitle = Utils.findRequiredViewAsType(source, R.id.lblServiceProviderTitle, "field 'mLblServiceProviderTitle'", AppCompatTextView.class);
    target.mLblServiceProviderDescription = Utils.findRequiredViewAsType(source, R.id.lblServiceProviderDescription, "field 'mLblServiceProviderDescription'", AppCompatTextView.class);
    target.mImgBtnSelected = Utils.findRequiredViewAsType(source, R.id.imgBtnSelected, "field 'mImgBtnSelected'", ImageButton.class);
    target.mLblServiceProviderCategoryAndSubCategory = Utils.findRequiredViewAsType(source, R.id.lblServiceProviderCategoryAndSubCategory, "field 'mLblServiceProviderCategoryAndSubCategory'", AppCompatTextView.class);
    viewSource = source;
    source.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
    source.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View p0) {
        return target.onLongClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AppServiceProviderAdapter.AppServiceProviderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblServiceProviderTitle = null;
    target.mLblServiceProviderDescription = null;
    target.mImgBtnSelected = null;
    target.mLblServiceProviderCategoryAndSubCategory = null;

    viewSource.setOnClickListener(null);
    viewSource.setOnLongClickListener(null);
    viewSource = null;
  }
}
