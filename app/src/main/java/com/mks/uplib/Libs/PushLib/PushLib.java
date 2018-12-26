package com.mks.uplib.Libs.PushLib;

import android.content.Context;

import com.mks.pushlib.NotificationParams.NotificationParams;
import com.mks.uplib.Libs.BaseLib;
import com.mks.uplib.Libs.ILib;
import com.mks.uplib.Service.CodeUpdater.ExternalLibServicer;
import com.mks.uplib.Shell;

public class PushLib extends BaseLib implements IPushLib, ILib {

    public static final String LIB_NAME = "PushLib";  //Имя библиотеки
    public static String EXTERNAL_PACKAGE_NAME  = "com.mks.pushlib_external"; //Имя пакета для вызова из внешней библиотеки

    public PushLib(Context c){
        this.cnt = c;
        libServicer = new ExternalLibServicer(c, LIB_NAME);
    }

    @Override
    public String getVersion(Context c) {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new PushLib_External(c, libServicer, this).getVersion(cnt) : new PushLib_Default().getVersion(cnt);
        return res;
    }

    @Override
    public boolean showNotification(Context c, String message) {
        boolean res = false;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new PushLib_External(c, libServicer, this).showNotification(cnt, message) : new PushLib_Default().showNotification(cnt, message);
        return res;
    }

    @Override
    public void subscribeToTopic(Context c, String topic) {
        if (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) {
            new PushLib_External(c, libServicer, this).subscribeToTopic(cnt, topic);
        }else {
            new PushLib_Default().subscribeToTopic(cnt, topic);
        }
    }

    @Override
    public void unsubscribeFromTopic(Context c, String topic) {
        if (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) {
            new PushLib_External(c, libServicer, this).unsubscribeFromTopic(cnt, topic);
        }else {
            new PushLib_Default().unsubscribeFromTopic(cnt, topic);
        }
    }

    @Override
    public void init(Context c) {
        if (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) {
            new PushLib_External(c, libServicer, this).init(cnt);
        }else {
            new PushLib_Default().init(cnt);
        }
    }

    @Override
    public void send(Context c, NotificationParams param) {
        if (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) {
            new PushLib_External(c, libServicer, this).send(cnt, param);
        }else {
            new PushLib_Default().send(cnt, param);
        }
    }

    @Override
    public String getName() {
        return LIB_NAME;
    }

    @Override
    public void clearDexClassLoader()
    {
        libServicer.clearDexClassLoader();
        Shell.clearPushLib();
    }

    protected String getDefaultExtPackageName() {
        return EXTERNAL_PACKAGE_NAME;
    }
}
