package com.innoviussoftwaresolution.tjss.model.internal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Sony Raj on 30-09-17.
 */

public class ChatInfo implements Parcelable {

    public static final Creator<ChatInfo> CREATOR = new Creator<ChatInfo>() {
        @Override
        public ChatInfo createFromParcel(Parcel in) {
            return new ChatInfo(in);
        }

        @Override
        public ChatInfo[] newArray(int size) {
            return new ChatInfo[size];
        }
    };
    public String type;
    public String payload;


    public ChatInfo() {
    }

    protected ChatInfo(Parcel in) {
        type = in.readString();
        payload = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(payload);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "ChatInfo{" +
                "type='" + type + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
