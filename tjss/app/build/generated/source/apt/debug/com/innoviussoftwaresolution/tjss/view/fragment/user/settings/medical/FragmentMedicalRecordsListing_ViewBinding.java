// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.medical;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentMedicalRecordsListing_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentMedicalRecordsListing target;

  private View view2131755459;

  @UiThread
  public FragmentMedicalRecordsListing_ViewBinding(final FragmentMedicalRecordsListing target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstMedicalRecords = Utils.findRequiredViewAsType(source, R.id.lstMedicalRecords, "field 'mLstMedicalRecords'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnAddNewMedicalRecord, "method 'onViewClicked'");
    view2131755459 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentMedicalRecordsListing target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstMedicalRecords = null;

    view2131755459.setOnClickListener(null);
    view2131755459 = null;

    super.unbind();
  }
}
