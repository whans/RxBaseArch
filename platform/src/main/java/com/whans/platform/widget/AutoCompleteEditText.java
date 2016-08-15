package com.whans.platform.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.whans.platform.R;
import com.whans.platform.core.BaseAdapterData;
import com.whans.platform.widget.autocomplete.AutoCompleteAdapter;
import com.whans.platform.widget.autocomplete.AutoCompleteDataHandler;
import com.whans.platform.widget.autocomplete.AutoCompleteTextView;

/**
 * @author hanson.
 */

public class AutoCompleteEditText extends FrameLayout {

    private AutoCompleteTextView mAutoCompleteTxt;

    private AutoCompleteDataHandler mAutoCompleteDataHandler;
    private AutoCompleteAdapter mAutoCompleteAdapter;

    public AutoCompleteEditText(Context context) {
        super(context);
        init(context, null);
    }

    public AutoCompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoCompleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public String getText() {
        return mAutoCompleteTxt.getText().toString();
    }

    public void setText(String text) {
        mAutoCompleteTxt.setAdapter(null);
        mAutoCompleteTxt.setText(text);
        mAutoCompleteTxt.setAdapter(mAutoCompleteAdapter);
    }

    public void setHint(@StringRes int hintId) {
        mAutoCompleteTxt.setHint(hintId);
    }

    public void setHint(String hint) {
        mAutoCompleteTxt.setHint(hint);
    }

    public void setThreshold(int threshold) {
        mAutoCompleteTxt.setThreshold(threshold);
    }

    public void setAutoCompleteDataHandler(AutoCompleteDataHandler mAutoCompleteDataHandler) {
        this.mAutoCompleteDataHandler = mAutoCompleteDataHandler;
        mAutoCompleteAdapter.setAutoCompleteDataHandler(mAutoCompleteDataHandler);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.widget_autocomplete_edt, this);

        mAutoCompleteTxt = (AutoCompleteTextView) findViewById(R.id.autocomplete_txt);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.AutoCompleteEditText);
            if (typedArray.hasValue(R.styleable.AutoCompleteEditText_app_txt_hint)) {
                mAutoCompleteTxt.setHint(typedArray.
                        getResourceId(R.styleable.AutoCompleteEditText_app_txt_hint, 0));
            }
            typedArray.recycle();
        }

        mAutoCompleteTxt.setLoadingIndicator(progressBar);
        mAutoCompleteAdapter = new AutoCompleteAdapter(context);
        mAutoCompleteTxt.setThreshold(2);
        mAutoCompleteTxt.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteTxt.setOnItemClickListener((parent, view, position, id) -> {
            if (mAutoCompleteDataHandler != null) {
                BaseAdapterData data = (BaseAdapterData) mAutoCompleteAdapter.getSelectItem(position);
                mAutoCompleteDataHandler.onItemSelect(data);
            }

        });
    }
}
