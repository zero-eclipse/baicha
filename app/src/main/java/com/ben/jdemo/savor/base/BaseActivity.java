package com.ben.jdemo.savor.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ben.jdemo.savor.util.enumstyle.StatusBar;

/**
 * @author： BaiCha
 * @Time:2019/1/14
 * @description :
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        styleSetting();

        initial();
        initDeal();

    }

    protected abstract int getLayoutId();

    protected abstract StatusBar getStatusBarStyle();

    protected abstract void initial();

    protected abstract void initDeal();

    //状态栏的样式
    private void styleSetting(){
        if(getStatusBarStyle()== StatusBar.HIDE){
            if(Build.VERSION.SDK_INT >= 21){
                View viewDoctor = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                viewDoctor.setSystemUiVisibility(option);
                //设置状态栏和导航栏颜色为透明
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow().setNavigationBarColor(Color.TRANSPARENT);
            }
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }else if(getStatusBarStyle()==StatusBar.TRANSPARENT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                View decorView = window.getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                int finalColor = Color.TRANSPARENT ;
                window.setNavigationBarColor(finalColor);
                window.setStatusBarColor(finalColor);

            }

        }
    }

    public void setColorBar(@ColorInt int color, int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int alphaColor = alpha == 0 ? color : calculateColor(color, alpha);
            window.setStatusBarColor(alphaColor);
            window.setNavigationBarColor(alphaColor);
        }

    }


    private int calculateColor(@ColorInt int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }




}
