package com.twixttechnologies.tjss.model.internal;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * @author Sony Raj on 13-09-17.
 */

public class FaqQuestion extends ExpandableGroup<FaqAnswer> {

    public FaqQuestion(String title, List<FaqAnswer> items) {
        super(title, items);
    }
}
