package com.wolff.wbase.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolff.wbase.R;
import com.wolff.wbase.model.abs.WCatalog;

/**
 * Created by wolff on 04.09.2017.
 */

public class SelectView extends LinearLayout {
    private ImageButton btnSelSelect;
    private ImageButton btnSelClear;
    private TextView tvSelDescription;
    private TextView tvSelLabel;

    private WCatalog mItem;

    public SelectView(Context context) {
        super(context);
        initComponent();
    }

    public SelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponent();
    }

    public SelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent();
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initComponent();
    }
    //-------------------------------------------------------------------------------------------------
    public void setWCatalogItem(WCatalog item){
        this.mItem = item;
        updateFields();
        updateVisible();
    }
    public WCatalog getWCatalogItem(){
        return mItem;
    }

    public void setLabel(String label){
        tvSelLabel.setText(label);
    }

    public void setOnClickListener_choose(OnClickListener listener){
        btnSelSelect.setOnClickListener(listener);
    }
    public void setOnClickListener_clear(OnClickListener listener){
        btnSelClear.setOnClickListener(listener);
    }
    //-------------------------------------------------------------------------------------------------

     private void initComponent() {
         LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         inflater.inflate(R.layout.view_select, this);
         btnSelSelect = (ImageButton) findViewById(R.id.btnSelSelect);
         btnSelClear = (ImageButton) findViewById(R.id.btnSelClear);
         tvSelDescription = (TextView) findViewById(R.id.tvSelDescription);
         tvSelLabel = (TextView) findViewById(R.id.tvSelLabel);

         updateFields();
         updateVisible();
     }
    private void updateVisible(){
        if(mItem!=null){
            btnSelClear.setVisibility(VISIBLE);
        }else {
            btnSelClear.setVisibility(INVISIBLE);
        }
    }
    private void updateFields(){
        if(mItem!=null){
            tvSelDescription.setText(mItem.getDescription());
        }else {
            tvSelDescription.setText("");
        }
    }

}
