package com.wolff.wbase.model.catalogs.wCatalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.wolff.wbase.R;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCatalog_search_list_fragment extends Fragment implements SearchView.OnQueryTextListener {
    protected ListView mMainListView;
    protected SearchView mMainSearchView;
    protected WCatalog_list_item_adapter mMainAdapter;
    private Menu mMainOptionsMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
       }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.wcatalog_list_fragment,container,false);
        mMainListView = (ListView)view.findViewById(R.id.lvMain);
        mMainSearchView = (SearchView) view.findViewById(R.id.etSearchItem);
        setupSearchView();

        setHasOptionsMenu(true);
        return view;

    }
    private void setupSearchView() {
        mMainSearchView.setIconifiedByDefault(false);
        mMainSearchView.setOnQueryTextListener(this);
        mMainSearchView.setSubmitButtonEnabled(true);
        mMainSearchView.setQueryHint("Поиск");
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mMainAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
//--------------------------------------------------------------------------------------------------]
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    this.mMainOptionsMenu = menu;
    inflater.inflate(R.menu.menu_list_options, mMainOptionsMenu);
    super.onCreateOptionsMenu(mMainOptionsMenu,inflater);
}
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //MenuItem mi = menu.findItem(R.id.action_import_item);
        //mi.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }




}
