package com.example.louis.ilovezappos.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.louis.ilovezappos.R;
import com.example.louis.ilovezappos.Util.L;
import com.example.louis.ilovezappos.framework.BaseFragment;

public class MainSearchFragment extends BaseFragment implements  View.OnClickListener{

    private ViewGroup vgSearchBar;
    private OnMainSearchFragmentInteractionListener mListener;
    public static final int REQUEST_CODE_SEARCH = 1;

    public MainSearchFragment() {
        // Required empty public constructor
    }

    public static MainSearchFragment newInstance() {
        MainSearchFragment fragment = new MainSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_main_search, container, false);
        vgSearchBar = (ViewGroup) v.findViewById(R.id.vg_search_bar_from_search_fragment);
        vgSearchBar.setOnClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainSearchFragmentInteractionListener) {
            mListener = (OnMainSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainSearchFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == vgSearchBar){
            Intent intent = new Intent(getActivity(), SearchZappos.class);
            startActivityForResult(intent, REQUEST_CODE_SEARCH);
            getActivity().overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_SEARCH && resultCode == Activity.RESULT_OK) {
            String term = data.getStringExtra(SearchZappos.REQUEST_TERM_SEARCH);
            mListener.onSearchItem(term);
        }
    }

    public interface OnMainSearchFragmentInteractionListener {
        void onSearchItem(String item);
    }
}
