package com.ali.zhihu;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ali.zhihu.component.ApplicationComponent;
import com.ali.zhihu.component.DaggerApplicationComponent;
import com.ali.zhihu.module.ApplicationModule;

/**
 * Created by Administrator on 2018/1/30.
 * 提供全局context
 * 提供ApplicationComponent实例
 */

public class MyApplication extends Application {
    private static Context context;
    private static ApplicationComponent applicationComponent;
    private static int width;
    private static int height;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build();

        //获得屏幕高宽
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    public static Context getContext(){
        return context;
    }
    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    @Override

    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);

        MultiDex.install(this);

    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
