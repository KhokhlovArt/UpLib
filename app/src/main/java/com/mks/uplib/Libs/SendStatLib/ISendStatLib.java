package com.mks.uplib.Libs.SendStatLib;

import android.content.Context;

import com.mks.sendstatlib.StatParams.ExternalStatParams;

public interface ISendStatLib {
    String getVersion(Context c);
    void init(Context cnt, ExternalStatParams extParam);
    String sendStat(Context cnt, String action, String q);
}
