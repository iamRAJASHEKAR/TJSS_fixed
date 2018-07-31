// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.home;

import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentHome_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentHome target;

  private View view2131755235;

  private View view2131755242;

  private View view2131755245;

  private View view2131755246;

  private View view2131755251;

  private View view2131755252;

  private View view2131755249;

  private View view2131755254;

  private View view2131755255;

  private View view2131755257;

  private View view2131755264;

  private View view2131755259;

  private View view2131755260;

  private View view2131755261;

  private View view2131755265;

  private View view2131755422;

  private View view2131755256;

  private View view2131755430;

  private View view2131755429;

  private View view2131755428;

  private View view2131755433;

  private View view2131755431;

  private View view2131755253;

  private View view2131755258;

  private View view2131755435;

  private View view2131755434;

  private View view2131755432;

  @UiThread
  public FragmentHome_ViewBinding(final FragmentHome target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mHomeTabs = Utils.findRequiredViewAsType(source, R.id.homeTabs, "field 'mHomeTabs'", TabLayout.class);
    target.mHomePullOutView = Utils.findRequiredViewAsType(source, R.id.homePullOutView, "field 'mHomePullOutView'", CardView.class);
    view = Utils.findRequiredView(source, R.id.imgDrawerProfileImage, "field 'mImgDrawerProfileImage' and method 'onViewClicked'");
    target.mImgDrawerProfileImage = Utils.castView(view, R.id.imgDrawerProfileImage, "field 'mImgDrawerProfileImage'", CircleImageView.class);
    view2131755235 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mLblDrawerUsername = Utils.findRequiredViewAsType(source, R.id.lblDrawerUsername, "field 'mLblDrawerUsername'", AppCompatTextView.class);
    target.mLblDrawerEmail = Utils.findRequiredViewAsType(source, R.id.lblDrawerEmail, "field 'mLblDrawerEmail'", AppCompatTextView.class);
    target.mLblSelectedPlanName = Utils.findRequiredViewAsType(source, R.id.lblSelectedPlanName, "field 'mLblSelectedPlanName'", AppCompatTextView.class);
    target.mLblDrawerPrimaryCircleName = Utils.findRequiredViewAsType(source, R.id.lblDrawerPrimaryCircleName, "field 'mLblDrawerPrimaryCircleName'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnExpandOrCollapseCircles, "field 'mBtnExpandCircles' and method 'onViewClicked'");
    target.mBtnExpandCircles = Utils.castView(view, R.id.btnExpandOrCollapseCircles, "field 'mBtnExpandCircles'", AppCompatImageButton.class);
    view2131755242 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerCreateCircle, "field 'mBtnDrawerCreateCircle' and method 'onViewClicked'");
    target.mBtnDrawerCreateCircle = Utils.castView(view, R.id.btnDrawerCreateCircle, "field 'mBtnDrawerCreateCircle'", AppCompatButton.class);
    view2131755245 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerJoinCircle, "field 'mBtnDrawerJoinCircle' and method 'onViewClicked'");
    target.mBtnDrawerJoinCircle = Utils.castView(view, R.id.btnDrawerJoinCircle, "field 'mBtnDrawerJoinCircle'", AppCompatButton.class);
    view2131755246 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerMessages, "field 'mBtnDrawerMessages' and method 'onViewClicked'");
    target.mBtnDrawerMessages = Utils.castView(view, R.id.btnDrawerMessages, "field 'mBtnDrawerMessages'", AppCompatButton.class);
    view2131755251 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerHelpAlert, "field 'mBtnDrawerHelpAlert' and method 'onViewClicked'");
    target.mBtnDrawerHelpAlert = Utils.castView(view, R.id.btnDrawerHelpAlert, "field 'mBtnDrawerHelpAlert'", AppCompatButton.class);
    view2131755252 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerPlans, "field 'mBtnDrawerPlans' and method 'onViewClicked'");
    target.mBtnDrawerPlans = Utils.castView(view, R.id.btnDrawerPlans, "field 'mBtnDrawerPlans'", AppCompatButton.class);
    view2131755249 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerFavoritePlaces, "field 'mBtnDrawerFavoritePlaces' and method 'onViewClicked'");
    target.mBtnDrawerFavoritePlaces = Utils.castView(view, R.id.btnDrawerFavoritePlaces, "field 'mBtnDrawerFavoritePlaces'", AppCompatButton.class);
    view2131755254 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerLocationSharing, "field 'mBtnDrawerLocationSharing' and method 'onViewClicked'");
    target.mBtnDrawerLocationSharing = Utils.castView(view, R.id.btnDrawerLocationSharing, "field 'mBtnDrawerLocationSharing'", AppCompatButton.class);
    view2131755255 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerCoins, "field 'mBtnDrawerDrawerCoins' and method 'onViewClicked'");
    target.mBtnDrawerDrawerCoins = Utils.castView(view, R.id.btnDrawerCoins, "field 'mBtnDrawerDrawerCoins'", AppCompatButton.class);
    view2131755257 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerSettings, "field 'mBtnDrawerSettings' and method 'onViewClicked'");
    target.mBtnDrawerSettings = Utils.castView(view, R.id.btnDrawerSettings, "field 'mBtnDrawerSettings'", AppCompatButton.class);
    view2131755264 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerSecurityQuestion, "field 'mBtnDrawerSecurityQuestion' and method 'onViewClicked'");
    target.mBtnDrawerSecurityQuestion = Utils.castView(view, R.id.btnDrawerSecurityQuestion, "field 'mBtnDrawerSecurityQuestion'", AppCompatButton.class);
    view2131755259 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerCheckIn, "field 'mBtnDrawerCheckIn' and method 'onViewClicked'");
    target.mBtnDrawerCheckIn = Utils.castView(view, R.id.btnDrawerCheckIn, "field 'mBtnDrawerCheckIn'", AppCompatButton.class);
    view2131755260 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerHelpAlertHistory, "field 'mBtnDrawerHelpAlertHistory' and method 'onViewClicked'");
    target.mBtnDrawerHelpAlertHistory = Utils.castView(view, R.id.btnDrawerHelpAlertHistory, "field 'mBtnDrawerHelpAlertHistory'", AppCompatButton.class);
    view2131755261 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerFaqs, "field 'mBtnDrawerFaqs' and method 'onViewClicked'");
    target.mBtnDrawerFaqs = Utils.castView(view, R.id.btnDrawerFaqs, "field 'mBtnDrawerFaqs'", AppCompatButton.class);
    view2131755265 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mDrawerLayout = Utils.findRequiredViewAsType(source, R.id.drawerLayout, "field 'mDrawerLayout'", DrawerLayout.class);
    target.mLtSafetyCirclesHolder = Utils.findRequiredViewAsType(source, R.id.ltSafetyCirclesHolder, "field 'mLtSafetyCirclesHolder'", LinearLayout.class);
    target.mLtSafetyCirclesHolderMain = Utils.findRequiredViewAsType(source, R.id.ltSafetyCirclesHolderMain, "field 'mLtSafetyCirclesHolderMain'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tglShowMembersOfSafetyCircle, "field 'mTglShowMembersOfSafetyCircle' and method 'onCheckChanged'");
    target.mTglShowMembersOfSafetyCircle = Utils.castView(view, R.id.tglShowMembersOfSafetyCircle, "field 'mTglShowMembersOfSafetyCircle'", SwitchCompat.class);
    view2131755422 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckChanged(p1);
      }
    });
    target.mLtShowMembersOfSafetyCircle = Utils.findRequiredViewAsType(source, R.id.ltShowMembersOfSafetyCircle, "field 'mLtShowMembersOfSafetyCircle'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btnDrawerSafetyTips, "field 'mBtnDrawerSafetyTips' and method 'onViewClicked'");
    target.mBtnDrawerSafetyTips = Utils.castView(view, R.id.btnDrawerSafetyTips, "field 'mBtnDrawerSafetyTips'", AppCompatButton.class);
    view2131755256 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lblViewAllSelectedTabList, "field 'mLblViewAllSelectedTabList' and method 'onViewClicked'");
    target.mLblViewAllSelectedTabList = Utils.castView(view, R.id.lblViewAllSelectedTabList, "field 'mLblViewAllSelectedTabList'", AppCompatTextView.class);
    view2131755430 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mDrawerLeftMarginHack = Utils.findRequiredView(source, R.id.drawerLeftMarginHack, "field 'mDrawerLeftMarginHack'");
    view = Utils.findRequiredView(source, R.id.imgPullArrow, "field 'mImgPullArrow' and method 'onViewClicked'");
    target.mImgPullArrow = Utils.castView(view, R.id.imgPullArrow, "field 'mImgPullArrow'", AppCompatImageView.class);
    view2131755429 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnTabPullOutSeparator, "field 'mBtnTabPullOutSeparator' and method 'onViewClicked'");
    target.mBtnTabPullOutSeparator = Utils.castView(view, R.id.btnTabPullOutSeparator, "field 'mBtnTabPullOutSeparator'", AppCompatImageButton.class);
    view2131755428 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mLblUnreadMessagesCount = Utils.findRequiredViewAsType(source, R.id.lblUnreadMessageCount, "field 'mLblUnreadMessagesCount'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.imgPullArrowServiceProviders, "field 'mImgPullArrowServiceProviders' and method 'onViewClicked'");
    target.mImgPullArrowServiceProviders = Utils.castView(view, R.id.imgPullArrowServiceProviders, "field 'mImgPullArrowServiceProviders'", AppCompatImageView.class);
    view2131755433 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.homePullOutViewServiceProviders, "field 'mHomePullOutViewServiceProviders' and method 'onViewClicked'");
    target.mHomePullOutViewServiceProviders = Utils.castView(view, R.id.homePullOutViewServiceProviders, "field 'mHomePullOutViewServiceProviders'", CardView.class);
    view2131755431 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerServiceProviders, "method 'onViewClicked'");
    view2131755253 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDrawerInAppPurchase, "method 'onViewClicked'");
    view2131755258 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnHomeSendHelpAlert, "method 'onViewClicked'");
    view2131755435 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnTabPullOutSeparatorServiceProviders, "method 'onViewClicked'");
    view2131755434 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lblViewAllServiceProviders, "method 'onViewClicked'");
    view2131755432 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    FragmentHome target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mHomeTabs = null;
    target.mHomePullOutView = null;
    target.mImgDrawerProfileImage = null;
    target.mLblDrawerUsername = null;
    target.mLblDrawerEmail = null;
    target.mLblSelectedPlanName = null;
    target.mLblDrawerPrimaryCircleName = null;
    target.mBtnExpandCircles = null;
    target.mBtnDrawerCreateCircle = null;
    target.mBtnDrawerJoinCircle = null;
    target.mBtnDrawerMessages = null;
    target.mBtnDrawerHelpAlert = null;
    target.mBtnDrawerPlans = null;
    target.mBtnDrawerFavoritePlaces = null;
    target.mBtnDrawerLocationSharing = null;
    target.mBtnDrawerDrawerCoins = null;
    target.mBtnDrawerSettings = null;
    target.mBtnDrawerSecurityQuestion = null;
    target.mBtnDrawerCheckIn = null;
    target.mBtnDrawerHelpAlertHistory = null;
    target.mBtnDrawerFaqs = null;
    target.mDrawerLayout = null;
    target.mLtSafetyCirclesHolder = null;
    target.mLtSafetyCirclesHolderMain = null;
    target.mTglShowMembersOfSafetyCircle = null;
    target.mLtShowMembersOfSafetyCircle = null;
    target.mBtnDrawerSafetyTips = null;
    target.mLblViewAllSelectedTabList = null;
    target.mDrawerLeftMarginHack = null;
    target.mImgPullArrow = null;
    target.mBtnTabPullOutSeparator = null;
    target.mLblUnreadMessagesCount = null;
    target.mImgPullArrowServiceProviders = null;
    target.mHomePullOutViewServiceProviders = null;

    view2131755235.setOnClickListener(null);
    view2131755235 = null;
    view2131755242.setOnClickListener(null);
    view2131755242 = null;
    view2131755245.setOnClickListener(null);
    view2131755245 = null;
    view2131755246.setOnClickListener(null);
    view2131755246 = null;
    view2131755251.setOnClickListener(null);
    view2131755251 = null;
    view2131755252.setOnClickListener(null);
    view2131755252 = null;
    view2131755249.setOnClickListener(null);
    view2131755249 = null;
    view2131755254.setOnClickListener(null);
    view2131755254 = null;
    view2131755255.setOnClickListener(null);
    view2131755255 = null;
    view2131755257.setOnClickListener(null);
    view2131755257 = null;
    view2131755264.setOnClickListener(null);
    view2131755264 = null;
    view2131755259.setOnClickListener(null);
    view2131755259 = null;
    view2131755260.setOnClickListener(null);
    view2131755260 = null;
    view2131755261.setOnClickListener(null);
    view2131755261 = null;
    view2131755265.setOnClickListener(null);
    view2131755265 = null;
    ((CompoundButton) view2131755422).setOnCheckedChangeListener(null);
    view2131755422 = null;
    view2131755256.setOnClickListener(null);
    view2131755256 = null;
    view2131755430.setOnClickListener(null);
    view2131755430 = null;
    view2131755429.setOnClickListener(null);
    view2131755429 = null;
    view2131755428.setOnClickListener(null);
    view2131755428 = null;
    view2131755433.setOnClickListener(null);
    view2131755433 = null;
    view2131755431.setOnClickListener(null);
    view2131755431 = null;
    view2131755253.setOnClickListener(null);
    view2131755253 = null;
    view2131755258.setOnClickListener(null);
    view2131755258 = null;
    view2131755435.setOnClickListener(null);
    view2131755435 = null;
    view2131755434.setOnClickListener(null);
    view2131755434 = null;
    view2131755432.setOnClickListener(null);
    view2131755432 = null;

    super.unbind();
  }
}
