package com.twixttechnologies.tjss.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

/**
 * @author Sony Raj on 09-09-17.
 */

public class DependencyHandler {

    /*
     * Change this to your publishable key.
     *
     * You can get your key here: https://dashboard.stripe.com/account/apikeys
     */
    private static final String PUBLISHABLE_KEY = "pk_test_cr7A44thvBpi73DTF0Ae4Tqx";

    private AsyncTaskTokenController mAsyncTaskController;
    private Context mContext;

    public DependencyHandler(
            Activity activity) {
        mContext = activity.getBaseContext();

    }

    /**
     * Attach a listener that creates a token using the {@link android.os.AsyncTask}-based method.
     * Only gets attached once, unless you call {@link #clearReferences()}.
     *
     * @return a reference to the {@link AsyncTaskTokenController}
     */
    @NonNull
    public void attachAsyncTaskTokenController(Card card,
                                               TransactionCallback<Token, Exception> transactionCallback) {
        if (mAsyncTaskController == null) {
            mAsyncTaskController = new AsyncTaskTokenController(
                    mContext,
                    PUBLISHABLE_KEY,
                    transactionCallback);
        }
        mAsyncTaskController.saveCard(card);
    }

/*    *//**
     * Attach a listener that creates a token using an {@link android.app.IntentService} and the
     * synchronous {@link com.stripe.android.Stripe#createTokenSynchronous(Card, String)} method.
     *
     * Only gets attached once, unless you call {@link #clearReferences()}.
     *
     * @param button a button that, when clicked, gets a token.
     * @return a reference to the {@link IntentServiceTokenController}
     *//*
    @NonNull
    public IntentServiceTokenController attachIntentServiceTokenController(
            AppCompatActivity appCompatActivity,
            Button button) {
        if (mIntentServiceTokenController == null) {
            mIntentServiceTokenController = new IntentServiceTokenController(
                    appCompatActivity,
                    button,
                    mCard,
                    mErrorDialogHandler,
                    mListViewController,
                    mProgresDialogController,
                    PUBLISHABLE_KEY);
        }
        return mIntentServiceTokenController;
    }*/

   /* *//**
     * Attach a listener that creates a token using a {@link rx.Subscription} and the
     * synchronous {@link com.stripe.android.Stripe#createTokenSynchronous(Card, String)} method.
     *
     * Only gets attached once, unless you call {@link #clearReferences()}.
     *
     * @param button a button that, when clicked, gets a token.
     * @return a reference to the {@link RxTokenController}
     *//*
    @NonNull
    public RxTokenController attachRxTokenController(Button button) {
        if (mRxTokenController == null) {
            mRxTokenController = new RxTokenController(
                    button,
                    mCard,
                    mContext,
                    mErrorDialogHandler,
                    mListViewController,
                    mProgresDialogController,
                    PUBLISHABLE_KEY);
        }
        return mRxTokenController;
    }*/

    /**
     * Clear all the references so that we can start over again.
     */
    public void clearReferences() {

        if (mAsyncTaskController != null) {
            mAsyncTaskController.detach();
        }
        mAsyncTaskController = null;
    }
}
