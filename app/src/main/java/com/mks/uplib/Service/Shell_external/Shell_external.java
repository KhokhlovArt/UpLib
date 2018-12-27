package com.mks.uplib.Service.Shell_external;

import android.content.Context;
import android.graphics.Bitmap;

import com.advertising_id_service.appclick.googleadvertisingidservice.GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter;
import com.advertising_id_service.appclick.googleadvertisingidservice.PublisherID.PublisherIDMask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.mks.pushlib.NotificationParams.NotificationParams;
import com.mks.sendstatlib.StatParams.ExternalStatParams;
import com.mks.uplib.Libs.ILib;
import com.mks.uplib.Service.CodeUpdater.ExternalLibServicer;

import java.io.IOException;
import java.util.List;

public class Shell_external implements ILib {
    private static ExternalLibServicer libServicer;
    String extPackageName = "com.mks.uplib_external";
    static public String LIB_NAME = "UpLib";

    public Shell_external(Context cnt){
        if (libServicer == null) {
            libServicer = new ExternalLibServicer(cnt, LIB_NAME);
        }
    }

    public static void clear()
    {
        libServicer = null;
    }

    public boolean isExternalLibAccessible(Context cnt)
    {
        return libServicer.isExternalLibAccessible(cnt, LIB_NAME);
    }

    public String getVersion(Context cnt)
    {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        //libServicer.printClassMethods(clazzShell);
        Object instance = libServicer.getInstance(clazzShell, new Object[]{}, new Class[]{});
        String s = libServicer.callMethod(clazzShell, instance, "getVersion", new Object[]{cnt}, new Class[]{Context.class});
        return "" + s;
    }

    @Override
    public String getName() {
        return LIB_NAME;
    }

    @Override
    public void clearDexClassLoader() {
        libServicer = null;
    }

    public void PushLib_send(Context cnt, NotificationParams param) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.PushLib.PushLib");

