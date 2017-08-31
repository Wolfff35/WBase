package com.wolff.wbase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wolff.wbase.R;
import com.wolff.wbase.model.catalogs.wTask.WTask;

import java.util.ArrayList;

/**
 * Created by wolff on 31.08.2017.
 */

public class WTask_list_item_adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<WTask> mWTaskList;

    public WTask_list_item_adapter(Context context, ArrayList<WTask>taskList){
        mWTaskList = taskList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mWTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWTaskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view = mInflater.inflate(R.layout.wtask_list_item_adapter,parent,false);
        }
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        WTask task = (WTask)getItem(position);
        tvDescription.setText(task.getDescription());
        return view;
    }
}
