package com.bw.zhaozhipeng.presenter;

import android.graphics.Bitmap;

import com.bw.zhaozhipeng.NetUtil;
import com.bw.zhaozhipeng.contract;
import com.bw.zhaozhipeng.model.Model;

/**
 * Copyright (C)
 * <p>
 * FileName: Presenter
 * <p>
 * Author: zhaozhipeng
 * <p>
 * Date: 2019/11/5 8:56
 * <p>
 * Description:
 */
public class Presenter implements contract.IPresenter {
    private contract.ModelInter modelInter;
    private contract.IView mIView;

    @Override
    public void onAttch(contract.IView iView) {
        this.mIView = iView;
        modelInter = new Model();
    }

    @Override
    public void Start(String url) {
        NetUtil.getInstance().doGet(url, new NetUtil.Shared() {
            @Override
            public void doGetSuccess(String json) {
                mIView.Success(json);
            }

            @Override
            public void doGetFiled(String error) {
                mIView.Filed(error);
            }

            @Override
            public void doPhotoSuccess(Bitmap bitmap) {

            }

            @Override
            public void doPhotoFiled(String error) {

            }
        });
    }

    @Override
    public void End() {
        if (mIView != null) {
            mIView = null;
        }
        if (modelInter != null) {
            modelInter = null;
        }
    }
}
