package com.whans.platform.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;

import com.whans.platform.R;

/**
 * @author hanson.
 */

public class PasswordEditText extends AppCompatEditText {
    private boolean mIsShowingPassword = false;

    private int mVisibilityIndicatorShow = R.drawable.ic_password_edt_show;
    private int mVisibilityIndicatorHide = R.drawable.ic_password_edt_hide;

    public PasswordEditText(Context context) {
        super(context);
        init(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mIsShowingPassword = false;
        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD, true);
        showPasswordVisibilityIndicator(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[2];

            //check if the touch is within bound of drawable End icon
            if (event.getRawX() >= (getRight() - drawable.getBounds().width())) {
                togglePasswordVisibility();
                //use this to prevent the keyboard from coming up
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }

        return super.onTouchEvent(event);
    }

    private void showPasswordVisibilityIndicator(boolean show) {
        if (show) {
            Drawable original = mIsShowingPassword ?
                    ContextCompat.getDrawable(getContext(), mVisibilityIndicatorHide) :
                    ContextCompat.getDrawable(getContext(), mVisibilityIndicatorShow);
            original.mutate();

            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, original, null);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0], null, null, null);
        }
    }

    private void togglePasswordVisibility() {
        if (mIsShowingPassword) {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD, true);
        } else {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, true);
        }
        mIsShowingPassword = !mIsShowingPassword;
        showPasswordVisibilityIndicator(true);
    }

    private void setInputType(int inputType, boolean keepState) {
        int selectionStart = -1;
        int selectionEnd = -1;
        if (keepState) {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
        }
        setInputType(inputType);
        if (keepState) {
            setSelection(selectionStart, selectionEnd);
        }
    }
}
