package com.mks.uplib.Libs.SendStatLib;

import android.content.Context;

import com.mks.sendstatlib.StatParams.ExternalStatParams;
import com.mks.sendstatlib.StatSender;
import com.mks.uplib.Service.Logger.Logger;

public class SendStatLib_Default implements ISendStatLib {
    @Override
    public String getVersion(Context c) {
        Logger.log("SendStatLib_Default.getVersion()");
        return new StatSender().getVersion(c);
    }

    @Override
    public void init(Context cnt, ExternalStatParams extParam) {
        Logger.log("SendStatLib_Default.init()");
        new StatSender().init(cnt, extParam);
    }

    @Override
    public String sendStat(Context cnt, String action, String q) {
        Logger.log("SendStatLib_Default.sendStat()");
        return new StatSender().sendStat(cnt, action, q);
    }
}
