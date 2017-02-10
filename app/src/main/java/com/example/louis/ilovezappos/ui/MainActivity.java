package com.example.louis.ilovezappos.ui;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.louis.ilovezappos.R;
import com.example.louis.ilovezappos.Util.L;
import com.example.louis.ilovezappos.framework.BaseActivity;
import com.example.louis.ilovezappos.ui.productpage.view.ProductPageFragment;
import com.example.louis.ilovezappos.ui.search.MainSearchFragment;

public class MainActivity extends BaseActivity implements IMainActivity, MainSearchFragment.OnMainSearchFragmentInteractionListener {

    private boolean isToolbarHidden;
    private boolean fitsSystemWindows;

    private static String SEARCH_FRAG_ID = "SEARCH_FRAG_ID";
    private static String PRODUCT_FRAG_ID = "PRODUCT_FRAG_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openHomeFrag();

    }

    public void openHomeFrag(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainSearchFragment frag = MainSearchFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment_content, frag, SEARCH_FRAG_ID).addToBackStack(null).commit();
    }

    public void openProductPage(String term){
        setShowUpButton(true);
        setToolBarTitle(term);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ProductPageFragment frag = ProductPageFragment.newInstance(term);
        fragmentTransaction.replace(R.id.fragment_content, frag, PRODUCT_FRAG_ID).addToBackStack(null).commit();
    }

    @Override
    public void setToolbarHidden(boolean hideToolbar, boolean fitsSystemWindows) {
        if (hideToolbar == isToolbarHidden && this.fitsSystemWindows == fitsSystemWindows) {
            return;
        }
        isToolbarHidden = hideToolbar;
        this.fitsSystemWindows = fitsSystemWindows;
        if (hideToolbar) {
            findViewById(R.id.app_bar_layout_from_main).setVisibility(View.GONE);// hide actionbar
            findViewById(R.id.coordinator_layout_from_main).setFitsSystemWindows(fitsSystemWindows);// make content under statusbar
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.app_bar_layout_from_main).getLayoutParams();
            params.setBehavior(null);
        } else {
            findViewById(R.id.app_bar_layout_from_main).setVisibility(View.VISIBLE);
            findViewById(R.id.coordinator_layout_from_main).setFitsSystemWindows(fitsSystemWindows);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.app_bar_layout_from_main).getLayoutParams();
            params.setBehavior(new AppBarLayout.ScrollingViewBehavior());

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSearchItem(String item) {
        openProductPage(item);
        L.tmp("product page started");
    }
}
