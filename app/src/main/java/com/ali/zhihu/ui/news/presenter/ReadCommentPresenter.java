package com.ali.zhihu.ui.news.presenter;

import android.util.Log;
import android.view.View;

import com.ali.zhihu.bean.LongComment;
import com.ali.zhihu.bean.ShortComment;
import com.ali.zhihu.net.LatestNewsApi;
import com.ali.zhihu.ui.base.BaseObserver;
import com.ali.zhihu.ui.base.BasePresenter;
import com.ali.zhihu.ui.news.contract.ReadCommentContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Administrator on 2018/2/4.
 */

public class ReadCommentPresenter extends BasePresenter<ReadCommentContract.ReadCommentView> implements ReadCommentContract.ReadCommentPresenter {
    private LatestNewsApi latestNewsApi;

    @Inject
    public ReadCommentPresenter(LatestNewsApi latestNewsApi) {
        this.latestNewsApi = latestNewsApi;
    }

    @Override
    public void getLongComment(String articleId) {
        latestNewsApi.readLongComment(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<LongComment,List<LongComment.Comment>>(){
                    @Override
                    public List<LongComment.Comment> apply(LongComment longComment) throws Exception {
                        mView.readLongComment(longComment.getComments());
                        return longComment.getComments();
                    }
                })
                .subscribe(new BaseObserver<List<LongComment.Comment>>() {
                    @Override
                    public void onSuccsee(List<LongComment.Comment> comments) {
                        Log.i("aaa","aaa");
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }

    @Override
    public void getShortComment(String articleId) {
        latestNewsApi.readShortComment(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ShortComment>() {
                    @Override
                    public void onSuccsee(ShortComment shortComment) {
                        mView.readShortComment(shortComment.getComments());
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
    }
}
