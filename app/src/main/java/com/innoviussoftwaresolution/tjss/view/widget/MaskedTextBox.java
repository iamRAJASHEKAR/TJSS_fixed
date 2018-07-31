package com.innoviussoftwaresolution.tjss.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;

/**
 * @author Sony Raj on 07-09-17.
 */
@Deprecated
public class MaskedTextBox extends AppCompatEditText implements TextWatcher {

    private String mMaskPattern = null;
    private String mPatternCharacter = "*";

    //including space
    private int mMaxCharacterLength = 0;
    private boolean mIgnoreEvents = false;
    private boolean mHavePrefix = false;

    public MaskedTextBox(Context context) {
        this(context, null);
    }

    public MaskedTextBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskedTextBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaskedTextBox);
        try {
            mMaskPattern = typedArray.getString(R.styleable.MaskedTextBox_msk_pattern);
            mPatternCharacter = typedArray.getString(R.styleable.MaskedTextBox_msk_pattern_character);
            mMaxCharacterLength = typedArray.getInt(R.styleable.MaskedTextBox_msk_max_characters, 0);
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            M.log(e.getMessage());
        } finally {
            if (typedArray != null) typedArray.recycle();
        }

        mHavePrefix = mMaskPattern != null && mMaskPattern.substring(0, mPatternCharacter.length()).equals(mPatternCharacter);


    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mIgnoreEvents = false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > mMaxCharacterLength) {
            String temp = s.toString().substring(0, s.length() - 1);
            setText(temp);
            mIgnoreEvents = true;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (mIgnoreEvents) {
            mIgnoreEvents = false;
            return;
        }

        if (mMaskPattern == null) {
            //no pattern so display the text as is
            return;
        }
        final String pattern = mMaskPattern;
        final String patternCharacter = mPatternCharacter;
        //add the prefix characters (if there is any) in the pattern
        //for example, if the patterns is "+91 **** *** ***", we have to place "+91 "
        //(note the space after "1") before the text first character is typed into the
        //textBox/editText, So add it


    }
}
