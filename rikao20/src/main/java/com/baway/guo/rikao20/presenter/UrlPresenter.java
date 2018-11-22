package com.baway.guo.rikao20.presenter;

import com.baway.guo.rikao20.Model.UrlModel;
import com.baway.guo.rikao20.View.UrlView;

public class UrlPresenter {

    private UrlView mUrlView;
    private final UrlModel mUrlModel;

    public UrlPresenter(UrlView urlView) {
        this.mUrlView = urlView;
        mUrlModel = new UrlModel();
    }

    public void Url(String url) {
        mUrlModel.getUrl(url, new UrlModel.HttpData() {
            @Override
            public void getUrl(String s) {
                if (s != null) {
                    mUrlView.onSuccess(s);
                } else {
                    mUrlView.onFailer("失败");
                }
            }
        });
    }
}
