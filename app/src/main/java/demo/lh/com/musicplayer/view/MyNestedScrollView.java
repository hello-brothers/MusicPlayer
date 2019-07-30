package demo.lh.com.musicplayer.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.widget.Scroller;

public class MyNestedScrollView extends NestedScrollView {
    private ScrollChangeedListener listener;
    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener!=null){
            listener.onScrollChangeedListener(l, t, oldl, oldt);
        }

    }
    public interface ScrollChangeedListener{
        void onScrollChangeedListener(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    public void setOnScrollChangeedListener(ScrollChangeedListener listener) {
        this.listener = listener;
    }
}
