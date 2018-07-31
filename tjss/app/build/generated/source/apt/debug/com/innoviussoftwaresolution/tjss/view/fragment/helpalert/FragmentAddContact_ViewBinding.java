// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.helpalert;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentAddContact_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentAddContact target;

  private View view2131755015;

  @UiThread
  public FragmentAddContact_ViewBinding(final FragmentAddContact target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mTxtAddContactName = Utils.findRequiredViewAsType(source, R.id.txtAddContactName, "field 'mTxtAddContactName'", TextInputEditText.class);
    target.mCboAddContactIsdCode = Utils.findRequiredViewAsType(source, R.id.cboAddContactIsdCode, "field 'mCboAddContactIsdCode'", AppCompatSpinner.class);
    target.mTxtAddContactPhone = Utils.findRequiredViewAsType(source, R.id.txtAddContactPhone, "field 'mTxtAddContactPhone'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.btnNext, "method 'onViewClicked'");
    view2131755015 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentAddContact target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtAddContactName = null;
    target.mCboAddContactIsdCode = null;
    target.mTxtAddContactPhone = null;

    view2131755015.setOnClickListener(null);
    view2131755015 = null;

    super.unbind();
  }
}
