package com.wolff.wbase.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolff.wbase.R;

/**
 * Created by wolff on 06.09.2017.
 */

public class EditView extends LinearLayout {
    private EditText tvEdText;
    private TextView tvEdLabel;



    public EditView(Context context) {
        super(context);
        initComponent();
    }

    public EditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponent();
    }

    public EditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent();
    }

    public EditView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initComponent();
    }

    //-------------------------------------------------------------------------------------------------

    public void setLabel(String label){
        tvEdLabel.setText(label);
    }
    public void setText(String t){
        tvEdText.setText(t);
    }
    public String getText(){
        return tvEdText.getText().toString();
    }
    public void addTextChangedListener(TextWatcher watcher){
        tvEdText.addTextChangedListener(watcher);
    }
    public void setEnabled(boolean enabled){
        tvEdText.setEnabled(enabled);
    }

    //-------------------------------------------------------------------------------------------------

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_edit, this);
        tvEdText = (EditText) findViewById(R.id.tvEdText);
        tvEdLabel = (TextView) findViewById(R.id.tvEdLabel);

    }

 }

