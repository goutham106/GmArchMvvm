package com.gm.rtonumbermatcher.util;

import android.app.Application;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 * <p>
 * Crash related tool class
 * {@see https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CrashUtils.java}
 */

public final class CrashUtils {

    //The singleton tool class holds a weak reference for Application
    private static WeakReference<Application> mApplication;

    private static boolean mInitialized;
    private static String defaultDir;
    private static String dir;

    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH-mm-ss", Locale.getDefault());

    private static final String CRASH_HEAD;

    private static final Thread.UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER;
    private static final Thread.UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER;

    static {
        CRASH_HEAD = "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// Equipment manufacturers
                "\nDevice Model       : " + Build.MODEL +// Equipment model
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// system version
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK version
                "\n************* Crash Log Head ****************\n\n";

        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

        UNCAUGHT_EXCEPTION_HANDLER = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                if (e == null) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    return;
                }
                Date now = new Date(System.currentTimeMillis());
                String fileName = FORMAT.format(now) + ".txt";
                final String fullPath = (dir == null ? defaultDir : dir) + fileName;
                if (!createOrExistsFile(fullPath))
                    return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter pw = null;
                        try {
                            pw = new PrintWriter(new FileWriter(fullPath, false));
                            pw.write(CRASH_HEAD);
                            e.printStackTrace(pw);
                            Throwable cause = e.getCause();
                            while (cause != null) {
                                cause.printStackTrace(pw);
                                cause = cause.getCause();
                            }
                        } catch (IOException e) {
                            // e.printStackTrace();
                            Log.e("uncaughtException", e.getMessage());
                        } finally {
                            if (pw != null) {
                                pw.close();
                                System.exit(0);
                            }
                        }
                    }
                }).start();
                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
                }
            }
        };
    }

    private CrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * initialization
     * <p>Need to add permission {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @return {@code true}: Initialized successfully<br>{@code false}: initialization failed
     */
    public static boolean init(Application application) {
        if (mApplication == null)
            mApplication = new WeakReference<>(application);
        return init(application, "");
    }

    /**
     * initialization
     * <p>Need to add permission {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @param crashDir Crash file storage directory
     * @return {@code true}: Initialized successfully<br>{@code false}: initialization failed
     */
    public static boolean init(Application application, @NonNull final File crashDir) {
        if (mApplication == null)
            mApplication = new WeakReference<>(application);
        return init(application, crashDir.getAbsolutePath() + FILE_SEP);
    }

    /**
     * initialization
     * <p>Need to add permission {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @param crashDir Crash file storage directory
     * @return {@code true}: Initialized successfully<br>{@code false}: initialization failed
     */
    public static boolean init(Application application, final String crashDir) {
        if (mApplication == null)
            mApplication = new WeakReference<>(application);
        if (isSpace(crashDir)) {
            dir = null;
        } else {
            dir = crashDir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
        }
        if (mInitialized)
            return true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && mApplication.get().getExternalCacheDir() != null) {
            defaultDir = mApplication.get().getExternalCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        } else {
            defaultDir = mApplication.get().getCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        }
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
        return mInitialized = true;
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists())
            return file.isFile();
        if (!createOrExistsDir(file.getParentFile()))
            return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e("createOrExistsFile", e.getMessage());
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

