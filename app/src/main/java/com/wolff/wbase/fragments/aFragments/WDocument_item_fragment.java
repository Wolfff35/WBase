package com.wolff.wbase.fragments.aFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.wolff.wbase.R;

/**
 * Created by wolff on 04.09.2017.
 */

public abstract class WDocument_item_fragment extends Fragment {
    protected abstract void saveItem();
    protected abstract void updateItem();
    protected abstract void deleteItem();
    protected abstract void postItem();

    protected Menu mOptionsMenu;

    protected boolean mIsNewItem;
    protected boolean mIsDataChanged;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.mOptionsMenu = menu;
        inflater.inflate(R.menu.menu_wdocument_item_options,mOptionsMenu);
        super.onCreateOptionsMenu(mOptionsMenu, inflater);
        setOptionsMenuVisibility();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_item_save: {
                if(mIsNewItem) {
                    saveItem();
                }else {
                    updateItem();
                }
                break;
            }
            case R.id.action_item_post:{
                postItem();
                break;
            }
            case R.id.action_item_delete: {
                deleteItem();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void setOptionsMenuVisibility(){
        if(mOptionsMenu!=null){
            MenuItem it_save = mOptionsMenu.findItem(R.id.action_item_save);
            MenuItem it_del = mOptionsMenu.findItem(R.id.action_item_delete);
            it_save.setVisible(mIsDataChanged);
            it_del.setVisible(!mIsNewItem);
        }
    }

}
