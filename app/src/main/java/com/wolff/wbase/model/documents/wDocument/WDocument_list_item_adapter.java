package com.wolff.wbase.model.documents.wDocument;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.wolff.wbase.R;
import com.wolff.wbase.tools.DateFormatTools;
import com.wolff.wbase.tools.Debug;

import java.util.ArrayList;

/**
 * Created by wolff on 02.09.2017.
 */

public class WDocument_list_item_adapter extends BaseAdapter implements Filterable {

   private LayoutInflater mInflater;
   private ListFilter fFilter;
   private ArrayList<WDocument> fullList;
   private ArrayList<WDocument> filteredList;

   public WDocument_list_item_adapter(Context context, ArrayList<WDocument> fullList) {
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
        final WDocument item = (WDocument) getItem(position);
        DateFormatTools dateFormatTools = new DateFormatTools();
        if (view == null) {
            view = mInflater.inflate(R.layout.wdoc_kassa_pko_list_item_adapter, parent, false);

            holder = new ViewHolder();
            holder.tvDate = (TextView)view.findViewById(R.id.tvDate);
            holder.tvNumber = (TextView)view.findViewById(R.id.tvNumber);
            holder.tvDate.setText(dateFormatTools.dateToString(item.getDate(),DateFormatTools.DATE_FORMAT_VID));
            holder.tvNumber.setText(item.getNumber());
            view.setTag(holder);
        } else {
             //get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.tvDate.setText(dateFormatTools.dateToString(item.getDate(),DateFormatTools.DATE_FORMAT_VID));
        holder.tvNumber.setText(item.getNumber());

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
        TextView tvDate;
        TextView tvNumber;
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<WDocument> tempList = new ArrayList<WDocument>();

                // search content in friend list
                for (WDocument user : fullList) {
                    //if (user.getDate().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    //    tempList.add(user);
                    //}else
                    if(user.getNumber().toLowerCase().contains(constraint.toString().toLowerCase())){
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
            filteredList = (ArrayList<WDocument>) results.values;
            notifyDataSetChanged();
        }
    }
}