        Class clazzNotificationParams = libServicer.getExternalClass(cnt, extPackageName + ".NotificationParams.NotificationParams");
        Object instanceParams         = libServicer.getInstance(clazzNotificationParams, new Object[]{}, new Class[]{});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setTag", new Object[]{cnt, param.getTag()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setNameExtra1", new Object[]{cnt, param.getNameExtra1()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setNameExtra2", new Object[]{cnt, param.getNameExtra2()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setNameExtra3", new Object[]{cnt, param.getNameExtra3()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setIsOpenInBrowser", new Object[]{cnt, param.getIsOpenInBrowser()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setLink", new Object[]{cnt, param.getLink()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setTargetAppVersion", new Object[]{cnt, param.getTargetAppVersion()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setTitel", new Object[]{cnt, param.getTitel()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setMsgText", new Object[]{cnt, param.getMsgText()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setPriority", new Object[]{cnt, param.getPriority()}, new Class[]{Context.class, int.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setDefaults", new Object[]{cnt, param.getDefaults()}, new Class[]{Context.class, int.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setCategory", new Object[]{cnt, param.getСategory()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setLargeIcon", new Object[]{cnt, param.getLargeIcon()}, new Class[]{Context.class, Bitmap.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setAction", new Object[]{cnt, param.getAction()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setAppForStart", new Object[]{cnt, param.getAppForStart()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setAutoCancel", new Object[]{cnt, param.getAutoCancel()}, new Class[]{Context.class, boolean.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setStyle", new Object[]{cnt, param.getStyle()}, new Class[]{Context.class, String.class});
        libServicer.callMethod(clazzNotificationParams, instanceParams, "setVibrate", new Object[]{cnt, param.getVibrate()}, new Class[]{Context.class, long[].class});

        Object instance = libServicer.callStaticMethod(clazzShell, "PushLib", new Object[]{cnt}, new Class[]{Context.class});
        libServicer.callMethod(clazzLib, instance, "send", new Object[]{cnt, instanceParams}, new Class[]{Context.class, clazzNotificationParams});
    }

    public boolean PushLib_showNotification(Context cnt, String message) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.PushLib.PushLib");
        Object instance = libServicer.callStaticMethod(clazzShell, "PushLib", new Object[]{cnt}, new Class[]{Context.class});
        boolean res = libServicer.callMethod(clazzLib, instance, "showNotification", new Object[]{cnt, message}, new Class[]{Context.class, String.class});
        return res;
    }

    public void PushLib_subscribeToTopic(Context cnt, String topic) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.PushLib.PushLib");
        Object instance = libServicer.callStaticMethod(clazzShell, "PushLib", new Object[]{cnt}, new Class[]{Context.class});
        libServicer.callMethod(clazzLib, instance, "subscribeToTopic", new Object[]{cnt, topic}, new Class[]{Context.class, String.class});
    }

    public void PushLib_unsubscribeFromTopic(Context cnt, String topic) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.PushLib.PushLib");
        Object instance = libServicer.callStaticMethod(clazzShell, "PushLib", new Object[]{cnt}, new Class[]{Context.class});
        libServicer.callMethod(clazzLib, instance, "unsubscribeFromTopic", new Object[]{cnt, topic}, new Class[]{Context.class, String.class});
    }

    public void PushLib_init(Context cnt) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.PushLib.PushLib");
        Object instance = libServicer.callStaticMethod(clazzShell, "PushLib", new Object[]{cnt}, new Class[]{Context.class});
        libServicer.callMethod(clazzLib, instance, "init", new Object[]{cnt}, new Class[]{Context.class});
    }

    public void SendStatLib_init(Context cnt, ExternalStatParams extParam) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.SendStatLib.SendStatLib");

        Class clazzExternalStatParams = libServicer.getExternalClass(cnt, extPackageName + ".StatParams.ExternalStatParams");
        Object instanceParam          = libServicer.getInstance(clazzExternalStatParams, new Object[]{}, new Class[]{});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setAppFCMToken" , new Object[]{extParam.getAppFCMToken()} , new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setAppid"       , new Object[]{extParam.getAppid()}       , new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setExternalGaid", new Object[]{extParam.getExternalGaid()}, new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setPackageNames", new Object[]{extParam.getPackageNames()}, new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setPublisherId" , new Object[]{extParam.getPublisherId()} , new Class[]{String.class});
        libServicer.callMethod(clazzExternalStatParams, instanceParam, "setSdkversion"  , new Object[]{extParam.getSdkversion()}  , new Class[]{String.class});

        Object instance = libServicer.callStaticMethod(clazzShell, "SendStatLib", new Object[]{cnt}, new Class[]{Context.class});
        libServicer.callMethod(clazzLib, instance, "init", new Object[]{cnt, instanceParam}, new Class[]{Context.class, clazzExternalStatParams});
    }

    public String SendStatLib_sendStat(Context cnt, String action, String q) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.SendStatLib.SendStatLib");
        Object instance = libServicer.callStaticMethod(clazzShell, "SendStatLib", new Object[]{cnt}, new Class[]{Context.class});
        String res = libServicer.callMethod(clazzLib, instance, "sendStat", new Object[]{cnt, action, q}, new Class[]{Context.class, String.class, String.class});
        return res;
    }

    public String FakeGAID_getOriginalID(Context cnt) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.FakeGAID.FakeGAID");
        Object instance = libServicer.callStaticMethod(clazzShell, "FakeGAID", new Object[]{cnt}, new Class[]{Context.class});
        String res = libServicer.callMethod(clazzLib, instance, "getOriginalID", new Object[]{cnt}, new Class[]{Context.class});
        return res;
    }

    public String FakeGAID_generateGUID(IGoogleAdvertisingIdGetter.GenerateIDType control_parameter, Context cnt) {
        Class clazzShell           = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib             = libServicer.getExternalClass(cnt,extPackageName + ".Libs.FakeGAID.FakeGAID");

        Object instance = libServicer.callStaticMethod(clazzShell, "FakeGAID", new Object[]{cnt}, new Class[]{Context.class});
        String res = libServicer.callMethod(clazzLib, instance, "generateGUID",
                new Object[]
                        {
                            control_parameter,
                            cnt
                        },
                new Class[]
                        {
                            IGoogleAdvertisingIdGetter.GenerateIDType.class,
                            Context.class
                        });
        return res;
    }

