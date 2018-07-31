// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.message;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentChatDetail_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentChatDetail target;

  private View view2131755386;

  private View view2131755384;

  @UiThread
  public FragmentChatDetail_ViewBinding(final FragmentChatDetail target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstChatMessages = Utils.findRequiredViewAsType(source, R.id.lstChatMessages, "field 'mLstChatMessages'", RecyclerView.class);
    target.mTxtChatMessage = Utils.findRequiredViewAsType(source, R.id.txtChatMessage, "field 'mTxtChatMessage'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.btnSendMessage, "method 'onSendClicked'");
    view2131755386 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSendClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAttachment, "method 'onAttachmentClicked'");
    view2131755384 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAttachmentClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentChatDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstChatMessages = null;
    target.mTxtChatMessage = null;

    view2131755386.setOnClickListener(null);
    view2131755386 = null;
    view2131755384.setOnClickListener(null);
    view2131755384 = null;

    super.unbind();
  }
}
