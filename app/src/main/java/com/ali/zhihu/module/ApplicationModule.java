package com.ali.zhihu.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/1/30.
 */
@Module
public class ApplicationModule {
private Context context;
    public ApplicationModule(Context context){
        this.context = context;
    }
    @Provides
    Context provideContext(){
        return context;
    }
}
