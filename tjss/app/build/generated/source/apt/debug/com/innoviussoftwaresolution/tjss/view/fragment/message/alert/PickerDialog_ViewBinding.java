// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.message.alert;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CompoundButton;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PickerDialog_ViewBinding implements Unbinder {
  private PickerDialog target;

  private View view2131755464;

  private View view2131755465;

  private View view2131755467;

  private View view2131755468;

  private View view2131755471;

  private View view2131755470;

  @UiThread
  public PickerDialog_ViewBinding(final PickerDialog target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.rdoPickerImageGallery, "method 'onCheckedChanged'");
    view2131755464 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rdoPickerImageCamera, "method 'onCheckedChanged'");
    view2131755465 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rdoPickerVideoGallery, "method 'onCheckedChanged'");
    view2131755467 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rdoPickerVideoCamera, "method 'onCheckedChanged'");
    view2131755468 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rdoPickerAudioRecord, "method 'onCheckedChanged'");
    view2131755471 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rdoPickerAudioGallery, "method 'onCheckedChanged'");
    view2131755470 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(p0, p1);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    ((CompoundButton) view2131755464).setOnCheckedChangeListener(null);
    view2131755464 = null;
    ((CompoundButton) view2131755465).setOnCheckedChangeListener(null);
    view2131755465 = null;
    ((CompoundButton) view2131755467).setOnCheckedChangeListener(null);
    view2131755467 = null;
    ((CompoundButton) view2131755468).setOnCheckedChangeListener(null);
    view2131755468 = null;
    ((CompoundButton) view2131755471).setOnCheckedChangeListener(null);
    view2131755471 = null;
    ((CompoundButton) view2131755470).setOnCheckedChangeListener(null);
    view2131755470 = null;
  }
}
