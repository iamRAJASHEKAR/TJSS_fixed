// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatDetailAdapter$BaseViewHolder_ViewBinding implements Unbinder {
  private ChatDetailAdapter.BaseViewHolder target;

  private View view2131755014;

  private View viewSource;

  @UiThread
  public ChatDetailAdapter$BaseViewHolder_ViewBinding(final ChatDetailAdapter.BaseViewHolder target,
      View source) {
    this.target = target;

    View view;
    view = source.findViewById(R.id.btnDeleteMessage);
    target.mBtnDeleteMessage = Utils.castView(view, R.id.btnDeleteMessage, "field 'mBtnDeleteMessage'", AppCompatImageButton.class);
    if (view != null) {
      view2131755014 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onDeleteClicked();
        }
      });
    }
    viewSource = source;
    source.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatDetailAdapter.BaseViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnDeleteMessage = null;

    if (view2131755014 != null) {
      view2131755014.setOnClickListener(null);
      view2131755014 = null;
    }
    viewSource.setOnClickListener(null);
    viewSource = null;
  }
}
