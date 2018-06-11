package com.example.govin.cameraex;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import java.io.File;


public abstract class BaseActivity extends AppCompatActivity implements BaseView,
        AppConstants {

    public static final int TRANSACTION_TYPE_ADD = 0;
    public static final int TRANSACTION_TYPE_REPLACE = 1;
    public static final int TRANSACTION_TYPE_REMOVE = 2;

    protected abstract int getLayoutId();

    protected abstract void initializePresenter();

    protected abstract void onCreateView(Bundle saveInstanceState);

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(getLayoutId());

        initializePresenter();

        onCreateView(savedInstanceState);
    }

    public void loadImageUsingGlide(String imagePath, ImageView imageView) {

        if (imagePath.contains(WEB_IMAGE)) {
            Glide.with(this).load(imagePath).into(imageView);
            return;
        }
        Glide.with(this).load(new File(imagePath))
                .into(imageView);
    }



    @Override
    public void showProgress(String message) {
        hideProgress();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void showErrorMessage(String errorMessage) {
    }








    public void replaceFragmentAndAddToStackWithTargetFragment(
            Class<? extends Fragment> claz,
            Fragment targetFragment,
            int targetFragmentRequestCode, Bundle bundle,
            int animIn, int animOut, int transactionType) {
        // Code to replace the currently shown fragment with another one
        replaceFragment(claz, targetFragment,
                targetFragmentRequestCode, bundle, animIn, animOut, transactionType, true);
    }

    public Fragment replaceFragment(Class<? extends Fragment> claz,
                                    Bundle bundle) {
        return replaceFragment(claz, null, -1, bundle, 0, 0, TRANSACTION_TYPE_REPLACE, false);
    }

    @Nullable
    private Fragment replaceFragment(Class<? extends Fragment> claz, Fragment targetFragment,
                                     int targetFragmentRequestCode, Bundle bundle,
                                     int animIn, int animOut, int transactionType, boolean
                                                 backStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fragment fragment = fragmentManager.findFragmentByTag(claz.getCanonicalName());
        Fragment fragment = null;
        try {
            fragment = (Fragment) Class.forName(claz.getCanonicalName()).newInstance();
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragment == null) {
            return fragment;
        }

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if (targetFragment != null && targetFragmentRequestCode != -1) {
            fragment.setTargetFragment(targetFragment, targetFragmentRequestCode);
        }

        if (animIn != 0 && animOut != 0) {
            fragmentTransaction.setCustomAnimations(animIn, animOut);
        }

        if (backStack) {
            fragmentTransaction.addToBackStack(claz.getClass().getName());
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        return fragment;
    }


}
