package com.mks.uplib;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;

import com.advertising_id_service.appclick.googleadvertisingidservice.GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter;
import com.advertising_id_service.appclick.googleadvertisingidservice.PublisherID.PublisherIDMask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.mks.pushlib.NotificationParams.NotificationParams;
import com.mks.sendstatlib.StatParams.ExternalStatParams;
import com.mks.uplib.Libs.FakeGAID.FakeGAID;
import com.mks.uplib.Libs.PushLib.PushLib;
import com.mks.uplib.Libs.SendStatLib.SendStatLib;
import com.mks.uplib.Service.CodeUpdater.CodeUpdater;
import com.mks.uplib.Service.Logger.Logger;
import com.mks.uplib.Service.Services.InitializeService;
import com.mks.uplib.Service.Shell_external.Shell_default;
import com.mks.uplib.Service.Shell_external.Shell_external;
import com.mks.uplib.Service.Shell_external.UpLib;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Shell implements IShell{
    private static FakeGAID fakeGAID;
    private static PushLib pushLib;
    private static SendStatLib sendStatLib;
    private static com.mks.uplib.Service.Shell_external.UpLib upLib;

    public Shell()
    {
       // new FilesLoader().downloadJson("https://drive.google.com/a/adviator.com/uc?authuser=0&id=1cP7AGOYNJNkjo0hrJxSCgyGi5TpSna-v&export=download");
    }

    public static synchronized UpLib UpLib(Context cnt){
        if (upLib == null){
            upLib = new UpLib();
        }
        return upLib;
    }
    public static synchronized void clearUpLib(){
        upLib = null;
    }

    public static synchronized FakeGAID FakeGAID(Context cnt){
        if (fakeGAID == null){
            fakeGAID = new FakeGAID(cnt);
        }
        return fakeGAID;
    }
    public static synchronized void clearFakeGAID(){
        fakeGAID = null;
    }

    public static synchronized PushLib PushLib(Context cnt){
        if (pushLib == null){
            pushLib = new PushLib(cnt);
        }
        return pushLib;
    }
    public static synchronized void clearPushLib(){
        pushLib = null;
    }

    public static synchronized SendStatLib SendStatLib(Context cnt){
        if (sendStatLib == null){
            sendStatLib = new SendStatLib(cnt);
        }
        return sendStatLib;
    }
    public static synchronized void clearSendStatLib(){
        sendStatLib = null;
    }

    @Override
    public void updateLibs(final Context cnt, LoaderManager lm) {
        new CodeUpdater().updateCode(cnt, lm);
    }

    @Override
    public void initialize(Context cnt) {
        cnt.startService( new Intent(cnt, InitializeService.class));
    }

    @Override
    public String getVersion(Context cnt) {
        if(isExternalLibAccessible(cnt)) {
            return new Shell_external(cnt).getVersion(cnt);
        }else{
            return C.CODE_VERSION;
        }
    }


    /*****************************************************************************************************
     *****************************************************************************************************
     ******* Методы для запуска от пользователей. Нужны что бы их можно было запускать из DEX-a **********
     *****************************************************************************************************
     *****************************************************************************************************/
    private static boolean isExternalLibAccessible(Context cnt)
    {
        // для сборки DEX-а поменять!
        //return false;
        return new Shell_external(cnt).isExternalLibAccessible(cnt);
    }

    public static void PushLib_send(Context cnt, NotificationParams param) {
        if(isExternalLibAccessible(cnt)) {
            new Shell_external(cnt).PushLib_send(cnt, param);
        }else{
            Shell_default.PushLib_send(cnt, param);
        }
    }

    public static boolean PushLib_showNotification(Context cnt, String message) {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).PushLib_showNotification(cnt, message) : Shell_default.PushLib_showNotification(cnt, message);
    }

    public static void PushLib_subscribeToTopic(Context cnt, String topic) {
        if(isExternalLibAccessible(cnt)) {
            new Shell_external(cnt).PushLib_subscribeToTopic(cnt, topic);
        }else{
            Shell_default.PushLib_subscribeToTopic(cnt, topic);
        }
    }

    public static void PushLib_unsubscribeFromTopic(Context cnt, String topic) {
        if(isExternalLibAccessible(cnt)) {
            new Shell_external(cnt).PushLib_unsubscribeFromTopic(cnt, topic);
        }else{
            Shell_default.PushLib_unsubscribeFromTopic(cnt, topic);
        }
    }

    public static void PushLib_init(Context cnt) {
        if(isExternalLibAccessible(cnt)) {
            new Shell_external(cnt).PushLib_init(cnt);
        }else{
            Shell_default.PushLib_init(cnt);
        }
    }

    public static void SendStatLib_init(Context cnt, ExternalStatParams extParam) {
        if(isExternalLibAccessible(cnt)) {
            new Shell_external(cnt).SendStatLib_init(cnt, extParam);
        }else{
            Shell_default.SendStatLib_init(cnt, extParam);
        }
    }

    public static String SendStatLib_sendStat(Context cnt, String action, String q) {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).SendStatLib_sendStat(cnt, action, q) : Shell_default.SendStatLib_sendStat(cnt, action, q);
    }

    public static String FakeGAID_getOriginalID(Context cnt) {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).FakeGAID_getOriginalID(cnt) : Shell_default.FakeGAID_getOriginalID(cnt);
    }

    public static String FakeGAID_generateGUID(IGoogleAdvertisingIdGetter.GenerateIDType control_parameter, Context cnt) {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).FakeGAID_generateGUID(control_parameter, cnt) : Shell_default.FakeGAID_generateGUID(control_parameter, cnt);
    }

    public static String FakeGAID_getFakeGaid(Context cnt) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).FakeGAID_getFakeGaid(cnt) : Shell_default.FakeGAID_getFakeGaid(cnt);
    }

    public static List<String> FakeGAID_getFilePublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, PublisherIDMask mask) {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).FakeGAID_getFilePublisherIDs(control_parameter, cnt, mask) : Shell_default.FakeGAID_getFilePublisherIDs(control_parameter, cnt, mask);
    }
    public static List<String> FakeGAID_getFilePublisherIDs(Context cnt, PublisherIDMask mask) {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).FakeGAID_getFilePublisherIDs(cnt, mask) : Shell_default.FakeGAID_getFilePublisherIDs(cnt, mask);
    }

    public static String FakeGAID_getInnerPublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, String key) {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).FakeGAID_getInnerPublisherIDs(control_parameter, cnt, key) :Shell_default. FakeGAID_getInnerPublisherIDs(control_parameter, cnt, key);
    }

    public static void FakeGAID_setGAID(Context cnt, String id) {
        if(isExternalLibAccessible(cnt)) {
            new Shell_external(cnt).FakeGAID_setGAID(cnt, id);
        }else{
            Shell_default.FakeGAID_setGAID(cnt, id);
        }
    }

    public static String FakeGAID_getGAID(Context cnt, String callDestination) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        return (isExternalLibAccessible(cnt)) ? new Shell_external(cnt).FakeGAID_getGAID(cnt, callDestination) : Shell_default.FakeGAID_getGAID(cnt, callDestination);
    }


}
