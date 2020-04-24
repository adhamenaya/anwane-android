package io.enwane.app.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.test.enwane.R;
import io.enwane.app.helper.LocaleManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import androidx.core.content.ContextCompat;

public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();
    private static Snackbar snackbar;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    public static final String UTC_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static boolean isExternalPlay = false;


    private final Context context;

    public AppUtils(Context context) {
        this.context = context;
    }

    public static int calculateAge(long date) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    /**
     * function for avoiding emoji typing in keyboard
     **/
    public static InputFilter EMOJI_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {

                int type = Character.getType(source.charAt(index));

                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    };

    /**
     * function for avoiding special characters typing in keyboard
     **/
    public static InputFilter SPECIAL_CHARACTERS_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            for (int i = start; i < end; i++) {
                if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    /**
     * To convert the given dp value to pixel
     **/
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];
        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }

    /**
     * Returns the consumer friendly device name
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        Logging.i(TAG, "getDeviceName: " + manufacturer + " " + model);
        return manufacturer + " " + model;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void hideStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = activity.getActionBar();
        actionBar.hide();
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * Returns {@code null} if this couldn't be determined.
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @SuppressLint("PrivateApi")
    public static Boolean hasNavigationBar() {
        try {
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            IBinder serviceBinder = (IBinder) serviceManager.getMethod("getService", String.class).invoke(serviceManager, "window");
            Class<?> stub = Class.forName("android.view.IWindowManager$Stub");
            Object windowManagerService = stub.getMethod("asInterface", IBinder.class).invoke(stub, serviceBinder);
            Method hasNavigationBar = windowManagerService.getClass().getMethod("hasNavigationBar");
            return (boolean) hasNavigationBar.invoke(windowManagerService);
        } catch (ClassNotFoundException | ClassCastException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Log.w("YOUR_TAG_HERE", "Couldn't determine whether the device has a navigation bar", e);
            return false;
        }
    }


    public static String getPackageTitle(String validity, Context context) {
        switch (validity) {
            case "P1W":
                validity = "1 Week";
                break;
            case "P1M":
                validity = "1 Month";
                break;
            case "P3M":
                validity = "3 Month";
                break;
            case "P6M":
                validity = "6 Month";
                break;
            case "P1Y":
                validity = "1 Year";
                break;
        }
        return validity;
    }

    public static String getGemsCount(Long gems) {
        if (gems != null && gems > 999) {
            return (gems / 1000) + "K";
        } else {
            return "" + gems;
        }
    }

    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    private static String getPremiumDateFromUTC(Context context, String premiumExpiry) {
        DateFormat utcFormat = new SimpleDateFormat(UTC_DATE_PATTERN, Locale.US);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", LocaleManager.getLocale(context.getResources()));
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        utcFormat.setTimeZone(utcZone);
        Date premiumDate = new Date();
        try {
            premiumDate = utcFormat.parse(premiumExpiry);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(premiumDate);
    }

    public static String getCurrentUTCTime(Context context) {
        DateFormat utcFormat = new SimpleDateFormat(UTC_DATE_PATTERN, Locale.US);
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        utcFormat.setTimeZone(utcZone);
        Date myDate = new Date();
        return utcFormat.format(myDate);
    }

    public static String getUTCFromTimeStamp(Context context, long timeStamp) {
        DateFormat utcFormat = new SimpleDateFormat(UTC_DATE_PATTERN, LocaleManager.getLocale(context.getResources()));
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        utcFormat.setTimeZone(utcZone);
        Date myDate = new Date();
        myDate.setTime(timeStamp);
        return utcFormat.format(myDate);
    }

    public static Long getTimeFromUTC(Context context, String date) {
        DateFormat utcFormat = new SimpleDateFormat(UTC_DATE_PATTERN,Locale.US);

        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        utcFormat.setTimeZone(utcZone);
        Date myDate = new Date();
        try {
            myDate = utcFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate.getTime();
    }

    public static String getDateFromUTC(Context context, String date) {
        DateFormat utcFormat = new SimpleDateFormat(UTC_DATE_PATTERN, Locale.US);
        DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", LocaleManager.getLocale(context.getResources()));
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        utcFormat.setTimeZone(utcZone);
        Date myDate = new Date();
        String newDate = "";
        try {
            myDate = utcFormat.parse(date);
            newDate = newFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.i(TAG, "getDateFromUTC: " + newDate);
        return newDate;
    }

    public static String getRecentDate(Context context, long smsTimeInMillis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeZone(TimeZone.getDefault());
        smsTime.setTimeInMillis(smsTimeInMillis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "MMMM d, hh:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return context.getString(R.string.today) + ", " + android.text.format.DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return context.getString(R.string.yesterday) + ", " + android.text.format.DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return android.text.format.DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return android.text.format.DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString();
        }
    }

    public static String getRecentChatTime(Context context, String utcTime) {
        Date inputDate = new Date();
        if (utcTime != null && !utcTime.isEmpty()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UTC_DATE_PATTERN);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                inputDate = simpleDateFormat.parse(utcTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeZone(TimeZone.getDefault());
        smsTime.setTime(inputDate);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "MMMM d, hh:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "" + android.text.format.DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return context.getString(R.string.yesterday);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return android.text.format.DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return android.text.format.DateFormat.format("MMMM/dd/yyyy", smsTime).toString();
        }
    }

    public static String getChatTime(Context context, String date) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeZone(TimeZone.getDefault());
        smsTime.setTimeInMillis(getTimeFromUTC(context, date));
        return android.text.format.DateFormat.format("h:mm aa", smsTime).toString();
    }

    public static String getChatDate(Context context, String date) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeZone(TimeZone.getDefault());
        smsTime.setTimeInMillis(getTimeFromUTC(context, date));
        return android.text.format.DateFormat.format("MMMM dd yyyy", smsTime).toString();
    }

    public static String getTimeAgo(Context context, String utcTime) {

        Date inputDate = new Date();
        if (utcTime != null && !utcTime.isEmpty()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UTC_DATE_PATTERN);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                inputDate = simpleDateFormat.parse(utcTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Calendar smsTime = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        smsTime.setTimeZone(TimeZone.getDefault());
        smsTime.setTime(inputDate);

        long now = System.currentTimeMillis();
        final long diff = now - smsTime.getTimeInMillis();
        if (calendar.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", LocaleManager.getLocale(context.getResources()));
            return dateFormat.format(smsTime.getTimeInMillis());
        } else if (calendar.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return context.getString(R.string.yesterday);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", LocaleManager.getLocale(context.getResources()));
            return dateFormat.format(smsTime.getTimeInMillis());
        }
    }

    public static String formatWord(String word) {
        if (word != null && !word.isEmpty()) {
            word = String.valueOf(word.charAt(0)).toUpperCase() + word.subSequence(1, word.length());
            return word;
        }
        return "";
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    // Showing network status in Snackbar
    public static void showSnack(final Context context, View view, boolean isConnected) {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        snackbar = Snackbar
                .make(view, context.getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(R.string.settings).toUpperCase(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        context.startActivity(intent);
                    }
                })
                .setActionTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        if (isConnected) {
            snackbar.dismiss();
            snackbar = null;
        } else {
            if (!snackbar.isShown()) {
                snackbar.show();
            }
        }
    }

    public static void dismissSnack() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
            snackbar = null;
        }
    }


    public static String twoDigitString(long number) {
        if (number == 0) {
            return "00";
        } else if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static void startAlphaAnimation(View v, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(ALPHA_ANIMATIONS_DURATION);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public String getFormattedDuration(long currentMilliSeconds) {
        String duration;
        if (TimeUnit.MILLISECONDS.toHours(currentMilliSeconds) != 0)
            duration = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(currentMilliSeconds),
                    TimeUnit.MILLISECONDS.toMinutes(currentMilliSeconds) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(currentMilliSeconds) % TimeUnit.MINUTES.toSeconds(1));
        else
            duration = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(currentMilliSeconds) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(currentMilliSeconds) % TimeUnit.MINUTES.toSeconds(1));
        return duration;
    }

    public void pauseExternalAudio() {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (manager.isMusicActive()) {
            isExternalPlay = true;
            Intent i = new Intent("com.android.music.musicservicecommand");
            i.putExtra("command", "pause");
            context.sendBroadcast(i);
        }
    }

    public void resumeExternalAudio() {
        if (isExternalPlay) {
            isExternalPlay = false;
            Intent i = new Intent("com.android.music.musicservicecommand");
            i.putExtra("command", "play");
            context.sendBroadcast(i);
        }
    }

    public static String formateMilliSeccond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        return finalTimerString;
    }
}
