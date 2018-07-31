package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;

/**
 * @author Sony Raj on 04-08-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public abstract class SignUpFragmentBase extends BaseFragment {

    @Override
    public void onPause() {
        super.onPause();
        setDataOnPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataOnResume();
    }

    abstract void setDataOnPause();

    abstract void setDataOnResume();

}
