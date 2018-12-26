package com.mks.uplib.Libs.SendStatLib;

import android.content.Context;

import com.mks.sendstatlib.StatParams.ExternalStatParams;
import com.mks.uplib.Libs.BaseLib;
import com.mks.uplib.Service.CodeUpdater.ExternalLibServicer;
import com.mks.uplib.Service.Logger.Logger;

public class SendStatLib_External  implements ISendStatLib {
    ExternalLibServicer libServicer;
    String extPackageName;
    SendStatLib_External(Context cnt, ExternalLibServicer libServicer, BaseLib bl)
    {
        this.libServicer = libServicer;
        extPackageName = bl.getExtPackageName(cnt);
    }

    @Override
    public String getVersion(Context cnt) {
        Logger.log("SendStatLib_External.getVersion()");
        Class clazzStatSender = libServicer.getExternalClass(cnt, extPackageName + ".StatSender");
        Object instance       = libServicer.getInstance(clazzStatSender, new Object[]{}, new Class[]{});
        String str            = libServicer.callMethod(clazzStatSender, instance, "getVersion", new Object[]{cnt}, new Class[]{Context.class});
        return str;
    }

    @Override
    public void init(Context cnt, ExternalStatParams extParam) {
        Logger.log("SendStatLib_External.init()");
        Class clazzStatSender = libServicer.getExternalClass(cnt, extPackageName + ".StatSender");

        Class clazzExternalStatParams = libServicer.getExternalClass(cnt, extPackageName + ".StatParams.ExternalStatParams");
        Object instanceParam          = libServicer.getInstance(clazzExternalStatParams, new Object[]{}, new Class[]{});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setAppFCMToken" , new Object[]{extParam.getAppFCMToken()} , new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setAppid"       , new Object[]{extParam.getAppid()}       , new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setExternalGaid", new Object[]{extParam.getExternalGaid()}, new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setPackageNames", new Object[]{extParam.getPackageNames()}, new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setPublisherId" , new Object[]{extParam.getPublisherId()} , new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setSdkversion"  , new Object[]{extParam.getSdkversion()}  , new Class[]{String.class});

        Object instance = libServicer.getInstance(clazzStatSender, new Object[]{}, new Class[]{});
        String str      = libServicer.callMethod(clazzStatSender, instance, "init", new Object[]{cnt, instanceParam}, new Class[]{Context.class, clazzExternalStatParams});

    }

    @Override
    public String sendStat(Context cnt, String action, String q) {
        Logger.log("SendStatLib_External.sendStat()");
        Class clazzStatSender = libServicer.getExternalClass(cnt, extPackageName + ".StatSender");
        Object instance       = libServicer.getInstance(clazzStatSender, new Object[]{}, new Class[]{});
        String str            = libServicer.callMethod(clazzStatSender, instance, "sendStat", new Object[]{cnt, action, q}, new Class[]{Context.class, String.class, String.class});
        return str;
    }
}
