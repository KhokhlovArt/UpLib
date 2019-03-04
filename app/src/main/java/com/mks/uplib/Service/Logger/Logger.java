package com.mks.uplib.Service.Logger;

import android.content.Context;
import android.util.Log;

import com.mks.sendstatlib.StatSender;
import com.mks.uplib.C;

public class Logger {
    public static void log(String s)
    {
        if (C.NEED_LOG) {
            Log.e("UpLib", "" + C.CODE_VERSION + ": " + s);
        }
    }

    public static void logIntoKibana(Context cnt, String action, String q)
    {
        if (C.IS_NEED_KIBANA_LOG) {
            new StatSender().sendStat(cnt, action, q);
        }
    }
}
