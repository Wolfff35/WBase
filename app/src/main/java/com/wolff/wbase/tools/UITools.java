package com.wolff.wbase.tools;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wolff.wbase.R;

/**
 * Created by wolff on 09.08.2017.
 */

public class UITools {
    public void designNavigationMenu(Context context, Menu menu){
        MenuItem nav_menu_item_documents = menu.findItem(R.id.nav_menu_item_documents);
        SpannableString s = new SpannableString(nav_menu_item_documents.getTitle());
        s.setSpan(new TextAppearanceSpan(context,R.style.nav_menu_item_documents),0,s.length(),0);
        nav_menu_item_documents.setTitle(s);

    }
    public void firstRunInitialize(Context context){
        Log.e("FIRST RUN","INIT");
        new PreferencesTools().setBooleanPreference(context,PreferencesTools.PREFERENCE_IS_FIRST_RUN,false);
    }
    public void displayFragment(FragmentActivity context, Fragment fragment) {
        FragmentTransaction fragmentTransaction;
        FragmentManager fm = context.getSupportFragmentManager();

        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
                //.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }
/*
   // Восстанавливаем уже созданный фрагмент
    FragmentManager fm = getSupportFragmentManager();
    fragment = (MyFragment) fm.findFragmentByTag(FRAGMENT_INSTANCE_NAME);
    // Если фрагмент не сохранен, создаем новый экземпляр
    if(fragment == null){
        fragment = new MyFragment();
        fm.beginTransaction().add(R.id.container, fragment, FRAGMENT_INSTANCE_NAME).commit();
 */
}
