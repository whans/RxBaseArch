package com.whans.platform.widget.autocomplete;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * @author hanson.
 */

public class AutoCompleteTextView extends AppCompatAutoCompleteTextView {
    private static final int MESSAGE_TEXT_CHANGED = 100;
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 1000;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            AutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };
    private int mAutoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
    private ProgressBar mLoadingIndicator;

    public AutoCompleteTextView(Context context) {
        super(context);
    }

    public AutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(VISIBLE);
        }

        mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text), mAutoCompleteDelay);
    }

    @Override
    public void onFilterComplete(int count) {
        mLoadingIndicator.setVisibility(GONE);
        super.onFilterComplete(count);
    }

    public void setLoadingIndicator(ProgressBar progressBar) {
        this.mLoadingIndicator = progressBar;
    }

    public void setAutoCompleteDelay(int mAutoCompleteDelay) {
        this.mAutoCompleteDelay = mAutoCompleteDelay;
    }
}
