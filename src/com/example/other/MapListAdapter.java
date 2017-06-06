package com.example.other;
import java.util.List;
import java.util.Map;

import com.example.baidu.R;
import com.example.lib.HttpApi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class MapListAdapter extends ArrayAdapter<Map<String,String>> {

    private GridView gridView;
    public MapListAdapter(Activity activity, List<Map<String,String>> imageAndTexts, GridView gridView1) {
        super(activity, 0, imageAndTexts);
        this.gridView = gridView1;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" })
	public View getView(int position, View convertView, ViewGroup parent) {

        Activity activity = (Activity) getContext();//获取正在运行的activity


        View rowView = convertView;
        LayoutInflater inflater = activity.getLayoutInflater();
        rowView = inflater.inflate(R.layout.map_user_list, null);//找到gridView

        Map<String,String> ItemData = getItem(position);//获取指定position item 的数据
        
        
        String name = ItemData.get("name").toString();//获取本item的image_path

        Button button = (Button) rowView.findViewById(R.id.btn_map_list_name);
        button.setText(name);
        
        HttpApi.MyLog(name);
        
        return rowView;
    }

}