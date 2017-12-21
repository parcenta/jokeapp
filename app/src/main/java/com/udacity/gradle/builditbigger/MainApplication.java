package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by USUARIO on 21/12/2017.
 */

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
