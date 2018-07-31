package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.payment.FragmentPlansPayment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Anyone who calls this activity should pass
 * the payment type with key {@link #PAYMENT_TYPE}
 * and values should be one from {@link PaymentType},
 * <p>
 * <ul>
 * Possible values are {@link #PAYMENT_TYPE_PLAN} and {@link #PAYMENT_TYPE_COINS}
 * </ul>
 * <p>
 * <ul>
 * if the selected payment type is {@link #PAYMENT_TYPE_PLAN} then pass in
 * <li>
 * plain id with key {@link #PLAIN_ID}
 * </li>
 * <li>
 * Stripe plan with key {@link #STRIPE_PLAN}
 * </li>
 * </ul>
 * <p>
 * <ul>
 * if the selected plan typ eis {@link #PAYMENT_TYPE_COINS} then pass in
 * <li>
 * number of coins to purchase with key {@link #NUMBER_OF_COINS}
 * <em> Should be multiple of 100</em>
 * </li>
 * </ul>
 */
public class PaymentActivity extends BaseActivity {

    public static final String PAYMENT_TYPE = "paymentType";
    public static final String PLAIN_ID = "planId";
    public static final String STRIPE_PLAN = "stripePlan";
    public static final String NUMBER_OF_COINS = "numberOfCoins";
    public static final String PLAN_NAME ="planName";
    public static final int INVALID = -1;
    public static final int PAYMENT_TYPE_PLAN = 1;
    public static final int PAYMENT_TYPE_COINS = 2;

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment;

        Intent callingIntent = getIntent();

        @PaymentType
        int callingFor = callingIntent.getIntExtra(PAYMENT_TYPE, INVALID);
        if (callingFor == INVALID) {
            throw new IllegalArgumentException("Should have a valid payment type");
        }

        Bundle b = new Bundle();
        if (callingFor == PAYMENT_TYPE_PLAN) {
            String planId = callingIntent.getStringExtra(PLAIN_ID);
            String stripePlan = callingIntent.getStringExtra(STRIPE_PLAN);
            String planName = callingIntent.getStringExtra(PLAN_NAME);
            b.putString(PLAIN_ID, planId);
            b.putString(STRIPE_PLAN, stripePlan);
            b.putString(PLAN_NAME,planName);
        } else if (callingFor == PAYMENT_TYPE_COINS) {
            int numberOfCoins = callingIntent.getIntExtra(NUMBER_OF_COINS, INVALID);
            if (numberOfCoins == INVALID || numberOfCoins % 100 != 0) {
                throw new IllegalArgumentException("Number of coins should be multiple of 100");
            }
            b.putInt(NUMBER_OF_COINS, numberOfCoins);
        }

        b.putInt(PAYMENT_TYPE, callingFor);

        fragment = FragmentPlansPayment.newInstance(b);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentPlansPayment.class.getName())
                .commit();

    }

    @IntDef({INVALID, PAYMENT_TYPE_PLAN, PAYMENT_TYPE_COINS})
    @Retention(RetentionPolicy.SOURCE)
    @interface PaymentType {
    }


}
