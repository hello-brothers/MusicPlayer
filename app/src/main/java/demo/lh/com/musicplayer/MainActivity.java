package demo.lh.com.musicplayer;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import demo.lh.com.musicplayer.ui.UIUtils;
import demo.lh.com.musicplayer.ui.ViewCalculateUtil;
import demo.lh.com.musicplayer.util.StatusBarUtil;
import demo.lh.com.musicplayer.view.MyNestedScrollView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MainActivity extends AppCompatActivity {
    public final static String IMAGE_URL_MEDIUM = "http://p1.music.126.net/mXqmc1nD5mu2S4pEvBVHzw==/109951164141857357.jpg";
    private Toolbar toolbar;
    private RecyclerView music_recycler;
    private int slidingDistance;
    private ImageView toolbar_bg;
    private ImageView header_bg;
    private LinearLayout lv_header_contail;
    private ImageView header_music_log;
    private ImageView header_image_item;
    private MyNestedScrollView myNestedScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        notifyDate();
        postImage();
        changeToolbar();
    }

    private void changeToolbar() {
        slidingDistance = UIUtils.getInstance().getHeight(490);
        myNestedScrollView.setOnScrollChangeedListener(new MyNestedScrollView.ScrollChangeedListener() {
            @Override
            public void onScrollChangeedListener(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("tag", "onScrollChangeedListener: " + scrollY);
                scrollChangeHeader(scrollY);

            }
        });
    }

    private void scrollChangeHeader(int scrollY) {
        if (scrollY<0){
            scrollY = 0;
        }
        float alpha = Math.abs(scrollY) * 1.0f / slidingDistance;
        Drawable drawable = toolbar_bg.getDrawable();
        if (drawable != null){
            if (scrollY < slidingDistance ){
                drawable.mutate().setAlpha((int) (alpha*255));
                toolbar_bg.setImageDrawable(drawable);
            }else {
                drawable.mutate().setAlpha(255);
                toolbar_bg.setImageDrawable(drawable);
            }
        }
    }

    private void postImage() {
        Glide.with(this)
                .load(IMAGE_URL_MEDIUM)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.i("tuch", "onException: "+e);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.i("tuch", "onResourceReady: ");
                        return false;
                    }
                }).override(400, 400)
                .into(header_image_item);

        // "14":模糊度；"3":图片缩放3倍后再进行模糊
        Glide.with(this)
                .load(IMAGE_URL_MEDIUM)
                .error(R.drawable.stackblur_default)
                .placeholder(R.drawable.stackblur_default)
                .crossFade(500)
                .bitmapTransform(new BlurTransformation(this, 200, 3))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        lv_header_contail.setBackground(resource);
                    }
                });
        Glide.with(this).load(IMAGE_URL_MEDIUM)
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(this, 250, 6))// 设置高斯模糊
                .listener(new RequestListener<String, GlideDrawable>() {//监听加载状态
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        toolbar.setBackgroundColor(Color.TRANSPARENT);
                        toolbar_bg.setImageAlpha(0);
                        toolbar_bg.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(toolbar_bg);
    }

    private void notifyDate() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        music_recycler.setLayoutManager(manager);
        //需加上 不然滑动不流畅
        music_recycler.setNestedScrollingEnabled(false);
        music_recycler.setHasFixedSize(false);
        //分割线
//        music_recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        MusicAdapter adapter = new MusicAdapter(this);
        music_recycler.setAdapter(adapter);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UIUtils.getInstance(getApplicationContext());

        music_recycler = findViewById(R.id.music_recycler);
        toolbar_bg = findViewById(R.id.toolbar_bg);
        header_bg = findViewById(R.id.header_bg);
        myNestedScrollView = findViewById(R.id.nsv_scrollview);
        header_music_log = findViewById(R.id.header_music_log);
        LinearLayout lv_header_detail = findViewById(R.id.lv_header_detail);
        RelativeLayout rv_header_container = findViewById(R.id.rv_header_container);
        lv_header_contail = findViewById(R.id.lv_header_contail);
        header_image_item = findViewById(R.id.header_image_item);

        ViewCalculateUtil.setViewLayoutParam(toolbar, 1080, 164, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(rv_header_container,1080,740,164,0,0,0);
        ViewCalculateUtil.setViewLayoutParam(toolbar,1080, 164, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLayoutParam(toolbar_bg,1080,164+UIUtils.getInstance().getSystemBarHeight(this),0,0,0,0);
        ViewCalculateUtil.setViewLayoutParam(header_bg, 1080, 740, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLayoutParam(lv_header_detail, 1080, 380, 72, 0, 52, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(header_image_item,380,380);
        ViewCalculateUtil.setViewLayoutParam(header_music_log,60,60,59,0,52,0);
        StatusBarUtil.setStatusBar(this, toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mucis, menu);
        return true;
    }
}
