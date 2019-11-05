package com.bw.zhaozhipeng;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.zhaozhipeng.presenter.Presenter;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

public class MainActivity extends AppCompatActivity implements contract.IView {
    private XBanner xbannerPointId;
    private GridView gv;
    private Presenter presenter;
    private String http = "http://blog.zhaoliang5156.cn/api/shop/jingdong.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter();
        presenter.onAttch(this);
        presenter.Start(http);
        setContentView(R.layout.activity_main);


        xbannerPointId = findViewById(R.id.xbanner_pointId);
        gv = findViewById(R.id.gv);

    }

    @Override
    public void Success(String json) {
        //进行网络判断
        if (NetUtil.getInstance().iswang(MainActivity.this)) {
            getData();
        } else {
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void Filed(String error) {

    }


    private void getData() {
        NetUtil.getInstance().doGet(http, new NetUtil.Shared() {
            @Override
            public void doGetSuccess(String json) {
                Gson gson = new Gson();
                StudentBean studentBean = gson.fromJson(json, StudentBean.class);
                final List<StudentBean.BannerdataBean> bannerdata = studentBean.getBannerdata();
                //绑定数据
                xbannerPointId.setBannerData(bannerdata);
                //加载图片
                xbannerPointId.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, final View view, int position) {
                        NetUtil.getInstance().doGetPhoto(bannerdata.get(position).getImageurl(), new NetUtil.Shared() {
                            @Override
                            public void doGetSuccess(String json) {

                            }

                            @Override
                            public void doGetFiled(String error) {

                            }

                            @Override
                            public void doPhotoSuccess(Bitmap bitmap) {
                                ((ImageView) view).setImageBitmap(bitmap);
                            }

                            @Override
                            public void doPhotoFiled(String error) {

                            }
                        });
                    }
                });

                List<StudentBean.GriddataBean> griddata = studentBean.getGriddata();

                gv.setAdapter(new MyBaseAdapter(MainActivity.this, griddata));
            }

            @Override
            public void doGetFiled(String error) {

            }

            @Override
            public void doPhotoSuccess(Bitmap bitmap) {

            }

            @Override
            public void doPhotoFiled(String error) {

            }
        });
    }
}
