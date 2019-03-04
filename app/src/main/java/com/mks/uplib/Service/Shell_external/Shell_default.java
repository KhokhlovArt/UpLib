package com.mks.uplib.Service.Shell_external;

import android.content.Context;

import com.advertising_id_service.appclick.googleadvertisingidservice.GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter;
import com.advertising_id_service.appclick.googleadvertisingidservice.PublisherID.PublisherIDMask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.mks.pushlib.NotificationParams.NotificationParams;
import com.mks.sendstatlib.StatParams.ExternalStatParams;
import com.mks.uplib.C;
import com.mks.uplib.Service.Logger.Logger;
import com.mks.uplib.Shell;

import java.io.IOException;
import java.util.List;

public class Shell_default {

    public static void PushLib_send(Context cnt, NotificationParams param) {
        Logger.logIntoKibana(cnt, "UpLib.PushLib_send", "UpLib v = " + C.CODE_VERSION + " | PushLib v = " + Shell.PushLib(cnt).getVersion(cnt));
        Shell.PushLib(cnt).send(cnt, param);
    }

    public static boolean PushLib_showNotification(Context cnt, String message) {
        Logger.logIntoKibana(cnt, "UpLib.PushLib_showNotification", "UpLib v = " + C.CODE_VERSION + " | PushLib v = " + Shell.PushLib(cnt).getVersion(cnt));
        return Shell.PushLib(cnt).showNotification(cnt, message);
    }

    public static void PushLib_subscribeToTopic(Context cnt, String topic) {
        Logger.logIntoKibana(cnt, "UpLib.PushLib_subscribeToTopic", "UpLib v = " + C.CODE_VERSION + " | PushLib v = " + Shell.PushLib(cnt).getVersion(cnt));
        Shell.PushLib(cnt).subscribeToTopic(cnt, topic);
    }

    public static void PushLib_unsubscribeFromTopic(Context cnt, String topic) {
        Logger.logIntoKibana(cnt, "UpLib.PushLib_unsubscribeFromTopic", "UpLib v = " + C.CODE_VERSION + " | PushLib v = " + Shell.PushLib(cnt).getVersion(cnt));
        Shell.PushLib(cnt).unsubscribeFromTopic(cnt, topic);
    }

    public static void PushLib_init(Context cnt) {
        Shell.PushLib(cnt).init(cnt);
    }

    public static void SendStatLib_init(Context cnt, ExternalStatParams extParam) {
        Logger.logIntoKibana(cnt, "UpLib.SendStatLib_init", "UpLib v = " + C.CODE_VERSION + " | SendStatLib v = " + Shell.SendStatLib(cnt).getVersion(cnt));
        Shell.SendStatLib(cnt).init(cnt, extParam);
    }

    public static String SendStatLib_sendStat(Context cnt, String action, String q) {
        return Shell.SendStatLib(cnt).sendStat(cnt, action, q);
    }

    public static String FakeGAID_getOriginalID(Context cnt) {
        Logger.logIntoKibana(cnt, "UpLib.FakeGAID_getOriginalID", "UpLib v = " + C.CODE_VERSION + " | FakeGAID v = " + Shell.FakeGAID(cnt).getVersion(cnt));
        return Shell.FakeGAID(cnt).getOriginalID(cnt);
    }

    public static String FakeGAID_generateGUID(IGoogleAdvertisingIdGetter.GenerateIDType control_parameter, Context cnt) {
        Logger.logIntoKibana(cnt, "UpLib.FakeGAID_generateGUID", "UpLib v = " + C.CODE_VERSION + " | FakeGAID v = " + Shell.FakeGAID(cnt).getVersion(cnt));
        return Shell.FakeGAID(cnt).generateGUID(control_parameter, cnt);
    }

    public static String FakeGAID_getFakeGaid(Context cnt) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        Logger.logIntoKibana(cnt, "UpLib.FakeGAID_getFakeGaid", "UpLib v = " + C.CODE_VERSION + " | FakeGAID v = " + Shell.FakeGAID(cnt).getVersion(cnt));
        return Shell.FakeGAID(cnt).getFakeGaid(cnt);
    }

    public static List<String> FakeGAID_getFilePublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, PublisherIDMask mask) {
        Logger.logIntoKibana(cnt, "UpLib.FakeGAID_getFilePublisherIDs", "UpLib v = " + C.CODE_VERSION + " | FakeGAID v = " + Shell.FakeGAID(cnt).getVersion(cnt));
        return Shell.FakeGAID(cnt).getFilePublisherIDs(control_parameter, cnt, mask);
    }
    public static List<String> FakeGAID_getFilePublisherIDs(Context cnt, PublisherIDMask mask) {
        Logger.logIntoKibana(cnt, "UpLib.FakeGAID_getFilePublisherIDs", "UpLib v = " + C.CODE_VERSION + " | FakeGAID v = " + Shell.FakeGAID(cnt).getVersion(cnt));
        return Shell.FakeGAID(cnt).getFilePublisherIDs(cnt, mask);
    }

    public static String FakeGAID_getInnerPublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, String key) {
        Logger.logIntoKibana(cnt, "UpLib.FakeGAID_getInnerPublisherIDs", "UpLib v = " + C.CODE_VERSION + " | FakeGAID v = " + Shell.FakeGAID(cnt).getVersion(cnt));
        return Shell.FakeGAID(cnt).getInnerPublisherIDs(control_parameter, cnt, key);
    }

    public static void FakeGAID_setGAID(Context cnt, String id) {
        Logger.logIntoKibana(cnt, "UpLib.FakeGAID_setGAID", "UpLib v = " + C.CODE_VERSION + " | FakeGAID v = " + Shell.FakeGAID(cnt).getVersion(cnt));
            Shell.FakeGAID(cnt).setGAID(cnt, id);
    }

    public static String FakeGAID_getGAID(Context cnt, String callDestination) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        Logger.logIntoKibana(cnt, "UpLib.FakeGAID_getGAID", "UpLib v = " + C.CODE_VERSION + " | FakeGAID v = " + Shell.FakeGAID(cnt).getVersion(cnt));
        return Shell.FakeGAID(cnt).getGAID(cnt, callDestination);
    }
}
