package com.example.sqliteassigment2;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sqliteassigment2.Model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Activity activity;
    private int layout;
    private List<Product> list;

    public ProductAdapter(Activity activity, int layout, List<Product> list) {
        this.activity = activity;
        this.layout = layout;
        this.list = list;
    }

    public ProductAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tvName, tvPrice;


        if(view == null){
            view = activity.getLayoutInflater().inflate(layout, null);
            tvName = view.findViewById(R.id.txtShowName);
            tvPrice = view.findViewById(R.id.txtShowPrice);

            view.setTag(R.id.txtShowName, tvName);
            view.setTag(R.id.txtShowPrice, tvPrice);
        }else{
            tvName = (TextView) view.getTag(R.id.txtShowName);
            tvPrice = (TextView) view.getTag(R.id.txtShowPrice);

        }

        Product product = list.get(i);
        tvName.setText(product.getName());
        tvPrice.setText(String.valueOf(product.getPrice()));


        return view;
    }
}
