package demo.lh.com.musicplayer.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import demo.lh.com.musicplayer.ui.UIUtils;
import demo.lh.com.musicplayer.view.StatusBarView;

public class StatusBarUtil {

    /**
     * 设置状态栏为透明 沉浸式
     */
    public static void setTranslateStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            /**
             * SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 状态栏上浮于activity
             * SYSTEM_UI_FLAG_LAYOUT_STABLE 保持整个View稳定
             * SYSTEM_UI_FLAG_FULLSCREEN  状态栏隐藏
             */
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            /**
             * FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS 负责状态栏背景的绘制 如果设置  状态栏颜色为透明色
             * 同时需要保证FLAG_TRANSLUCENT_STATUS这个flag没有被设置
             */
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置当前状态栏为透明色，同时需要同步设置 FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置透明状态栏,这样才能让 ContentView 向上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


    }
    public static void setStatusBar(Activity activity, Toolbar toolbar){
        setTranslateStatusBar(activity);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        /**
         * 判断是否已经添加了StatusBarView
         */
        int childCount = decorView.getChildCount();
        if (childCount>0 && decorView.getChildAt(childCount-1) instanceof StatusBarView){
            decorView.getChildAt(childCount-1).setBackgroundColor(Color.argb(0, 0, 0, 0));
        }
        //绘制一个和状态栏一样高的view 透明
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UIUtils.getInstance().getSystemBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        decorView.addView(statusBarView);

        /**
         * 将toolbar向下移动
         */
        if (toolbar != null){
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            layoutParams.setMargins(0, UIUtils.getInstance().getSystemBarHeight(activity), 0, 0);
        }
    }
}
