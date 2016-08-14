package com.whans.platform.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.whans.platform.R;

/**
 * @author hanson.
 */

public class DeleteEditText extends AppCompatEditText {
    private Drawable mDeleteIcon;

    public DeleteEditText(Context context) {
        super(context);
        init(context, null);
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DeleteEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showDeleteIcon();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mDeleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_edt_clear);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DeleteEditText, 0, 0);
        if (array.hasValue(R.styleable.DeleteEditText_app_ic_delete)) {
            Drawable deleteIcRes = array.getDrawable(R.styleable.DeleteEditText_app_ic_delete);
            if (deleteIcRes != null) {
                mDeleteIcon = deleteIcRes;
            }
        }

        array.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[2];

            //check if the touch is within bound of drawable End icon
            if (drawable != null
                    && (event.getRawX() >= (getRight() - drawable.getBounds().width()))) {
                setText("");
                showDeleteIcon();
                //use this to prevent the keyboard from coming up
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }

        return super.onTouchEvent(event);
    }

    private void showDeleteIcon() {
        if (getText().length() > 0) {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, mDeleteIcon, null);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0], null, null, null);
        }
    }
}
