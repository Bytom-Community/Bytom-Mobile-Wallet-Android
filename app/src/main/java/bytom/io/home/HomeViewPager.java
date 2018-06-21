package bytom.io.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by DongFangZhou on 2018/6/21.
 */

public class HomeViewPager extends ViewPager {
    public HomeViewPager (@NonNull Context context) {
        this(context,null);
    }

    public HomeViewPager (@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;

    }
}
