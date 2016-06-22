package ru.badboy.uno;

import android.support.v4.app.Fragment;

/**
 * Created by Евгений on 12.01.2016.
 */
public class StartActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new StartGameFragment();
    }
}
