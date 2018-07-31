package com.innoviussoftwaresolution.tjss.view.activity;

import android.view.MenuItem;

/**
 * @author Sony Raj on 30/06/17.
 *         <p>
 *         <p>
 *         Activities that contain a toolbar should implement this activity<br/>
 *         If an activity extends this class and a toolbar is absent, be ready for a
 *         {@link com.innoviussoftwaresolution.tjss.exception.NoToolbarFoundException} at runtime
 */

abstract class BaseActivityWithToolbar extends BaseActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
