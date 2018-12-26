package com.mks.uplib.Libs.SendStatLib;

import android.content.Context;

import com.mks.sendstatlib.StatParams.ExternalStatParams;
import com.mks.uplib.Libs.BaseLib;
import com.mks.uplib.Libs.ILib;
import com.mks.uplib.Service.CodeUpdater.ExternalLibServicer;
import com.mks.uplib.Shell;

public class SendStatLib extends BaseLib implements ISendStatLib, ILib {

    public static final String LIB_NAME = "SendStatLib";  //Имя библиотеки
    public static String EXTERNAL_PACKAGE_NAME  = "com.mks.sendstatlib_external"; //Имя пакета для вызова из внешней библиотеки

    public SendStatLib(Context c){
        this.cnt = c;
        libServicer = new ExternalLibServicer(c, LIB_NAME);
    }

    @Override
    public String getVersion(Context c) {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new SendStatLib_External(cnt, libServicer, this).getVersion(cnt) : new SendStatLib_Default().getVersion(cnt);
        return res;
    }

    @Override
    public void init(Context cnt, ExternalStatParams extParam) {

        if (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) {
            new SendStatLib_External(cnt, libServicer, this).init(cnt, extParam);
        }else {
            new SendStatLib_Default().init(cnt, extParam);
        }
    }

    @Override
    public String sendStat(Context cnt, String action, String q) {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new SendStatLib_External(cnt, libServicer, this).sendStat(cnt, action, q) : new SendStatLib_Default().sendStat(cnt, action, q);
        return res;
    }

    @Override
    public String getName() {
        return LIB_NAME;
    }

    @Override
    public void clearDexClassLoader()
    {
        libServicer.clearDexClassLoader();
        Shell.clearSendStatLib();
    }


    protected String getDefaultExtPackageName() {
        return EXTERNAL_PACKAGE_NAME;
    }
}
