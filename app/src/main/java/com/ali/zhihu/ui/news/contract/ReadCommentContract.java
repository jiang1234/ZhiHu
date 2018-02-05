package com.ali.zhihu.ui.news.contract;

import com.ali.zhihu.bean.LongComment;
import com.ali.zhihu.bean.ShortComment;
import com.ali.zhihu.ui.base.BaseContract;
import com.ali.zhihu.ui.base.BasePresenter;

import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */

public interface ReadCommentContract {
    interface ReadCommentPresenter extends BaseContract.BasePresenter<ReadCommentView>{
        void getLongComment(String articleId);

        void getShortComment(String articleId);
    }
    interface ReadCommentView extends BaseContract.BaseView{
        void readLongComment(List<LongComment.Comment> longComment);

        void readShortComment(List<ShortComment.Comment> shortComment);
    }
}
