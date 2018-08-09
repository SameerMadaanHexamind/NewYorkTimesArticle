package com.scorematics.android.utils;

import android.Manifest.permission;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.util.Xml;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.scorematics.android.R;
import com.scorematics.android.base.BaseFragment;
import com.scorematics.android.interfaces.OnAlertDialogFragmentListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utilities {
    private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
    private static final int CAMERA_ACTIVITY = 1;
    private static final int GALLERY_ACTIVITY = 2;
    private static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static String DECRYPTION_PUBLIC_KEY = "abcuniverse";


    // private EncryptionDecryption mEncryptionDecryptionInstance;

    // public void setisSessionClear(boolean setSessionStatus) {
    //
    // // UrlConstants.isSessionClear = setSessionStatus;
    //
    // }

    // public boolean getSessionStatus() {
    // // return UrlConstants.isSessionClear;
    // }
    private static Utilities utilityInstance;
    private static Context context;
    TimePickerDialog timePicker;
    private Pattern pattern;
    private Matcher matcher;

    /*
     *
     * Default Constructor
     */
    private Utilities() {

    }

    /**
     * create single instance of the Utility class. Check if the instance is
     * null then it will create new instance else
     *
     * @param ctx of the activity
     * @return instance
     */
    public static Utilities getInstance(Context ctx) {
        if (utilityInstance == null) {
            utilityInstance = new Utilities();

        }

        context = ctx;
        return utilityInstance;
    }

    public String compareDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        String recentDateString = formatter.format(calendar.getTime());
        long currentTime = System.currentTimeMillis();
        calendar.setTimeInMillis(currentTime);
        String currentdateString = formatter.format(calendar.getTime());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date recentDate = null;
        Date currentDate = null;
        long diffSeconds = 0, diffMinutes = 0, diffHours = 0, diffDays = 0;

        try {
            recentDate = format.parse(recentDateString);
            currentDate = format.parse(currentdateString);

            //in milliseconds
            long diff = currentDate.getTime() - recentDate.getTime();
            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);


        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((int) diffDays > 0) {
            return (String.valueOf((int) diffDays) + " day(s) ago");

        } else {
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("HH:mm");
            calendar.setTimeInMillis(milliSeconds);
            String dateValue = simpleFormatter.format(calendar.getTime());
            return "Today " + dateValue;
        }


    }

    public void setFocusForKeyBoard(CharSequence stringValue, EditText previous, EditText next) {

        if (stringValue != null && stringValue.length() >= 1) {
            if (next != null) {
                next.requestFocus();
            } else {
                Utilities.getInstance(context).showHideKeyboard(false);
            }
        } else {
            if (previous != null) {
                previous.requestFocus();
            }
        }

    }

    public boolean isServiceRunning(String serviceName) {
        boolean isRunning = false;

        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;

    }


    /**
     * Used to copy input stream of one file to another file
     *
     * @param input  the input stream of file that needs to be copied.
     * @param output the of file where input stream needs to be copied.
     * @throws IOException
     */
    public void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public void setDialogPosition(View viewSource, Window dialogWindow,
                                  int horizontalMargin, int verticalMargin) {
        if (viewSource == null) {
            return; // Leave the dialog in default position
        }

        // Find out location of source component on screen
        // see http://stackoverflow.com/a/6798093/56285
        int[] location = new int[2];
        viewSource.getLocationOnScreen(location);
        int sourceX = location[0];
        int sourceY = location[1];

        Window window = dialogWindow;

        // set "origin" to top left corner
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER);

        WindowManager.LayoutParams params = window.getAttributes();

        // Just an example; edit to suit your needs.
        params.x = sourceX;
        params.y = dpToPx(180) + dpToPx(verticalMargin);
