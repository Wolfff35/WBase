package com.wolff.wbase.model.documents.wDoc_Kassa_PKO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wbase.R;

/**
 * Created by wolff on 04.09.2017.
 */

public class WDoc_Kassa_PKO_item_activity extends AppCompatActivity {
    private static final String EXTRA_ORG_ITEM = "WDocItem";

    public static Intent newIntent(Context context, String item_key){
        Intent intent = new Intent(context,WDoc_Kassa_PKO_item_activity.class);
        intent.putExtra(EXTRA_ORG_ITEM,item_key);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.item_activity);
        String item_key = getIntent().getStringExtra(EXTRA_ORG_ITEM);
        //WDoc_Kassa_PKO item = null;
        if(item_key!=null) {
            //item = new WDoc_Kassa_PKO_getter(getApplicationContext()).getItem(item_key);
        }
        WDoc_Kassa_PKO_item_fragment itemFragment = WDoc_Kassa_PKO_item_fragment.newInstance(item_key);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.item_container, itemFragment);
        fragmentTransaction.commit();


    }

}


