package com.twixttechnologies.tjss.view.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.broadcastreceiver.EditorBroadCastReceiver;
import com.twixttechnologies.tjss.utils.M;

/**
 * @author Sony Raj on 04-08-17.
 */

public class SecurityCircleCodeView extends LinearLayout implements TextWatcher {

    private int numChildren = 0;

    public SecurityCircleCodeView(Context context) {
        this(context, null);
    }

    public SecurityCircleCodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityCircleCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setGravity(Gravity.CENTER_HORIZONTAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SecurityCircleCodeView);
        try {
            numChildren = typedArray.getInt(R.styleable.SecurityCircleCodeView_cc_num_children, 1);
            int spaceAfter = typedArray.getInt(R.styleable.SecurityCircleCodeView_cc_space_after_every, 1);

            LayoutInflater inflater = LayoutInflater.from(context);
            for (int i = 0; i < numChildren; i++) {
                AppCompatEditText editText = (AppCompatEditText) inflater.inflate(R.layout.extra_safety_circle_text_box, this, false);
                addView(editText);
                editText.setTag(i);
                editText.addTextChangedListener(this);
                if (spaceAfter > 0) {
                    if (numChildren > 1 && i + 1 == spaceAfter && i < numChildren - 1) {
                        View view = inflater.inflate(R.layout.extra_safety_circle_separator, this, false);
                        addView(view);
                    }
                }
            }

        } catch (Exception e) {
            M.log(e.getMessage());
        } finally {
            typedArray.recycle();
        }
    }


    public String getSecurityCode() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof AppCompatEditText) {
                if (((AppCompatEditText) getChildAt(i)).getText() != null)
                    sb.append(((AppCompatEditText) getChildAt(i)).getText().toString());
                else
                    sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public void setSecurityCode(String securityCode) {
        if (securityCode.length() > numChildren)
            throw new IllegalArgumentException("length of security code should " +
                    "not be greater than numChildren");

        if (securityCode.length() <= 0) return;
        for (int i = 0; i < getChildCount(); i++) {
            try {
                if (getChildAt(i) instanceof AppCompatEditText)
                    ((AppCompatEditText) getChildAt(i)).setText(securityCode.charAt(i));
            } catch (Exception e) {
                M.log(e.getMessage());
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        AppCompatEditText current = (AppCompatEditText) findFocus();
        if (current == null) return;
        if (TextUtils.isEmpty(current.getText())) return;
        int tag = (int) current.getTag();
        int childCount = getChildCount();
        if (tag == childCount - 1) return;
        AppCompatEditText target = (AppCompatEditText) findViewWithTag(tag + 1);
        if (target == null) {
            Intent intent = new Intent(EditorBroadCastReceiver.EDITOR_ACTION);
            LocalBroadcastManager.getInstance(getContext())
                    .sendBroadcast(intent);
        } else {
            target.requestFocus();
        }
    }
}
