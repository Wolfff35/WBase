package com.wolff.wbase.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wolff.wbase.R;
import com.wolff.wbase.model.catalogs.wTask.WTask;

/**
 * Created by wolff on 31.08.2017.
 */

public class WTask_list_fragment extends Fragment {
    private ListView mListViewMain;

    public interface WTask_list_fragment_listener{
        void OnWTaskItemSelected(WTask task);
    }
    public static WTask_list_fragment newInstance(){
        return new WTask_list_fragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.list_fragment,container,false);
        mListViewMain = (ListView)view.findViewById(R.id.lvMain);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //mChannelList = DataLab.get(getContext()).getWChannelsList();
        WTask_list_adapter adapter = new Channel_list_adapter(getContext(),mChannelList);

        mChannelListViewMain.setAdapter(adapter);
        mChannelListViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener1.onChannelSelected(mChannelList.get(position));
            }
        });
        getActivity().setTitle(getString(R.string.menu_nav_header_channels));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener1 = (Channel_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener1=null;
        mOptionsMenu=null;
    }

}
