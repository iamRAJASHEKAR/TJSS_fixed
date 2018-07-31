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

public class MedicalRecordsListAdapter$MedicalRecordViewHolder_ViewBinding implements Unbinder {
  private MedicalRecordsListAdapter.MedicalRecordViewHolder target;

  private View view2131755233;

  @UiThread
  public MedicalRecordsListAdapter$MedicalRecordViewHolder_ViewBinding(final MedicalRecordsListAdapter.MedicalRecordViewHolder target,
      View source) {
    this.target = target;

    View view;
    target.mLblMedicalRecordTitle = Utils.findRequiredViewAsType(source, R.id.lblMedicalRecordTitle, "field 'mLblMedicalRecordTitle'", AppCompatTextView.class);
    target.mLblMedicalRecordName = Utils.findRequiredViewAsType(source, R.id.lblMedicalRecordName, "field 'mLblMedicalRecordName'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnDelete, "method 'onViewClicked'");
    view2131755233 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MedicalRecordsListAdapter.MedicalRecordViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblMedicalRecordTitle = null;
    target.mLblMedicalRecordName = null;

    view2131755233.setOnClickListener(null);
    view2131755233 = null;
  }
}
