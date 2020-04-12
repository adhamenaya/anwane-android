package com.test.myapplication.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import com.facebook.stetho.Stetho;
import com.test.myapplication.helper.LocaleManager;
import com.test.myapplication.utils.SharedPref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleObserver;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends android.app.Application implements LifecycleObserver {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharedPref.initPref(instance);
        Stetho.initializeWithDefaults(this);
        setUpRealmConfig();
    }

    private void setUpRealmConfig() {
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded().
                build();
        Realm.setDefaultConfiguration(config);
    }

    public static synchronized App getInstance() {
        return instance;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
        fix();
    }

    public static void fix() {
        try {
            Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
            Method method = clazz.getSuperclass().getDeclaredMethod("stop");
            method.setAccessible(true);

            Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);

            method.invoke(field.get(null));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

}



