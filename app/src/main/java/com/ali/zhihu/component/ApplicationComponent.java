package com.ali.zhihu.component;

import android.content.Context;

import com.ali.zhihu.module.ApplicationModule;
import com.ali.zhihu.module.HttpModule;

import dagger.Component;
import dagger.Module;

/**
 * Created by Administrator on 2018/1/30.
 */
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent  {
    //暴露给下级component
    Context getContext();
}
