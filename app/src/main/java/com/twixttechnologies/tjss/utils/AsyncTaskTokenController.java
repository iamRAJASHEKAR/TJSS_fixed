package com.twixttechnologies.tjss.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

/**
 * @author Sony Raj on 09-09-17.
 */

public class AsyncTaskTokenController {

    private final TransactionCallback<Token, Exception> mTransactionCallback;
    private Card mCard;
    private Context mContext;
    private String mPublishableKey;

    public AsyncTaskTokenController(
            @NonNull Context context,
            @NonNull String publishableKey,
            @NonNull TransactionCallback<Token, Exception> callback) {
        mContext = context;
        mPublishableKey = publishableKey;
        mTransactionCallback = callback;
    }

    public void detach() {
        mCard = null;
    }

    void saveCard(Card card) {
        mCard = card;
        final Card cardToSave = mCard;
        if (cardToSave == null) {
            try {
                Toast.makeText(mContext, "Invalid card Data", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                M.log(e.getMessage());
            }
            return;
        }
        new Stripe(mContext).createToken(
                cardToSave,
                mPublishableKey,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        mTransactionCallback.onSuccess(token);
                    }

                    public void onError(Exception error) {
                        mTransactionCallback.onFailure(error);
                    }
                });
    }

}
