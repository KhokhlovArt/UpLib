package com.mks.uplib.Service.Logger;

import android.util.Log;

import com.mks.uplib.C;

public class Logger {
    public static void log(String s)
    {
        if (C.NEED_LOG) {
            Log.e("UpLib", "" + C.CODE_VERSION + ": " + s);
        }
    }
}
