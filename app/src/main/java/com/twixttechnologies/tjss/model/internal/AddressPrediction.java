package com.twixttechnologies.tjss.model.internal;

import com.google.android.gms.location.places.AutocompletePrediction;

/**
 * @author Sony Raj on 21-09-17.
 */

public class AddressPrediction {

    private CharSequence fullText;
    private CharSequence primaryText;
    private CharSequence secondaryText;
    private String placeId;


    public AddressPrediction(AutocompletePrediction prediction) {
        if (prediction != null) {
            fullText = prediction.getFullText(null);
            primaryText = prediction.getPrimaryText(null);
            secondaryText = prediction.getSecondaryText(null);
            placeId = prediction.getPlaceId();
        }
    }


    public CharSequence getFullText() {
        return fullText;
    }

    public CharSequence getPrimaryText() {
        return primaryText;
    }

    public CharSequence getSecondaryText() {
        return secondaryText;
    }

    public String getPlaceId() {
        return placeId;
    }

    @Override
    public String toString() {
        return fullText.toString();
    }
}
