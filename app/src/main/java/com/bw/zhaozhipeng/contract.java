package com.bw.zhaozhipeng;

/**
 * Copyright (C)
 * <p>
 * FileName: contract
 * <p>
 * Author: zhaozhipeng
 * <p>
 * Date: 2019/11/5 8:53
 * <p>
 * Description:
 */
public interface contract {
    interface ModelInter {
        void doData_GET(String url, ModelShared modelShared);

        void doData_POST(String url, ModelShared modelShared);
    }

    interface IPresenter {
        void onAttch(IView iView);

        void Start(String url);

        void End();
    }

    interface IView {
        void Success(String json);

        void Filed(String error);
    }

    interface ModelShared {
        void Success(String json);

        void Filed(String error);
    }
}
