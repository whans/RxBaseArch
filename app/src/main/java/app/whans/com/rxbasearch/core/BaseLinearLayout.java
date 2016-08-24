package app.whans.com.rxbasearch.core;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

/**
 * @author hanson.
 */

public abstract class BaseLinearLayout extends LinearLayout {
    private boolean mIsViewInit = false;

    public BaseLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.bind(this);
        mIsViewInit = true;
        onViewInit();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIsViewInit = false;
        onViewRemove();
    }

    public boolean isViewInited() {
        return mIsViewInit;
    }

    public int getTitleResId() {
        return 0;
    }

    public abstract void init(Context context);

    public abstract void onViewInit();

    public abstract void onViewRemove();

    public void onReload() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
