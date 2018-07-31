package com.twixttechnologies.tjss.model.internal;

import com.google.maps.android.clustering.ClusterItem;

/**
 * @author Sony Raj on 10/11/17.
 */

public interface ClusterItemBase extends ClusterItem {

    String getImage();

    String getTitle();

    String getId();

    String[] createParts();

    int zIndex();

    void setZIndex(int zIndex);

}
