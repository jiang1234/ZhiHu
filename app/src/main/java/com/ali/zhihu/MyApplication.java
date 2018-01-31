package com.ali.zhihu;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

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

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build();
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
}
