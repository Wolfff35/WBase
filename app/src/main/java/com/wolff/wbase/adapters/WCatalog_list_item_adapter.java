package com.wolff.wbase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.wolff.wbase.R;
import com.wolff.wbase.model.abs.WCatalog;
import com.wolff.wbase.tools.Debug;

import java.util.ArrayList;

/**
 * Created by wolff on 02.09.2017.
 */

public class WCatalog_list_item_adapter extends BaseAdapter implements Filterable {

   private LayoutInflater mInflater;
   private ListFilter fFilter;
   private ArrayList<WCatalog> fullList;
   private ArrayList<WCatalog> filteredList;

   public WCatalog_list_item_adapter(Context context, ArrayList<WCatalog> fullList) {
       this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       this.fullList = fullList;
       this.filteredList = fullList;

       getFilter();
   }

    @Override
    public int getCount() {
        if(filteredList!=null) {
            return filteredList.size();
        }else return 0;
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        final ViewHolder holder;
        final WCatalog item = (WCatalog) getItem(position);

        if (view == null) {
            view = mInflater.inflate(R.layout.wcat_organization_list_item_adapter, parent, false);

            holder = new ViewHolder();
            holder.tvCode = (TextView)view.findViewById(R.id.tvCode);
            holder.tvDescription = (TextView)view.findViewById(R.id.tvDescription);
            holder.tvCode.setText(item.getCode());
            holder.tvDescription.setText(item.getDescription());
            view.setTag(holder);
        } else {
             //get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.tvCode.setText(item.getCode());
        holder.tvDescription.setText(item.getDescription());

        return view;
    }

    @Override
    public Filter getFilter() {
        if (fFilter == null) {
            fFilter = new ListFilter();
        }

        return fFilter;
    }

    static class ViewHolder {
        TextView tvCode;
        TextView tvDescription;
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<WCatalog> tempList = new ArrayList<WCatalog>();

                // search content in friend list
                for (WCatalog user : fullList) {
                    if (user.getDescription().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }else if(user.getCode().toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempList.add(user);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = fullList.size();
                filterResults.values = fullList;
            }
            Debug.Log("FILTER RESULTS","Count item = "+filterResults.count);
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<WCatalog>) results.values;
            notifyDataSetChanged();
        }
    }
}
