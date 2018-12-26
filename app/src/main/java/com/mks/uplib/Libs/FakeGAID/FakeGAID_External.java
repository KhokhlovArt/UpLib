package com.mks.uplib.Libs.FakeGAID;

import android.content.Context;

import com.advertising_id_service.appclick.googleadvertisingidservice.GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter;
import com.advertising_id_service.appclick.googleadvertisingidservice.PublisherID.PublisherIDMask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.mks.uplib.Libs.BaseLib;
import com.mks.uplib.Service.CodeUpdater.ExternalLibServicer;
import com.mks.uplib.Service.Logger.Logger;

import java.io.IOException;
import java.util.List;

public class FakeGAID_External implements IFakeGAID {
    ExternalLibServicer libServicer;

    String extPackageName;
    FakeGAID_External(Context cnt, ExternalLibServicer libServicer, BaseLib bl)
    {
        this.libServicer = libServicer;
        extPackageName = bl.getExtPackageName(cnt);
    }

    @Override
    public String getVersion(Context cnt) {
        Logger.log("FakeGAID_External.getVersion()");
        Class clazzGoogleAdvertisingIdGetter = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetter");
        Object instance                      = libServicer.getInstance(clazzGoogleAdvertisingIdGetter, new Object[]{}, new Class[]{});
        String str                           = libServicer.callMethod(clazzGoogleAdvertisingIdGetter, instance, "getVersion", new Object[]{cnt}, new Class[]{Context.class});
        return str;
    }

    @Override
    public String getOriginalID(Context cnt) {
        Logger.log("Текущая версия GoogleAdvertisingIdGetter_FromExternalLib.getOriginalID()");
        Class clazzGoogleAdvertisingIdGetter = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetter");
        Object instance                      = libServicer.getInstance(clazzGoogleAdvertisingIdGetter, new Object[]{}, new Class[]{});
        String str                           = libServicer.callMethod(clazzGoogleAdvertisingIdGetter, instance, "getOriginalID", new Object[]{cnt}, new Class[]{Context.class});
        return str;
    }

    @Override
    public String generateGUID(IGoogleAdvertisingIdGetter.GenerateIDType control_parameter, Context cnt) {
        Logger.log("Текущая версия GoogleAdvertisingIdGetter_FromExternalLib.generateGUID()");
        Class clazzGoogleAdvertisingIdGetter = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetter");
        Class clazzEnumGenerateIDType        = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter$GenerateIDType");
        Object instance                      = libServicer.getInstance(clazzGoogleAdvertisingIdGetter, new Object[]{}, new Class[]{});
        String str                           = libServicer.callMethod(clazzGoogleAdvertisingIdGetter, instance, "generateGUID",
                new Object[]{
                        libServicer.getEnumValue(clazzEnumGenerateIDType, control_parameter.ordinal()),
                        cnt
                },
                new Class[]{
                        clazzEnumGenerateIDType,
                        Context.class
                });
        return str;
    }

    @Override
    public String getFakeGaid(Context cnt) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        Logger.log("Текущая версия GoogleAdvertisingIdGetter_FromExternalLib.getFakeGaid()");
        Class clazzGoogleAdvertisingIdGetter = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetter");
        Object instance                      = libServicer.getInstance(clazzGoogleAdvertisingIdGetter, new Object[]{}, new Class[]{});
        String str                           = libServicer.callMethod(clazzGoogleAdvertisingIdGetter, instance, "getFakeGaid", new Object[]{cnt}, new Class[]{Context.class});
        return str;
    }

    @Override
    public List<String> getFilePublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, PublisherIDMask mask) {
        Logger.log("Текущая версия GoogleAdvertisingIdGetter_FromExternalLib.getFilePublisherIDs()");
        Class clazzGoogleAdvertisingIdGetter = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetter");
        Class clazzMask                      = libServicer.getExternalClass(cnt, extPackageName + ".PublisherID.PublisherIDMask");
        Class clazzEnumPublusherIDType       = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter$PublusherIDType");

        Object instance                      = libServicer.getInstance(clazzGoogleAdvertisingIdGetter, new Object[]{}, new Class[]{});
        Object instanceMask                  = libServicer.getInstance(clazzMask,
                new Object[]{
                        mask.getPrefix(),
                        mask.getSeporator(),
                        mask.getExtension()
                },
                new Class[]{
                        String.class,
                        String.class,
                        String.class
                });


        List<String> res                     = libServicer.callMethod(clazzGoogleAdvertisingIdGetter, instance, "getFilePublisherIDs",
                new Object[]{
                        libServicer.getEnumValue(clazzEnumPublusherIDType, control_parameter.ordinal()),
                        cnt,
                        instanceMask
                },
                new Class[]{
                        clazzEnumPublusherIDType,
                        Context.class,
                        clazzMask
                });
        return res;
    }

    @Override
    public String getInnerPublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, String key) {
        Logger.log("Текущая версия GoogleAdvertisingIdGetter_FromExternalLib.getInnerPublisherIDs()");
        Class clazzGoogleAdvertisingIdGetter = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetter");
        Class clazzEnumPublusherIDType       = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter$PublusherIDType");
        Object instance                      = libServicer.getInstance(clazzGoogleAdvertisingIdGetter, new Object[]{}, new Class[]{});
        String str                           = libServicer.callMethod(clazzGoogleAdvertisingIdGetter, instance, "getInnerPublisherIDs",
                new Object[]{
                        libServicer.getEnumValue(clazzEnumPublusherIDType, control_parameter.ordinal()),
                        cnt,
                        key
                },
                new Class[]{
                        clazzEnumPublusherIDType,
                        Context.class,
                        String.class
                });
        return str;
    }

    @Override
    public void setGAID(Context cnt, String id) {
        Logger.log("Текущая версия GoogleAdvertisingIdGetter_FromExternalLib.setGAID()");
        Class clazzGoogleAdvertisingIdGetter = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetter");
        Object instance                      = libServicer.getInstance(clazzGoogleAdvertisingIdGetter, new Object[]{}, new Class[]{});
        libServicer.callMethod(clazzGoogleAdvertisingIdGetter, instance, "setGAID", new Object[]{cnt, id}, new Class[]{Context.class, String.class});
    }

    @Override
    public String getGAID(Context cnt, String callDestination) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        Logger.log("Текущая версия GoogleAdvertisingIdGetter_FromExternalLib.getGAID()");
        Class clazzGoogleAdvertisingIdGetter = libServicer.getExternalClass(cnt, extPackageName + ".GoogleAdvertisingIdGetter");
        Object instance                      = libServicer.getInstance(clazzGoogleAdvertisingIdGetter, new Object[]{}, new Class[]{});
        String str                           = libServicer.callMethod(clazzGoogleAdvertisingIdGetter, instance, "getGAID", new Object[]{cnt, callDestination}, new Class[]{Context.class, String.class});
        return str;
    }
}
