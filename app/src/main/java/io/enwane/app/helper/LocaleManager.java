package io.enwane.app.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import io.enwane.app.utils.Constants;

import java.util.Locale;

import androidx.annotation.RequiresApi;

import static android.content.Context.MODE_PRIVATE;

public class LocaleManager {

    private static final String TAG = LocaleManager.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Context setLocale(Context c) {
        return updateResources(c, getLanguageCode(c));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Context setNewLocale(Context c, String languageCode, String languageName) {
        saveLanguagePref(c, languageCode, languageName);
        return updateResources(c, languageCode);
    }

    public static String getLanguageCode(Context c) {
        SharedPreferences pref = c.getSharedPreferences("LangPref", MODE_PRIVATE);
        return pref.getString(Constants.TAG_LANGUAGE_CODE, Constants.DEFAULT_LANGUAGE_CODE);
    }

    public static String getLanguageName(Context c) {
        SharedPreferences pref = c.getSharedPreferences("LangPref", MODE_PRIVATE);
        return pref.getString(Constants.TAG_LANGUAGE, Constants.DEFAULT_LANGUAGE);
    }

    @SuppressLint("ApplySharedPref")
    private static void saveLanguagePref(Context c, String languageCode, String languageName) {
        SharedPreferences pref = c.getSharedPreferences("LangPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        editor.putString(Constants.TAG_LANGUAGE, languageName);
        editor.putString(Constants.TAG_LANGUAGE_CODE, languageCode).commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Context updateResources(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }

    public static boolean isRTL() {
        return Locale.getDefault().getLanguage().equals("ar");
    }

}