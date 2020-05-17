package com.app.rahul.gumsml;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView3;
    private String state;
    private String message;
    private static int isHealthy = -1;
    private static final int GALLERY_REQUEST = 1234;
    private final static int RESULT_LOAD_IMG = 1234;
    private static Bitmap bitmap;
    private static int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find, set listener for Floating Camera Button
        FloatingActionButton button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(this);

        textView3 = findViewById(R.id.textView3);
        state = "N/A";
        message = state;
        String text = textView3.getText() + state;
        textView3.setText(text);
    }

    private void detect(int isHealthy) {
        if (isHealthy == 1) {
            state = "HEALTHY";
            message = "Current State of Gums: " + state;

            textView3.setText(message);
        } else if (isHealthy == 2) {
            state = "NOT HEALTHY";
            message = "Current State of Gums: " + state;
            textView3.setText(message);
        }
    }

    // Used to go from home screen to camera screen
    public void onClick(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    private static int check(Bitmap bitmap) {
        int r = 0;
        int count = 0;
        double sumRatio = 0;
        int start = (int) (bitmap.getHeight() / 5.5);
        double green;
        double red;
        double blue;

        label:
        for (int row = start; row < bitmap.getHeight(); row++) {
            for (int col = 0; col < bitmap.getWidth(); col++) {
                red = Color.red(bitmap.getPixel(col, row));
                green = Color.green(bitmap.getPixel(col, row));
                if (red >= 200 && green >= 150) {
                    r = row - 25;
                    break label;
                }
            }
        }
        for (int col = 0; col < bitmap.getWidth(); col++) {
            red = Color.red(bitmap.getPixel(col, r));
            blue = Color.blue(bitmap.getPixel(col, r));
            green = Color.green(bitmap.getPixel(col, r));

            sumRatio += (green / blue) / red;
            count++;
        }
        sumRatio /= count;
        if (sumRatio > 0.004 && sumRatio < 0.008) {// no disease
            isHealthy = 1;
            return 1;
        } else {
            isHealthy = 2;
            return 2;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                        {
                            ImageView iView = new ImageView(new Context() {
                                @Override
                                public AssetManager getAssets() {
                                    return null;
                                }

                                @Override
                                public Resources getResources() {
                                    return null;
                                }

                                @Override
                                public PackageManager getPackageManager() {
                                    return null;
                                }

                                @Override
                                public ContentResolver getContentResolver() {
                                    return null;
                                }

                                @Override
                                public Looper getMainLooper() {
                                    return null;
                                }

                                @Override
                                public Context getApplicationContext() {
                                    return null;
                                }

                                @Override
                                public void setTheme(int resid) {

                                }

                                @Override
                                public Resources.Theme getTheme() {
                                    return null;
                                }

                                @Override
                                public ClassLoader getClassLoader() {
                                    return null;
                                }

                                @Override
                                public String getPackageName() {
                                    return null;
                                }

                                @Override
                                public ApplicationInfo getApplicationInfo() {
                                    return null;
                                }

                                @Override
                                public String getPackageResourcePath() {
                                    return null;
                                }

                                @Override
                                public String getPackageCodePath() {
                                    return null;
                                }

                                @Override
                                public SharedPreferences getSharedPreferences(String name, int mode) {
                                    return null;
                                }

                                @Override
                                public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
                                    return false;
                                }

                                @Override
                                public boolean deleteSharedPreferences(String name) {
                                    return false;
                                }

                                @Override
                                public FileInputStream openFileInput(String name) {
                                    return null;
                                }

                                @Override
                                public FileOutputStream openFileOutput(String name, int mode) {
                                    return null;
                                }

                                @Override
                                public boolean deleteFile(String name) {
                                    return false;
                                }

                                @Override
                                public File getFileStreamPath(String name) {
                                    return null;
                                }

                                @Override
                                public File getDataDir() {
                                    return null;
                                }

                                @Override
                                public File getFilesDir() {
                                    return null;
                                }

                                @Override
                                public File getNoBackupFilesDir() {
                                    return null;
                                }

                                @Nullable
                                @Override
                                public File getExternalFilesDir(@Nullable String type) {
                                    return null;
                                }

                                @Override
                                public File[] getExternalFilesDirs(String type) {
                                    return new File[0];
                                }

                                @Override
                                public File getObbDir() {
                                    return null;
                                }

                                @Override
                                public File[] getObbDirs() {
                                    return new File[0];
                                }

                                @Override
                                public File getCacheDir() {
                                    return null;
                                }

                                @Override
                                public File getCodeCacheDir() {
                                    return null;
                                }

                                @Nullable
                                @Override
                                public File getExternalCacheDir() {
                                    return null;
                                }

                                @Override
                                public File[] getExternalCacheDirs() {
                                    return new File[0];
                                }

                                @Override
                                public File[] getExternalMediaDirs() {
                                    return new File[0];
                                }

                                @Override
                                public String[] fileList() {
                                    return new String[0];
                                }

                                @Override
                                public File getDir(String name, int mode) {
                                    return null;
                                }

                                @Override
                                public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
                                    return null;
                                }

                                @Override
                                public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler) {
                                    return null;
                                }

                                @Override
                                public boolean moveDatabaseFrom(Context sourceContext, String name) {
                                    return false;
                                }

                                @Override
                                public boolean deleteDatabase(String name) {
                                    return false;
                                }

                                @Override
                                public File getDatabasePath(String name) {
                                    return null;
                                }

                                @Override
                                public String[] databaseList() {
                                    return new String[0];
                                }

                                @Override
                                public Drawable getWallpaper() {
                                    return null;
                                }

                                @Override
                                public Drawable peekWallpaper() {
                                    return null;
                                }

                                @Override
                                public int getWallpaperDesiredMinimumWidth() {
                                    return 0;
                                }

                                @Override
                                public int getWallpaperDesiredMinimumHeight() {
                                    return 0;
                                }

                                @Override
                                public void setWallpaper(Bitmap bitmap) {

                                }

                                @Override
                                public void setWallpaper(InputStream data) {

                                }

                                @Override
                                public void clearWallpaper() {

                                }

                                @Override
                                public void startActivity(Intent intent) {

                                }

                                @Override
                                public void startActivity(Intent intent, @Nullable Bundle options) {

                                }

                                @Override
                                public void startActivities(Intent[] intents) {

                                }

                                @Override
                                public void startActivities(Intent[] intents, Bundle options) {

                                }

                                @Override
                                public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) {

                                }

                                @Override
                                public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) {

                                }

                                @Override
                                public void sendBroadcast(Intent intent) {

                                }

                                @Override
                                public void sendBroadcast(Intent intent, @Nullable String receiverPermission) {

                                }

                                @Override
                                public void sendOrderedBroadcast(Intent intent, @Nullable String receiverPermission) {

                                }

                                @Override
                                public void sendOrderedBroadcast(@NonNull Intent intent, @Nullable String receiverPermission, @Nullable BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

                                }

                                @Override
                                public void sendBroadcastAsUser(Intent intent, UserHandle user) {

                                }

                                @Override
                                public void sendBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission) {

                                }

                                @Override
                                public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

                                }

                                @Override
                                public void sendStickyBroadcast(Intent intent) {

                                }

                                @Override
                                public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

                                }

                                @Override
                                public void removeStickyBroadcast(Intent intent) {

                                }

                                @Override
                                public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {

                                }

                                @Override
                                public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

                                }

                                @Override
                                public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {

                                }

                                @Nullable
                                @Override
                                public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter) {
                                    return null;
                                }

                                @Nullable
                                @Override
                                public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags) {
                                    return null;
                                }

                                @Nullable
                                @Override
                                public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler) {
                                    return null;
                                }

                                @Nullable
                                @Override
                                public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler, int flags) {
                                    return null;
                                }

                                @Override
                                public void unregisterReceiver(BroadcastReceiver receiver) {

                                }

                                @Nullable
                                @Override
                                public ComponentName startService(Intent service) {
                                    return null;
                                }

                                @Nullable
                                @Override
                                public ComponentName startForegroundService(Intent service) {
                                    return null;
                                }

                                @Override
                                public boolean stopService(Intent service) {
                                    return false;
                                }

                                @Override
                                public boolean bindService(Intent service, @NonNull ServiceConnection conn, int flags) {
                                    return false;
                                }

                                @Override
                                public void unbindService(@NonNull ServiceConnection conn) {

                                }

                                @Override
                                public boolean startInstrumentation(@NonNull ComponentName className, @Nullable String profileFile, @Nullable Bundle arguments) {
                                    return false;
                                }

                                @Nullable
                                @Override
                                public Object getSystemService(@NonNull String name) {
                                    return null;
                                }

                                @Nullable
                                @Override
                                public String getSystemServiceName(@NonNull Class<?> serviceClass) {
                                    return null;
                                }

                                @Override
                                public int checkPermission(@NonNull String permission, int pid, int uid) {
                                    return PackageManager.PERMISSION_GRANTED;
                                }

                                @Override
                                public int checkCallingPermission(@NonNull String permission) {
                                    return PackageManager.PERMISSION_GRANTED;
                                }

                                @Override
                                public int checkCallingOrSelfPermission(@NonNull String permission) {
                                    return PackageManager.PERMISSION_GRANTED;
                                }

                                @Override
                                public int checkSelfPermission(@NonNull String permission) {
                                    return PackageManager.PERMISSION_GRANTED;
                                }

                                @Override
                                public void enforcePermission(@NonNull String permission, int pid, int uid, @Nullable String message) {

                                }

                                @Override
                                public void enforceCallingPermission(@NonNull String permission, @Nullable String message) {

                                }

                                @Override
                                public void enforceCallingOrSelfPermission(@NonNull String permission, @Nullable String message) {

                                }

                                @Override
                                public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {

                                }

                                @Override
                                public void revokeUriPermission(Uri uri, int modeFlags) {

                                }

                                @Override
                                public void revokeUriPermission(String toPackage, Uri uri, int modeFlags) {

                                }

                                @Override
                                public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
                                    return PackageManager.PERMISSION_GRANTED;
                                }

                                @Override
                                public int checkCallingUriPermission(Uri uri, int modeFlags) {
                                    return PackageManager.PERMISSION_GRANTED;
                                }

                                @Override
                                public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
                                    return PackageManager.PERMISSION_GRANTED;
                                }

                                @Override
                                public int checkUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags) {
                                    return PackageManager.PERMISSION_GRANTED;
                                }

                                @Override
                                public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {

                                }

                                @Override
                                public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {

                                }

                                @Override
                                public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {

                                }

                                @Override
                                public void enforceUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags, @Nullable String message) {

                                }

                                @Override
                                public Context createPackageContext(String packageName, int flags) {
                                    return null;
                                }

                                @Override
                                public Context createContextForSplit(String splitName) {
                                    return null;
                                }

                                @Override
                                public Context createConfigurationContext(@NonNull Configuration overrideConfiguration) {
                                    return null;
                                }

                                @Override
                                public Context createDisplayContext(@NonNull Display display) {
                                    return null;
                                }

                                @Override
                                public Context createDeviceProtectedStorageContext() {
                                    return null;
                                }

                                @Override
                                public boolean isDeviceProtectedStorage() {
                                    return false;
                                }
                            });
                            iView.setImageBitmap(bitmap);
                        }
                    } catch (Exception e) {
                        Log.i("TAG", "Some exception " + e.toString());

                    }
                    detect(check(bitmap));
                    switch (x) {
                        case 0:
                            addToHistory();
                            break;
                        case 1:
                            addToHistory2();
                            break;
                        case 2:
                            addToHistory3();
                            break;
                        default:
                            addToHistory4();
                            break;
                    }
                    break;
                default:
                    detect(check(bitmap));
                    switch (x) {
                        case 0:
                            addToHistory();
                            break;
                        case 1:
                            addToHistory2();
                            break;
                        case 2:
                            addToHistory3();
                            break;
                        default:
                            addToHistory4();
                            break;
                    }
                    break;
            }
    }

    private void addToHistory() {
        Date currentTime = Calendar.getInstance().getTime();

        TextView textView8 = findViewById(R.id.textView8);
        String gumState;
        if (isHealthy == 1)
            gumState = "HEALTHY";
        else
            gumState = "UNHEALTHY";

        String text = currentTime + "    " + gumState;
        textView8.setText(text);
        x++;
    }

    private void addToHistory2() {
        Date currentTime = Calendar.getInstance().getTime();
        TextView textView11 = findViewById(R.id.textView11);
        String gumState;
        if (isHealthy == 1)
            gumState = "HEALTHY";
        else
            gumState = "UNHEALTHY";

        String text = currentTime + "    " + gumState;
        textView11.setText(text);
        x++;
    }

    private void addToHistory3() {
        Date currentTime = Calendar.getInstance().getTime();
        TextView textView14 = findViewById(R.id.textView14);
        String gumState;
        if (isHealthy == 1) {
            gumState = "HEALTHY";
        } else
            gumState = "UNHEALTHY";

        String text = currentTime + "    " + gumState;
        textView14.setText(text);
        x++;
    }

    private void addToHistory4() {
        Date currentTime = Calendar.getInstance().getTime();
        TextView textView17 = findViewById(R.id.textView17);
        String gumState;
        if (isHealthy == 1)
            gumState = "HEALTHY";
        else
            gumState = "UNHEALTHY";

        String text = currentTime + "    " + gumState;
        textView17.setText(text);
    }

    public int ml(Bitmap c) {
        return 0;
    }

}
