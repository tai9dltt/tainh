package com.example.i_tainh.demoorderfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.i_tainh.demoorderfood.R;
import com.example.i_tainh.demoorderfood.entity.Table;

import java.util.List;

public class ShowTableAdapter extends BaseAdapter implements View.OnClickListener {

    viewHolder viewHolder;
    Context context;
    int layout;
    List<Table> tableList;


    public ShowTableAdapter(Context context, int layout, List<Table> listTable){
        this.context = context;
        this.layout = layout;
        this.tableList = listTable;
    }

    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public Object getItem(int position) {
        return tableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tableList.get(position).getTableID();
    }



    public class viewHolder{
        ImageView imgBanAn, imgThanhtoan,imghide, imgGoimon;
        TextView txtTableName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= convertView;
        if(view == null){
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_showtable,parent,false);
            viewHolder = new viewHolder();
            viewHolder.imgBanAn = view.findViewById(R.id.img_tableFalse);
            viewHolder.imgGoimon = view.findViewById(R.id.img_goimon);
            viewHolder.imgThanhtoan = view.findViewById(R.id.img_thanhToan);
            viewHolder.imghide = view.findViewById(R.id.img_hide);
            viewHolder.txtTableName = view.findViewById(R.id.txt_tableName);

            view.setTag(viewHolder);
        }
        else{
             viewHolder = (viewHolder) view.getTag();
        }
        if(tableList.get(position).isClicked()){
            viewHolder.imgGoimon.setVisibility(View.VISIBLE);
            viewHolder.imghide.setVisibility(View.VISIBLE);
            viewHolder.imgThanhtoan.setVisibility(View.VISIBLE);
        }else{
            viewHolder.imgGoimon.setVisibility(View.INVISIBLE);
            viewHolder.imghide.setVisibility(View.INVISIBLE);
            viewHolder.imgThanhtoan.setVisibility(View.INVISIBLE);
        }

        Table table = tableList.get(position);
        viewHolder.txtTableName.setText(table.getTableName());
        viewHolder.imgBanAn.setTag(position);


        viewHolder.imgBanAn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        viewHolder = (viewHolder) ((View)v.getParent()).getTag();
        switch (id){
            case R.id.img_tableFalse:
                int pos = (int) v.getTag();
                tableList.get(pos).setClicked(true);
                viewHolder.imgGoimon.setVisibility(View.VISIBLE);
                viewHolder.imghide.setVisibility(View.VISIBLE);
                viewHolder.imgThanhtoan.setVisibility(View.VISIBLE);

                break;
        }

    }
}
