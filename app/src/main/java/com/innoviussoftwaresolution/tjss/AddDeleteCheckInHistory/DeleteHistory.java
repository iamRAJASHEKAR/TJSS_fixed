package com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 4/3/2018.
 */

public class DeleteHistory {
    @SerializedName("id")
    String id;

    public DeleteHistory() {
    }

    public DeleteHistory(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
