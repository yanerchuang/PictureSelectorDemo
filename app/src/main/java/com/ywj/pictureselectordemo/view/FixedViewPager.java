package com.ywj.pictureselectordemo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决异常
 * 配合手指缩放出现  Android java.lang.IllegalArgumentException: pointerIndex out of range的问题
 * http://blog.csdn.net/aaawqqq/article/details/45953263
 */
public class FixedViewPager extends ViewPager {
    public FixedViewPager(Context context) {
        super(context);
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException ignored) {

        } catch (ArrayIndexOutOfBoundsException e) {

        }

        return false;

    }
}