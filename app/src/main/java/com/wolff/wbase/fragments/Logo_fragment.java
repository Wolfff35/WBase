package com.wolff.wbase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolff.wbase.R;

/**
 * Created by wolff on 31.08.2017.
 */

public class Logo_fragment extends Fragment{
    public static Logo_fragment newInstance(){
        return new Logo_fragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.logo_fragment,container,false);

        return view;
    }
}
