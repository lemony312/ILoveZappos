package com.example.louis.ilovezappos.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.louis.ilovezappos.R;
import com.example.louis.ilovezappos.Util.L;
import com.example.louis.ilovezappos.framework.BaseActivity;

public class SearchZappos extends BaseActivity implements  View.OnClickListener{

    private EditText search;
    private ImageView clearBtn;
    private View vgClose;

    public static final String REQUEST_TERM_SEARCH = "REQUEST_TERM_SEARCH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_zappos);
        setShowUpButton(true);
        setupWhiteActionBar();
        search = (EditText) findViewById(R.id.et_search);
        search.addTextChangedListener(filterTextWatcher);
        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(REQUEST_TERM_SEARCH,  search.getText().toString());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    return true;
                }
                return false;
            }
        });

        clearBtn = (ImageView) findViewById(R.id.delete_from_search_ed);
        clearBtn.setOnClickListener(this);

        vgClose = (View) findViewById(R.id.vg_touch_close);
        vgClose.setOnClickListener(this);
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int visibility = (TextUtils.isEmpty(s)) ? View.GONE : View.VISIBLE;
            clearBtn.setVisibility(visibility);
        }
    };

    @Override
    public void onClick(View view) {
        if (view == clearBtn) {
            search.setText(null);
            search.requestFocus();
        }
        else if(view == vgClose){
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, android.R.anim.slide_out_right);
    }
}
