package com.bw.zhaozhipeng;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Copyright (C)
 * <p>
 * FileName: MyBaseAdapter
 * <p>
 * Author: zhaozhipeng
 * <p>
 * Date: 2019/11/5 9:18
 * <p>
 * Description:
 */
public class MyBaseAdapter extends BaseAdapter {
    private Context context;
    private List<StudentBean.GriddataBean> griddata;

    public MyBaseAdapter(Context context, List<StudentBean.GriddataBean> griddata) {

        this.context = context;
        this.griddata = griddata;
    }

    @Override
    public int getCount() {
        return griddata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = View.inflate(context, R.layout.item, null);
            hodler.iv = convertView.findViewById(R.id.iv);
            hodler.name = convertView.findViewById(R.id.name);
            hodler.price = convertView.findViewById(R.id.price);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }

        final ViewHodler finalHodler = hodler;
        NetUtil.getInstance().doGetPhoto(griddata.get(position).getImageurl(), new NetUtil.Shared() {
            @Override
            public void doGetSuccess(String json) {

            }

            @Override
            public void doGetFiled(String error) {

            }

            @Override
            public void doPhotoSuccess(Bitmap bitmap) {
                finalHodler.iv.setImageBitmap(bitmap);
            }

            @Override
            public void doPhotoFiled(String error) {

            }
        });
        hodler.name.setText(griddata.get(position).getTitle() + "");
        hodler.price.setText(griddata.get(position).getPrice() + "");
        return convertView;
    }

    class ViewHodler {
        ImageView iv;
        TextView name, price;
    }
}
