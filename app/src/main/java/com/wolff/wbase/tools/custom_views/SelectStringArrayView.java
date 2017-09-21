package com.wolff.wbase.tools.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolff.wbase.R;

/**
 * Created by wolff on 04.09.2017.
 */

public class SelectStringArrayView extends LinearLayout {
    private ImageButton btnSelSelect;
    private ImageButton btnSelClear;
    private TextView tvSelDescription;
    private TextView tvSelLabel;

    private String mItem;

    public SelectStringArrayView(Context context) {
        super(context);
        initComponent();
    }

    public SelectStringArrayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponent();
    }

    public SelectStringArrayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent();
    }

    public SelectStringArrayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initComponent();
    }
    //-------------------------------------------------------------------------------------------------
    public void setItem(String item){
        this.mItem = item;
        updateFields();
        updateVisible();
    }
    public String getItem(){
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
         inflater.inflate(R.layout.view_select_wcatalog, this);
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
            tvSelDescription.setText(mItem);
        }else {
            tvSelDescription.setText("");
        }
    }

}
