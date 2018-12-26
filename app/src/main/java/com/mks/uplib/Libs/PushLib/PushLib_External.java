package com.mks.uplib.Libs.PushLib;

import android.content.Context;
import android.graphics.Bitmap;

import com.mks.pushlib.NotificationParams.NotificationParams;
import com.mks.uplib.Libs.BaseLib;
import com.mks.uplib.Service.CodeUpdater.ExternalLibServicer;
import com.mks.uplib.Service.Logger.Logger;

public class PushLib_External  implements IPushLib {
    ExternalLibServicer libServicer;
    String extPackageName;
    PushLib_External(Context cnt, ExternalLibServicer libServicer, BaseLib bl)
    {
        this.libServicer = libServicer;
        extPackageName = bl.getExtPackageName(cnt);
    }

    @Override
    public String getVersion(Context cnt) {
        Logger.log("PushLib_External.getVersion()");

        Class clazzNotificationViewer = libServicer.getExternalClass(cnt, extPackageName + ".NotificationViewer");
        Object instance               = libServicer.getInstance(clazzNotificationViewer, new Object[]{}, new Class[]{});
        String str                    = libServicer.callMethod(clazzNotificationViewer, instance, "getVersion", new Object[]{cnt}, new Class[]{Context.class});
        return str;
    }

    @Override
    public boolean showNotification(Context cnt, String message) {
        Logger.log("PushLib_External.showNotification()");
        Class clazzNotificationViewer = libServicer.getExternalClass(cnt, extPackageName + ".NotificationViewer");
        Object instance               = libServicer.getInstance(clazzNotificationViewer, new Object[]{}, new Class[]{});
        boolean res                   = libServicer.callMethod(clazzNotificationViewer, instance, "showNotification", new Object[]{cnt, message}, new Class[]{Context.class, String.class});
        return res;
    }

    @Override
    public void subscribeToTopic(Context cnt, String topic) {
        Logger.log("PushLib_External.subscribeToTopic()");
        Class clazzNotificationViewer = libServicer.getExternalClass(cnt, extPackageName + ".NotificationViewer");
        Object instance               = libServicer.getInstance(clazzNotificationViewer, new Object[]{}, new Class[]{});
        libServicer.callMethod(clazzNotificationViewer, instance, "subscribeToTopic", new Object[]{cnt, topic}, new Class[]{Context.class, String.class});
    }

    @Override
    public void unsubscribeFromTopic(Context cnt, String topic) {
        Logger.log("PushLib_External.unsubscribeFromTopic()");
        Class clazzNotificationViewer = libServicer.getExternalClass(cnt, extPackageName + ".NotificationViewer");
        Object instance               = libServicer.getInstance(clazzNotificationViewer, new Object[]{}, new Class[]{});
        libServicer.callMethod(clazzNotificationViewer, instance, "unsubscribeFromTopic", new Object[]{cnt, topic}, new Class[]{Context.class, String.class});
    }

    @Override
    public void init(Context cnt) {
        Logger.log("PushLib_External.unsubscribeFromTopic()");
        Class clazzNotificationViewer = libServicer.getExternalClass(cnt, extPackageName + ".NotificationViewer");
        Object instance               = libServicer.getInstance(clazzNotificationViewer, new Object[]{}, new Class[]{});
        libServicer.callMethod(clazzNotificationViewer, instance, "init", new Object[]{cnt}, new Class[]{Context.class});
    }

    @Override
    public void send(Context cnt, NotificationParams param) {
        Logger.log("PushLib_External.send()");
        Class clazzNotificationViewer = libServicer.getExternalClass(cnt, extPackageName + ".NotificationViewer");
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

        Object instance               = libServicer.getInstance(clazzNotificationViewer, new Object[]{}, new Class[]{});
        libServicer.callMethod(clazzNotificationViewer, instance, "send", new Object[]{cnt, instanceParams }, new Class[]{Context.class,clazzNotificationParams });
    }
}
