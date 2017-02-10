package com.example.louis.ilovezappos.framework;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewParent;

import com.example.louis.ilovezappos.R;
import com.example.louis.ilovezappos.Util.L;

/**
 * Created by Louis on 2/2/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected Toolbar toolbar;

    private PresenterHost presenterHost;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterHost = new ActivityPresenterHost(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void setShowUpButton(boolean b) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);
    }

    private Drawable whiteArrow;

    public void setupWhiteActionBar() {
        if (toolbar == null) {
            return;
        }
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        toolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));

        ViewParent parent = toolbar.getParent();
        while (!(parent instanceof CoordinatorLayout)) {
            parent = parent.getParent();
        }


        if (whiteArrow == null) {
            whiteArrow = toolbar.getNavigationIcon();
        }

        toolbar.setNavigationIcon(R.drawable.ic_back_arrow_blue);
    }

    protected void setToolBarTitle(String str) {
        toolbar.setTitle(str);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
