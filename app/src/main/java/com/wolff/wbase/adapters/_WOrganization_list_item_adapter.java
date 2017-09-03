package com.wolff.wbase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wolff.wbase.R;
import com.wolff.wbase.model.catalogs.wOrganization.WOrganization;

import java.util.ArrayList;

/**
 * Created by wolff on 01.09.2017.
 */

public class _WOrganization_list_item_adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<WOrganization> mList;

    public _WOrganization_list_item_adapter(Context context, ArrayList<WOrganization>list){
        this.mList = list;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(mList!=null) {
            return mList.size();
        }else return 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view = mInflater.inflate(R.layout.worganization_list_item_adapter,parent,false);
        }
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        WOrganization org = (WOrganization) getItem(position);
        tvDescription.setText(org.getDescription());
        return view;
    }
}
