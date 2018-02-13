package com.ali.zhihu.ui.theme;

import com.ali.zhihu.bean.ReadTheme;
import com.ali.zhihu.ui.base.BaseContract;

/**
 * Created by Administrator on 2018/2/13.
 */

public interface ReadThemeContract {
    interface ReadThemePresenter extends BaseContract.BasePresenter<ReadThemeView>{
        void getArticle(int articleId);
    }
    interface ReadThemeView extends BaseContract.BaseView{
        void readZhihuArticle(ReadTheme article);
        void readOtherArticle(ReadTheme article);
    }
}
