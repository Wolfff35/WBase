package com.wolff.wbase.model.catalogs.wCatalog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wbase.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wolff on 04.09.2017.
 */

public class WCatalog_item_activity extends AppCompatActivity {
    //private WChannel mChannelItem;
    private static final String EXTRA_ITEM = "WCatItem";
    private static final String EXTRA_CLASS = "WCatClass";

    public static Intent newIntent(Context context, String item_key,Class cl){
        Intent intent = new Intent(context,WCatalog_item_activity.class);
        intent.putExtra(EXTRA_ITEM,item_key);
        intent.putExtra(EXTRA_CLASS,cl);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.item_activity);
        String item_key = getIntent().getStringExtra(EXTRA_ITEM);
        Class item_class = (Class) getIntent().getSerializableExtra(EXTRA_CLASS);

        try {
            Class[] paramTypes = new Class[]{String.class};
            Fragment itemFragment = null;
            Method method = item_class.getMethod("getItemFragment",paramTypes);
            itemFragment = (Fragment) method.invoke(null,item_key);

            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.item_container, itemFragment);
            fragmentTransaction.commit();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}