//        params.height = dpToPx(250);
//        params.width = dpToPx(260);

        window.setAttributes(params);
    }

    public Uri getTempUri(boolean isCreatingNewFile) {
        return Uri.fromFile(getTempFile(isCreatingNewFile));
    }

    private File getTempFile(boolean isCreatingNewFile) {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            File file = new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO_FILE);
            try {
                if (!file.exists()) {
                    // if (isCreatingNewFile) {
                    // file.delete();
                    // file.createNewFile();
                    //
                    // }
                    // } else {
                    file.createNewFile();
                }
            } catch (IOException e) {
            }

            return file;
        } else {

            return null;
        }

    }

    public String getFilePath(boolean isCreatingNewFile) {
        File file = getTempFile(isCreatingNewFile);
        return file.getAbsolutePath();
    }


    public String createXML(String[] keys, String[] values) {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            xmlSerializer.setOutput(writer);
            // start DOCUMENT
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("", keys[0]);
            for (int count = 1; count < keys.length; count++) {
                xmlSerializer.startTag("", keys[count]);
                xmlSerializer.text(values[count]);
                xmlSerializer.endTag("", keys[count]);

            }
            // end DOCUMENT
            xmlSerializer.endTag("", keys[0]);
            xmlSerializer.endDocument();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return writer.toString();

    }

    public int dpToPx(float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                valueInDp, metrics);
    }

    // public String encryptString(String valueToEncypt) {
    // return EncryptionDecryption.encryptString(valueToEncypt);
    // }

    public boolean isOnline() {
        boolean mConnected;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();
            mConnected = networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnected();

        } catch (Exception e) {
            mConnected = false;
        }
        return mConnected;

    }

    /**
     * ***********************************************************************************************
     * <p/>
     * This method is used to check if the content can be resolved while playid
     * a video in the YouTube Player
     * <p/>
     * ************************************************************************************************
     */
    public boolean canResolveIntent(Context context, Intent intent) {
        List<ResolveInfo> resolveInfo = context.getPackageManager()
                .queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    public String getDateAndTime(String dateandTime, String currentFormat,
                                 String selectedFormat) {
        StringBuilder dateTime = new StringBuilder();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date tempDate = simpleDateFormat.parse(dateandTime.split("T")[0]);
            Date tempTime = timeFormat.parse(dateandTime.split("T")[1]);
            // String[] dateTimeString = dateandTime.split("T");
            SimpleDateFormat dateFormat = new SimpleDateFormat(selectedFormat);

            String date = dateFormat.format(tempDate);

            dateTime.append(date.toString());

        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateTime.toString();

    }

    public String getDateAndTime1(String dateandTime, String currentFormat,
                                  String selectedFormat) {
        StringBuilder dateTime = new StringBuilder();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    currentFormat);

            Date tempDate = simpleDateFormat.parse(dateandTime);

            // String[] dateTimeString = dateandTime.split("T");
            SimpleDateFormat dateFormat = new SimpleDateFormat(selectedFormat);

            String date = dateFormat.format(tempDate);

            dateTime.append(date.toString());

        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateTime.toString();

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
                MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void justifyListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        int viewHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            viewHeight = listItem.getMeasuredHeight();
            totalHeight += viewHeight;
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight;
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    public String getDateAndTime(String dateandTime) {
        StringBuilder dateTime = new StringBuilder();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date tempDate = simpleDateFormat.parse(dateandTime.split("T")[0]);
            Date tempTime = timeFormat.parse(dateandTime.split("T")[1]);
            // String[] dateTimeString = dateandTime.split("T");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat actualTimeFormat = new SimpleDateFormat("hh:mm a");
            String date = dateFormat.format(tempDate);

            String time = actualTimeFormat.format(tempTime);
            dateTime.append(date.toString());
            dateTime.append("\t");

            dateTime.append(time.toString());

        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateTime.toString();

    }

    public boolean checkEndDateConstraints(String startDate, String endDate) {
        boolean isgreater = false;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            if (startDate != null && endDate != null) {
                Date date1 = sdf.parse(startDate);
                Date date2 = sdf.parse(endDate);
                if (date1.before(date2)) {
                    isgreater = false;
                } else if (date1.compareTo(date2) == 0) {
                    isgreater = false;
                } else {
                    isgreater = true;
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isgreater;

    }

    public String getCurrentDateAndTime(boolean isDateonly) {
        GregorianCalendar currentDay = (GregorianCalendar) GregorianCalendar
                .getInstance();
        String currentDateandTime;
        if (isDateonly) {
            currentDateandTime = (String) android.text.format.DateFormat
                    .format("MM/dd/yyyy", currentDay);
        } else {
            currentDateandTime = (String) android.text.format.DateFormat
                    .format("MM/dd/yyyy hh:mm a", currentDay);
        }

        return currentDateandTime;
    }

    public String set15MinutePrior(String dateandTime) {
        Calendar currentDate = Calendar.getInstance();
        Calendar selectedDate = (Calendar) currentDate.clone();
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        Date tempDate;
        try {
            tempDate = timeFormat.parse(dateandTime);
            selectedDate.setTime(tempDate);
            selectedDate.add(Calendar.MINUTE, -15);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return (String) android.text.format.DateFormat.format(
                "MM/dd/yyyy hh:mm a", selectedDate);

    }

    // /**
    // * Show single option alert dialog
    // *
    // * @param title
    // * of the dialog
    // * @param msg
    // * to show in alert dialog
    // * @param button1
    // * text
    // * @param iconResId
    // * if any icon to show
    // * @param listener
    // * for this alertdialog
    // * @param activityId
    // * to which it will callback
    // */
    // private AlertDialog mAlertDialog;

    // public void showSingleOption_AlertDialog(String title, String msg,
    // String button1, int iconResId,
    // OnAlertDialogFragmentListener listener, int activityId) {
    // try {
    // showHideKeyboard(false);
    // if (mAlertDialog != null) {
    // if (mAlertDialog.isShowing()) {
    // mAlertDialog.cancel();
    //
    // }
    // mAlertDialog = null;
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // }

    // /**
    // * Show single option alert dialog
    // *
    // * @param title
    // * of the dialog
    // * @param msg
    // * to show in alert dialog
    // * @param button1
    // * text
    // * @param button2
    // * text
    // * @param iconResId
    // * if any icon to show
    // * @param listener
    // * for this alertdialog
    // * @param activityId
    // * to which it will callback
    // */
    //
    // public void showDoubleOption_AlertDialog(String title, String msg,
    // String button1, String button2, int iconResId,
    // OnAlertDialogFragmentListener listener, int activityId) {
    // showHideKeyboard(false);
    //
    // new AlertDialogUtil().doubleOptionAlertDialog(context, title, msg,
    // button1, button2, iconResId, listener, activityId);
    // }

    /**
     * This method is used to show Toast message with custom message and the
     * custom duration. It will show the toast if
     *
     * @param message  not equal to null
     * @param duration in milliseconds greater than zero else it will show the toast
     *                 with default Toast duration.
     */
    public void showToast(String message, int duration) {

        if (!TextUtils.isEmpty(message) && !TextUtils.equals("null", message)) {
            try {
                if (duration < 0) {
                    Toast.makeText(context.getApplicationContext(), message,
                            duration).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), message,
                            Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String picturePath = cursor.getString(columnIndex); // returns null
        cursor.close();
        return picturePath;
    }

    /**
     * This method is used to show the custom Toast with custom layout and
     * duration
     *
     * @param view     inflated layout to show
     * @param duration for toast to show on UI
     * @param gravity  for the toast. For Example (LEFT,RIGHT,TOP,BOTTOM,CENTER)
     */

    public void showCustomToast(View view, int duration, int gravity) {
        Toast customToast = new Toast(context);
        if (view != null) {
            customToast.setView(view);

            if (duration > 0) {
                customToast.setDuration(duration);

            } else {
                customToast.setDuration(Toast.LENGTH_SHORT);
            }
            if (gravity > 0) {
                customToast.setGravity(gravity, 0, 0);
            }

            customToast.show();
        }

    }

    /**
     * This method is used to get width and height of screen using Display class
     * methods. These methods are deprecated in new Android API's
     *
     * @return width and height in int[]
     */
    public int[] getDisplay_Width_Height() {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return new int[]{width, height};
    }

	/*	*//**
     * Used to request data from server. Or to send data in request.
     *
     * @param url
     *            to hit
     * @param id
     *            callback id where to get response
     * @param callback
     *            where to send the response
     * @param key
     *            to send as embedded in url. if null simple get method will be
     *            called
     * @param value
     *            to send with related keys embedded in url.if null simple get
     *            method will be called
     */

    // public void getUrl(String url, int id, OnWebServiceProcess callback,
    // String[] key, String[] value) {
    // if (key != null && value != null) {
    // if (key.length > 0 && value.length > 0) {
    // WebServiceAsync.getInstance(context, callback).get(url, id,
    // key, value);
    //
    // }
    // } else {
    // WebServiceAsync.getInstance(context, callback).get(url, id);
    // }
    // }

    /**
     * Used to post data to server. Or to send data to server in Basic name
     * value pair or as jsonObject .
     *
     * @param url
     *            to hit
     * @param id
     *            callback id where to get response
     * @param callback
     *            where to send the response
     * @param key
     *            to send as embedded in url. if null simple get method will be
     *            called
     * @param value
     *            to send with related keys embedded in url.if null simple get
     *            method will be called
     * @param isJsonobject
     *            if true then the key value pair will be send as jsonobject
     *            else BasicNamevaluepair will be used
     */

    // public void postUrl(String url, int id, OnWebServiceProcess callback,
    // String[] key, String[] value, boolean isJsonobject) {
    // if (key != null && value != null) {
    // if (key.length > 0 && value.length > 0) {
    // WebServiceAsync.getInstance(context, callback).post(url, id,
    // isJsonobject, key, value);
    //
    // }
    // }
    // }

    /**
     * This method is used to get width and height of screen using Display
     * Metrices
     *
     * @return width and height in int[]
     */

    public int[] getDisplayMetrices() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels / 2;
        return new int[]{width, height};
    }


    /**
     * This method is used to get Window Manager Services.
     *
     * @return WindowManager
     */
    public WindowManager getWindowManager() {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return windowManager;
    }

    /**
     * Show single option alert dialog
     *
     * @param title      of the dialog
     * @param msg        to show in alert dialog
     * @param button1    text
     * @param button2    text
     * @param button3    text
     * @param iconResId  if any icon to show
     * @param listener   for this alertdialog
     * @param activityId to which it will callback
     */

    public void showThreeOption_AlertDialog(String title, String msg,
                                            String button1, String button2, String button3, int iconResId,
                                            OnAlertDialogFragmentListener listener, int activityId) {
        showHideKeyboard(false);

        new AlertDialogUtil().threeOptionAlertDialog(context, title, msg,
                button1, button2, button3, iconResId, listener, activityId);
    }

    public void showDoubleOption_AlertDialog(String title, String msg,
                                             String button1, String button2, int iconResId,
                                             OnAlertDialogFragmentListener listener, int activityId) {
        showHideKeyboard(false);

        new AlertDialogUtil().doubleOptionAlertDialog(context, title, msg,
                button1, button2, iconResId, listener, activityId);
    }

    public void showDoubleOptionCustomAlertDialog(String title, String msg,
                                                  OnAlertDialogFragmentListener listener, int activityId) {
        showHideKeyboard(false);

        new AlertDialogUtil().doubleOptionCustomAlertDialog(context, title, msg, listener, activityId);
    }

    public void showSingleOption_AlertDialog(String title, String msg,
                                             String button1, int iconResId,
                                             OnAlertDialogFragmentListener listener, int activityId) {
        showHideKeyboard(false);

        new AlertDialogUtil().singleOptionAlertDialog(context, title, msg,
                button1, iconResId, listener, activityId);
    }

    /**
     * This method is used to print log.if no type is matched then it will print
     * the message with default type.
     *
     * @param type    of log to print.(For example use Type.Error show message as
     *                error)
     * @param TAG     to represents Log.
     * @param message to print
     */

    public void showLog(Type type, String TAG, String message) {
        try {
            if (type != null) {
                switch (type) {
                    case DEBUG:
                        Log.d(TAG, message);
                        break;
                    case VERBOSE:
                        Log.v(TAG, message);
                        break;

                    case ERROR:
                        Log.e(TAG, message);
                        break;

                    case INFO:
                        Log.i(TAG, message);
                        break;

                    case WARN:
                        Log.w(TAG, message);
                        break;
                    default:
                        Log.e(TAG, message);
                        break;
                }
            } else {
                Log.e(TAG, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public AssetManager getAssetManager() {
        return context.getAssets();
    }

    ;

    public Resources getResources() {
        return context.getResources();
    }

    /**
     * Method to link the text
     *
     * @param textView
     * @param textViewText
     * @param texttoLinkArray
     * @param linkedTextArray
     * @param ifUrl
     */
    // public void linkifyText(TextView textView, String textViewText,
    // String[] texttoLinkArray, LinkTextModel[] linkedTextArray,
    // String ifUrl) {
    // LinkifyText.linkifyText(context, textView, textViewText,
    // texttoLinkArray, linkedTextArray, LinkType.ACTIVITY);
    // }

    /**
     * Check if media is mounted. Is External Storage is available
     *
     * @return true if available else false.
     */
    public boolean isMediaMounted() {
        if (TextUtils.equals(Environment.getExternalStorageState(),
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    ;


    /**
     * This method is used to check if the specific permission is declared in
     * the manifest file for given operation.
     *
     * @param permissions
     * @return true if permission granted else false
     */
    public boolean checkPermissions(BasicPermissions permissions) {
        int res;
        switch (permissions) {

            case INTERNET:
                res = context.checkCallingOrSelfPermission(permission.INTERNET);

                return (res == PackageManager.PERMISSION_GRANTED);

            case WRITE_PERMISSION:
                res = context
                        .checkCallingOrSelfPermission(permission.WRITE_EXTERNAL_STORAGE);

                return (res == PackageManager.PERMISSION_GRANTED);

            case ACCESS_NETWORK_STATE:

                res = context
                        .checkCallingOrSelfPermission(permission.ACCESS_NETWORK_STATE);

                return (res == PackageManager.PERMISSION_GRANTED);
            case READ_CONTACTS:

                res = context
                        .checkCallingOrSelfPermission(permission.READ_CONTACTS);

                return (res == PackageManager.PERMISSION_GRANTED);
            case WRITE_CONTACTS:

                res = context
                        .checkCallingOrSelfPermission(permission.WRITE_CONTACTS);

                return (res == PackageManager.PERMISSION_GRANTED);

            case CAMERA:
                res = context.checkCallingOrSelfPermission(permission.CAMERA);
                return (res == PackageManager.PERMISSION_GRANTED);

            case ACCESS_FINE_LOCATION:
                res = context
                        .checkCallingOrSelfPermission(permission.ACCESS_FINE_LOCATION);
                return (res == PackageManager.PERMISSION_GRANTED);
            case ACCESS_COARSE_LOCATION:
                res = context
                        .checkCallingOrSelfPermission(permission.ACCESS_COARSE_LOCATION);
                return (res == PackageManager.PERMISSION_GRANTED);
            case CHANGE_NETWORK_STATE:
                res = context
                        .checkCallingOrSelfPermission(permission.CHANGE_NETWORK_STATE);
                return (res == PackageManager.PERMISSION_GRANTED);
            case MODIFY_PHONE_STATE:
                res = context
                        .checkCallingOrSelfPermission(permission.MODIFY_PHONE_STATE);
                return (res == PackageManager.PERMISSION_GRANTED);
            default:
                return false;
        }
        // return false;

    }

	/*
     * public void showCustomProgressDialog(int resLayoutID) {
	 * CustomProgressDialog.getInstance(context).showCustomDialog(resLayoutID);
	 * }
	 *
	 * public void showProgressDialog(View view) {
	 * CustomProgressDialog.getInstance(context).showCustomDialog(view); }
	 */

    /**
     * This method is used to check the connectivity of the selected network
     * type.
     *
     * @param networkType
     * @return true if selected network is connected else return false.
     */
    public boolean checkInternetConnection(NetworkType networkType) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        switch (networkType) {
            case WIFI:
                for (NetworkInfo ni : netInfo) {

                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            return true;
                }

            case MOBILE:
                for (NetworkInfo ni : netInfo) {

                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            return true;
                }

            default:
                return false;
        }
    }


    /**
     * This method is used to show and hide the keyboard
     *
     * @param isShow if true show keyboard else hide.
     */
    public static void showHideKeyboard(boolean isShow) {
        try {

            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (isShow) {
                imm.showSoftInput(((Activity) context).getCurrentFocus(),
                        InputMethodManager.SHOW_FORCED);
            } else {
                if (imm.isAcceptingText()) {
                    try {

                        imm.hideSoftInputFromWindow(((Activity) context)
                                .getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * This method is used to enable or disable Gps programatically
     */
    public void toogleGps(boolean enabledGPS) {
        if (enabledGPS) {
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);
            context.sendBroadcast(intent);
        } else {
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", false);
            context.sendBroadcast(intent);

        }
    }

    /**
     * This method is used to enable and disable mobile network from the
     * application
     *
     * @param enableNetwork set accessible else false
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void toggleNetwork(boolean enableNetwork) {
        if (isSdkVersionAboveGingerBread()) {
            ConnectivityManager conman = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            Class conmanClass;
            try {
                conmanClass = Class.forName(conman.getClass().getName());

                Field iConnectivityManagerField = conmanClass
                        .getDeclaredField("mService");
                iConnectivityManagerField.setAccessible(true);
                Object iConnectivityManager = iConnectivityManagerField
                        .get(conman);
                Class iConnectivityManagerClass = Class
                        .forName(iConnectivityManager.getClass().getName());
                @SuppressWarnings("unchecked")
                Method setMobileDataEnabledMethod = iConnectivityManagerClass
                        .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                setMobileDataEnabledMethod.setAccessible(true);

                setMobileDataEnabledMethod.invoke(iConnectivityManager,
                        enableNetwork);

            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Method dataConnSwitchmethod;
            Class telephonyManagerClass;
            Object ITelephonyStub;
            Class ITelephonyClass;
            if (checkPermissions(BasicPermissions.MODIFY_PHONE_STATE)) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) context
                            .getSystemService(Context.TELEPHONY_SERVICE);

                    if (telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED) {
                        enableNetwork = true;
                    } else {
                        enableNetwork = false;
                    }

                    telephonyManagerClass = Class.forName(telephonyManager
                            .getClass().getName());
                    Method getITelephonyMethod = telephonyManagerClass
                            .getDeclaredMethod("getITelephony");
                    getITelephonyMethod.setAccessible(true);
                    ITelephonyStub = getITelephonyMethod
                            .invoke(telephonyManager);
                    ITelephonyClass = Class.forName(ITelephonyStub.getClass()
                            .getName());

                    if (enableNetwork) {
                        dataConnSwitchmethod = ITelephonyClass
                                .getDeclaredMethod("disableDataConnectivity");
                    } else {
                        dataConnSwitchmethod = ITelephonyClass
                                .getDeclaredMethod("enableDataConnectivity");
                    }
                    dataConnSwitchmethod.setAccessible(true);
                    dataConnSwitchmethod.invoke(ITelephonyStub);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Check application build API.If equal to GINGERBREAD or above return true
     * else false.
     *
     * @return
     */
    public boolean isSdkVersionAboveGingerBread() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }

    public boolean isSdkVersionAboveJellyBean() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return true;
        }
        return false;
    }

    /**
     * Fragment Utility Methods
     */

    /**
     * Used to send SMS from the application. User can also send long text
     * message using this method
     *
     * @param phoneNumber of the used to whom the message to be sent
     * @param message     to send
     */
    public void sendLongSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(message);
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null,
                null);
    }

    /**
     * Used to send email from the application.
     *
     * @param subject of the email
     * @param content to send
     * @param sendTo  recipient of the email
     */
    public void sendEmail(String subject, String content, String sendTo) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");

        if (!checkIfStringIsNullOrEmpty(sendTo)) {
            intent.putExtra(Intent.EXTRA_EMAIL, sendTo);
            intent.setData(Uri.parse("mailto:" + sendTo));

        } else {
            intent.putExtra(Intent.EXTRA_EMAIL, "");
            intent.setData(Uri.parse("mailto:" + ""));
        }
        if (!checkIfStringIsNullOrEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (!checkIfStringIsNullOrEmpty(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        try {

            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Email error:", e.toString());
        }
    }

    /**
     * This method is used to replaceFragment with another fragment
     *
     * @param replaceId   Set id of the view on which fragment is to replaced
     * @param fragment    fragment which is to called
     * @param tag         Set tag if needed otherwise set null
     * @param isBackStack Set true if need backStack else false
     */
    public final void replaceFragment(int replaceId, Fragment fragment,
                                      String tag, boolean isBackStack, Context activityContext) {

            FragmentTransaction ft = ((AppCompatActivity) activityContext)
                    .getSupportFragmentManager().beginTransaction();

            boolean fragmentPopped = ((AppCompatActivity) activityContext)
                    .getSupportFragmentManager().popBackStackImmediate(tag, 0);
            Log.e("Last fragment", "" + getActiveFragment(activityContext));
            if (!checkIfStringIsNullOrEmpty(tag)) {
                ft.replace(replaceId, fragment, tag);
            } else {
                ft.replace(replaceId, fragment);
            }

            if (isBackStack && !tag.equals(getActiveFragment(activityContext))) {
                ft.addToBackStack(tag);
            }
            // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();


    }

    public String getActiveFragment(Context activityContext) {
        if (((AppCompatActivity) activityContext).getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = ((AppCompatActivity) activityContext).getSupportFragmentManager().getBackStackEntryAt(((AppCompatActivity) activityContext).getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return ((Fragment) ((AppCompatActivity) activityContext).getSupportFragmentManager().findFragmentByTag(tag)).getTag();
    }

    public final void replaceFragmentWithAnim(int replaceId, Fragment fragment,
                                              String tag, boolean isBackStack, Context activityContext) {
        FragmentTransaction ft = ((AppCompatActivity) activityContext)
                .getSupportFragmentManager().beginTransaction();

        if (!checkIfStringIsNullOrEmpty(tag)) {
            ft.replace(replaceId, fragment, tag);
        } else {
            ft.replace(replaceId, fragment);
        }
        if (isBackStack) {
            ft.addToBackStack(tag);
        }
        // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    /**
     * This method is used to replaceFragment with another fragment
     *
     * @param replaceId   Set id of the view on which fragment is to replaced
     * @param fragment    fragment which is to called
     * @param tag         Set tag if needed otherwise set null
     * @param isBackStack Set true if need backStack else false
     */
    public final void replaceChildFragment(int replaceId, Fragment fragment,
                                           String tag, boolean isBackStack, Fragment activityContext) {
        FragmentTransaction ft = activityContext.getChildFragmentManager().beginTransaction();
        if (!checkIfStringIsNullOrEmpty(tag)) {
            ft.replace(replaceId, fragment, tag);
        } else {
            ft.replace(replaceId, fragment);
        }
        if (isBackStack) {
            ft.addToBackStack(tag);
        }
        // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    /**
     * This method is used to replaceFragment with another fragment
     *
     * @param replaceId   Set id of the view on which fragment is to replaced
     * @param fragment    fragment which is to called
     * @param tag         Set tag if needed otherwise set null
     * @param isBackStack Set true if need backStack else false
     */
    public final void replaceChildFragmentWithAnim(int replaceId, Fragment fragment,
                                                   String tag, boolean isBackStack, Fragment activityContext, int leftAnim, int rightAnim) {
        FragmentTransaction ft = activityContext.getChildFragmentManager().beginTransaction();
        ft.setCustomAnimations(leftAnim, rightAnim);
        if (!checkIfStringIsNullOrEmpty(tag)) {
            ft.replace(replaceId, fragment, tag);
        } else {
            ft.replace(replaceId, fragment);
        }
        if (isBackStack) {
            ft.addToBackStack(tag);
        }
        // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    /**
     * This method is used to addFragment for the first time
     *
     * @param replaceId   Set id of the view on which fragment is to replaced
     * @param fragment    fragment which is to called
     * @param tag         Set tag if needed otherwise set null
     * @param isBackStack Set true if need backStack else false
     */
    public final void addFragment(int replaceId, Fragment fragment, String tag,
                                  boolean isBackStack, Context activityContext) {
        FragmentTransaction ft = ((AppCompatActivity) activityContext)
                .getSupportFragmentManager().beginTransaction();
        if (!checkIfStringIsNullOrEmpty(tag)) {
            ft.add(replaceId, fragment, tag);
        } else {
            ft.add(replaceId, fragment);
        }
        if (isBackStack) {
            ft.addToBackStack(tag);
        }
        // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    /**
     * This method is used to popFragment from stack
     */
    public final void popFragment(int replaceId, final Context activityContext) {
        try {
            FragmentManager fragmentManager = ((AppCompatActivity) activityContext).getSupportFragmentManager();
            if (fragmentManager
                    .getBackStackEntryCount() > 0) {
                FragmentTransaction fragTrans = fragmentManager.beginTransaction();
                fragTrans.remove(fragmentManager.findFragmentById(replaceId));
                fragTrans.commit();
                fragmentManager.popBackStackImmediate();
            } else {
                showDoubleOptionCustomAlertDialog(activityContext.getString(R.string.app_name), "Do you really want to exit from the app?", new OnAlertDialogFragmentListener() {
                    @Override
                    public void onPositiveButtonClick(int id) {
                        ((AppCompatActivity) activityContext).finish();
                    }

                    @Override
                    public void onNegativeButtonClick(int id) {

                    }

                    @Override
                    public void onNeutralizeButtonClick(int id) {

                    }

                    @Override
                    public void interfaceAttachError(int fragmentId, String errorResponse) {

                    }

                    @Override
                    public void onBackPress(int id) {

                    }
                }, 101);
                // if (((Activity) activityContext) instanceof
                // CommonHomeScreenActivity) {
                // BaseActivityClass.isRunning = false;
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to popFragment from stack
     */
    public final void popChildFragment(int replaceId, final Fragment fragmentContext) {
        try {
            if (((BaseFragment) fragmentContext).getChildFragmentManager()
                    .getBackStackEntryCount() > 0) {
//                FragmentTransaction fragTrans = ((BaseFragment) fragmentContext)
//                        .getSupportFragmentManager().beginTransaction();
//                fragTrans.remove(((BaseFragment) fragmentContext)
//                        .getSupportFragmentManager().findFragmentById(replaceId));
//                fragTrans.commit();
                ((BaseFragment) fragmentContext).getChildFragmentManager()
                        .popBackStackImmediate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to clear all the fragments from stack
     */
    public final void clearBackStack(Context activityContext) {
        try {

            FragmentManager fm = ((AppCompatActivity) activityContext)
                    .getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                String fragTag = fm.getBackStackEntryAt(i).getName();
                Fragment fragment = fm.findFragmentByTag(fragTag);
                FragmentTransaction fragTrans = ((AppCompatActivity) activityContext)
                        .getSupportFragmentManager().beginTransaction();
                fragTrans.remove(fragment);
                fragTrans.commit();
                fm.popBackStack();

            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to clear all the fragments from stack
     */
    public final void popBackStack(Context activityContext, String tag) {
        try {

            FragmentManager fm = ((AppCompatActivity) activityContext).getSupportFragmentManager();

            fm.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    public boolean checkIfStringIsNullOrEmpty(String value) {
        boolean isnullorEmpty = false;
        if (TextUtils.equals(value, null)) {
            isnullorEmpty = true;
        } else if (TextUtils.equals(value, "")) {
            isnullorEmpty = true;
        } else if (TextUtils.equals(value, "null")) {
            isnullorEmpty = true;
        } else if (TextUtils.equals(value, "[]")) {
            isnullorEmpty = true;
        } else if (TextUtils.equals(value, " ")) {
            isnullorEmpty = true;
        } else if (TextUtils.equals(value, "{}")) {
            isnullorEmpty = true;
        }

        return isnullorEmpty;
    }


    public void showDatePickerDialog(
            final DatePickerdialogListener datePickerListener,
            final int callBackId) {
        // UrlConstants.isShowingDialog = true;
        Calendar calender = Calendar.getInstance();
        OnDateSetListener onDateChangeListener = new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // UrlConstants.isShowingDialog = false;
                datePickerListener.onDateChanged(year, monthOfYear, dayOfMonth,
                        callBackId);
            }

        };
        DatePickerDialog datePicker = new DatePickerDialog(context,
                onDateChangeListener, calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH));
        // datePicker.setCancelable(false);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
        datePicker.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                // UrlConstants.isShowingDialog = false;
            }
        });
    }

    public String convert24hrTo12hr(int hour, int minute) {

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 00);
        SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
        String convert = displayFormat.format(cal.getTime());
        return convert;

    }

    public void ShowTimePicker(
            final OnTimePickerDialogListener timePickerListener,
            final int callBackId) {
        // UrlConstants.isShowingDialog = true;
        Calendar calender = Calendar.getInstance();
        OnTimeSetListener onTimeChangeListener = new OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                // UrlConstants.isShowingDialog = false;
                String in12Hours = convert24hrTo12hr(hourOfDay, minute);
                timePickerListener.onTimeChanged(hourOfDay, minute, in12Hours,
                        callBackId);
            }
        };
        try {
            if (timePicker != null) {
                timePicker.cancel();
                timePicker = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (timePicker == null) {
            timePicker = new TimePickerDialog(context, onTimeChangeListener,
                    calender.HOUR_OF_DAY, calender.MINUTE, true);
            // datePicker.setCancelable(false);
        }
        timePicker.show();

        timePicker.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                // UrlConstants.isShowingDialog = false;
            }
        });

    }

    public void showSpinnerDialog(ArrayAdapter<String> listAdapter,
                                  String title,
                                  final OnSpinnerDialogListener onSpinnerDialogListener,
                                  final int callbackId) {
        new AlertDialog.Builder(context).setTitle(title)
                .setAdapter(listAdapter, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        onSpinnerDialogListener.onSpinnerDialogClick(which,
                                callbackId);
                        dialog.dismiss();
                    }
                }).create().show();

    }


    public void showSpinnerDialog1(ArrayAdapter<?> listAdapter, String title,
                                   final OnSpinnerDialogListener onSpinnerDialogListener,
                                   final int callbackId) {

        new AlertDialog.Builder(context).setTitle(title)
                .setAdapter(listAdapter, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        onSpinnerDialogListener.onSpinnerDialogClick(which,
                                callbackId);
                        dialog.dismiss();
                    }
                }).create().show();

    }

    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean isEmailValid(final String hex) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(hex);
        return matcher.matches();

    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public boolean isValidUrl(String url) {

        boolean isValid = false;
        try {
            URL newUrl = new URL(url);
            isValid = true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return isValid;

    }

    /**
     * **************************************************************************
     * Checks whether the given mobile number is valid.
     *
     * @param mobile represents the mobile number.
     * @return true if the mobile number is valid, false otherwise.
     * @since 27-Jan-2015
     * **************************************************************************
     */
    public boolean isValidNumber(String mobile) {
        if (mobile == null) {
            return false;
        }
        return Patterns.PHONE.matcher(mobile).matches();
    }

    public void writeObjectOnSharedPreference(String prefName, Object object, String prefsKey) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutput;
        try {
            objectOutput = new ObjectOutputStream(arrayOutputStream);
            objectOutput.writeObject(object);
            byte[] data = arrayOutputStream.toByteArray();
            objectOutput.close();
            arrayOutputStream.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Base64OutputStream b64 = new Base64OutputStream(out, Base64.DEFAULT);
            b64.write(data);
            b64.close();
            out.close();

            Sharedprefrences.getInstance(context,
                    prefName).putString(
                    prefsKey, new String(out.toByteArray()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object readObjectFromSharedPreference(String prefName, String prefkey) {

        Object object = null;
        byte[] bytes = Sharedprefrences.getInstance(context, prefName).getString(prefkey, "{}").getBytes();
        if (bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
        Base64InputStream base64InputStream = new Base64InputStream(byteArray,
                Base64.DEFAULT);
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(base64InputStream);
            // UserLoginModel loginModel =
            // UserLoginModel.getInstance();
            object = in.readObject();
            in.close();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }


    public static String SHA256(String text, int length) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String resultStr;
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(text.getBytes("UTF-8"));
        byte[] digest = md.digest();

        StringBuffer result = new StringBuffer();
        for (byte b : digest) {
            result.append(String.format("%02x", b)); //convert to hex
        }
        //return result.toString();

        if (length > result.toString().length()) {
            resultStr = result.toString();
        } else {
            resultStr = result.toString().substring(0, length);
        }

        return resultStr;

    }


    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }

    public void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * This method is used to create encrypted message packet to send
     */


    private JSONObject encodeJson(String[] key, String[] value) {
        JSONObject obj = new JSONObject();
        int length = key.length;
        for (int i = 0; i < length; i++) {
            try {
                obj.put(key[i], value[i]);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return obj;
    }


    public long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public static boolean isValidEmail(String s) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(s);
        return matcher.matches();
    }


    /**
     * Type of Log. Used in ShowLog method to choose the type of log to show.
     *
     * @author siddharth.brahmi
     */
    public enum Type {
        DEBUG, VERBOSE, ERROR, INFO, WARN
    }

    /**
     * Enum to check permission to perform related operation with the
     * permissions in the application
     *
     * @author siddharth.brahmi
     */
    public enum BasicPermissions {
        MODIFY_PHONE_STATE, CHANGE_NETWORK_STATE, INTERNET, WRITE_PERMISSION, ACCESS_NETWORK_STATE, READ_CONTACTS, WRITE_CONTACTS, CAMERA, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
    }

    /**
     * Enum class to declare the types of networks used in android. It will be
     * used in checkInternetConnection method to check the connectivity of the
     * selected type
     *
     * @author siddharth.brahmi
     */
    public enum NetworkType {
        WIFI, MOBILE
    }

    public interface OnTimePickerDialogListener {
        public void onTimeChanged(int hourOfDay, int minute,
                                  String formattedValue, int callBackId);
    }

    public interface DatePickerdialogListener {

        public void onDateChanged(int year, int monthOfYear, int dayOfMonth,
                                  int callBackId);
    }


    public interface OnSpinnerDialogListener {
        void onSpinnerDialogClick(int position, int callBackId);

    }


    public String readTextFileFromAssets(String assetFileName) {
        StringBuilder total = new StringBuilder();
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(assetFileName);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total.toString();
    }


    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

//    public static boolean isTablet(Context context) {
//        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
//                >= Configuration.SCREENLAYOUT_SIZE_LARGE ;
//    }

    public static void setActivityScreenOrientation(Activity act) {
        boolean isTablet = false;

        if ((act.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            isTablet = true;
        }

        if ((act.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            isTablet = true;
        }


        if (isTablet) {
            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("0.00");
        return Double.valueOf(twoDForm.format(d));
    }

    public static BigDecimal roundTwoFloat(Float d) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static BigDecimal round(Double decimalPlace) {
        BigDecimal bd = new BigDecimal(decimalPlace);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static Double round2(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static boolean compareDates(String psDate1, String psDate2) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date1 = dateFormat.parse(psDate1);
        Date date2 = dateFormat.parse(psDate2);
        if (date2.after(date1) || date1.equals(date2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidPhone(String phone)
    {

        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean passwordValidation(String password)
    {
        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[A-Z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }
    public static String toTitleCase(String str) {
        if (str == null) {
            return null;
        }
        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i=0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }


}
