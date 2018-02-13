package com.ali.zhihu.component;

import com.ali.zhihu.module.ApplicationModule;
import com.ali.zhihu.module.HttpModule;
import com.ali.zhihu.ui.news.LastestNewsFragment;
import com.ali.zhihu.ui.news.ReadArticleActivity;
import com.ali.zhihu.ui.news.ReadCommentActivity;
import com.ali.zhihu.ui.theme.ThemeFragment;

import dagger.Component;

/**
 * Created by Administrator on 2018/1/30.
 */
@Component(dependencies = ApplicationComponent.class, modules = {HttpModule.class})
public interface HttpComponent {
    void inject(LastestNewsFragment lastestNewsFragment);

    void inject(ReadArticleActivity readArticleActivity);

    void inject(ReadCommentActivity readCommentActivity);

    void inject(ThemeFragment themeFragment);
}
