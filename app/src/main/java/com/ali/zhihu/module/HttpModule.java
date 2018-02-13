package com.ali.zhihu.module;

import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.bean.Theme;
import com.ali.zhihu.net.LatestNewsApi;
import com.ali.zhihu.net.LatestNewsApiServer;
import com.ali.zhihu.net.ThemeApi;
import com.ali.zhihu.net.ThemeApiServer;
import com.ali.zhihu.ui.news.contract.LatestNewsContract;
import com.ali.zhihu.ui.news.presenter.LatestNewsPresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/30.
 */
@Module
public class HttpModule {
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS);
    }
    @Provides
    Retrofit provideRetrofit(OkHttpClient.Builder builder){
        return new Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/4/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    @Provides
    LatestNewsApi provideLatestNewsApi(Retrofit retrofit){
        LatestNewsApiServer latestNewsApiServer= retrofit.create(LatestNewsApiServer.class);
        return LatestNewsApi.getInstance(latestNewsApiServer);
    }

    @Provides
    ThemeApi provideThemeApi(Retrofit retrofit){
        ThemeApiServer themeApiServer= retrofit.create(ThemeApiServer.class);
        return ThemeApi.getInstance(themeApiServer);
    }
  //  @Provides
   // LatestNewsPresenter provideLatestNewsPresenter(LatestNewsApi latestNewsApi){
   //     return new LatestNewsPresenter(latestNewsApi);
   // }


}