    public String FakeGAID_getFakeGaid(Context cnt) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib   = libServicer.getExternalClass(cnt,extPackageName + ".Libs.FakeGAID.FakeGAID");
        Object instance = libServicer.callStaticMethod(clazzShell, "FakeGAID", new Object[]{cnt}, new Class[]{Context.class});
        String res = libServicer.callMethod(clazzLib, instance, "getFakeGaid", new Object[]{cnt}, new Class[]{Context.class});
        return res;
    }

    public List<String> FakeGAID_getFilePublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, PublisherIDMask mask) {
        Class clazzShell           = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib             = libServicer.getExternalClass(cnt,extPackageName + ".Libs.FakeGAID.FakeGAID");
        //Class clazzPublusherIDType = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter$PublusherIDType");
        //Class clazzMask            = libServicer.getExternalClass(cnt, extPackageName + ".PublisherID.PublisherIDMask");

//        Object instanceMask        = libServicer.getInstance(clazzMask,
//                new Object[]{
//                        mask.getPrefix(),
//                        mask.getSeporator(),
//                        mask.getExtension()
//                },
//                new Class[]{
//                        String.class,
//                        String.class,
//                        String.class
//                });

        Object instance = libServicer.callStaticMethod(clazzShell, "FakeGAID", new Object[]{cnt}, new Class[]{Context.class});
        List<String>  res = libServicer.callMethod(clazzLib, instance, "getInnerPublisherIDs",
                new Object[]
                        {
                            //libServicer.getEnumValue(clazzPublusherIDType, control_parameter.ordinal())
                            control_parameter,
                            cnt,
                            mask
                            //instanceMask
                        },
                new Class[]
                        {
                            //clazzPublusherIDType,
                            IGoogleAdvertisingIdGetter.PublusherIDType.class,
                            Context.class,
                            PublisherIDMask.class
                            //clazzMask
                        });

        return res;
    }

    public String FakeGAID_getInnerPublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, String key) {
        Class clazzShell           = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib             = libServicer.getExternalClass(cnt,extPackageName + ".Libs.FakeGAID.FakeGAID");
        //Class clazzPublusherIDType = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter$PublusherIDType");

        Object instance = libServicer.callStaticMethod(clazzShell, "FakeGAID", new Object[]{cnt}, new Class[]{Context.class});
        String res = libServicer.callMethod(clazzLib, instance, "getInnerPublisherIDs",
                new Object[]
                    {
                        //libServicer.getEnumValue(clazzPublusherIDType, control_parameter.ordinal()),
                        control_parameter,
                        cnt,
                        key
                    },
                new Class[]
                    {
                        //clazzPublusherIDType,
                        IGoogleAdvertisingIdGetter.PublusherIDType.class,
                        Context.class,
                        String.class
                    });

        return res;
    }

    public void FakeGAID_setGAID(Context cnt, String id) {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib = libServicer.getExternalClass(cnt,extPackageName + ".Libs.FakeGAID.FakeGAID");
        Object instance = libServicer.callStaticMethod(clazzShell, "FakeGAID", new Object[]{cnt}, new Class[]{Context.class});
        libServicer.callMethod(clazzLib, instance, "setGAID", new Object[]{cnt, id}, new Class[]{Context.class, String.class});
    }

    public String FakeGAID_getGAID(Context cnt, String callDestination) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        Class clazzShell = libServicer.getExternalClass(cnt, extPackageName + ".Shell");
        Class clazzLib = libServicer.getExternalClass(cnt,extPackageName + ".Libs.FakeGAID.FakeGAID");
        Object instance = libServicer.callStaticMethod(clazzShell, "FakeGAID", new Object[]{cnt}, new Class[]{Context.class});
        String res = libServicer.callMethod(clazzLib, instance, "getGAID", new Object[]{cnt, callDestination}, new Class[]{Context.class, String.class});
        return res;
    }

}
