package com.innoviussoftwaresolution.tjss.model.internal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Sony Raj on 13-09-17.
 */

public class FaqAnswer implements Parcelable {

    public static final Creator<FaqAnswer> CREATOR = new Creator<FaqAnswer>() {
        @Override
        public FaqAnswer createFromParcel(Parcel in) {
            return new FaqAnswer(in);
        }

        @Override
        public FaqAnswer[] newArray(int size) {
            return new FaqAnswer[size];
        }
    };
    private final String answer;


    public FaqAnswer(String answer) {
        this.answer = answer;
    }

    public FaqAnswer(Parcel in) {
        answer = in.readString();
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
