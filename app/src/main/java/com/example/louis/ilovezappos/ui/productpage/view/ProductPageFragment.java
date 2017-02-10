package com.example.louis.ilovezappos.ui.productpage.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.example.louis.ilovezappos.R;
import com.example.louis.ilovezappos.bindingobject.Product;
import com.example.louis.ilovezappos.databinding.FragmentProductPageBinding;
import com.example.louis.ilovezappos.framework.BaseFragment;
import com.example.louis.ilovezappos.mvpmodel.ProductModel;
import com.example.louis.ilovezappos.rest.model.ZapposProduct;
import com.example.louis.ilovezappos.ui.productpage.IProductPresenter;
import com.example.louis.ilovezappos.ui.productpage.IProductView;
import com.example.louis.ilovezappos.ui.productpage.presenter.ProductPresenterImpl;

public class ProductPageFragment extends BaseFragment implements  View.OnClickListener, IProductView{
    public static final String ARG_TERM_SEARCH = "ARG_TERM_SEARCH";

    private IProductPresenter presenter;
    private FragmentProductPageBinding binding;

    public ProductPageFragment() {
        // Required empty public constructor
    }

    public static ProductPageFragment newInstance(String term) {
        ProductPageFragment fragment = new ProductPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TERM_SEARCH, term);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String term = getArguments().getString(ARG_TERM_SEARCH);
            presenter = new ProductPresenterImpl();
            addPresenter(presenter, this, new ProductModel());
            presenter.loadProducts(term);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_page, container, false);

        final FloatingActionButton fab = (FloatingActionButton) binding.getRoot().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation show_fab_1 = AnimationUtils.loadAnimation(getContext(), R.anim.show);
                Animation hide_fab_1 = AnimationUtils.loadAnimation(getContext(), R.anim.hide);

                fab.startAnimation(hide_fab_1);
                hide_fab_1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        fab.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fab.startAnimation(show_fab_1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                show_fab_1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fab.setClickable(true);
                        CharSequence text = "Item has been added!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getContext(), text, duration);
                        toast.show();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    public void onProductLoaded(ZapposProduct zapposProduct, boolean noResults) {
        if(!noResults) {
            Product p = new Product(zapposProduct, getContext());
            binding.setProduct(p);
        }
        else{

        }
    }

    @Override
    public void onProductLoadError(String errMsg) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getContext(), errMsg, duration);
        toast.show();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
