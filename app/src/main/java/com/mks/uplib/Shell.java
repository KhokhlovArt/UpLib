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
import com.mks.uplib.Service.Services.InitializeService;

import java.io.IOException;
import java.util.List;


public class Shell implements IShell{
    private static FakeGAID fakeGAID;
    private static PushLib pushLib;
    private static SendStatLib sendStatLib;

    public Shell()
    {
       // new FilesLoader().downloadJson("https://drive.google.com/a/adviator.com/uc?authuser=0&id=1cP7AGOYNJNkjo0hrJxSCgyGi5TpSna-v&export=download");
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
        return C.CODE_VERSION;
    }


    /*****************************************************************************************************
     *****************************************************************************************************
     ******* Методы для запуска от пользователей. Нужны что бы их можно было запускать из DEX-a **********
     *****************************************************************************************************
     *****************************************************************************************************/

    public void PushLib_send(Context cnt, NotificationParams param) {
        PushLib(cnt).send(cnt, param);
    }

    public boolean PushLib_showNotification(Context cnt, String message) {
        return PushLib(cnt).showNotification(cnt, message);
    }

    public void PushLib_subscribeToTopic(Context cnt, String topic) {
        PushLib(cnt).subscribeToTopic(cnt, topic);
    }

    public void PushLib_unsubscribeFromTopic(Context cnt, String topic) {
        PushLib(cnt).unsubscribeFromTopic(cnt, topic);
    }

    public void PushLib_init(Context cnt) {
        PushLib(cnt).init(cnt);
    }

    public void SendStatLib_init(Context cnt, ExternalStatParams extParam) {
        SendStatLib(cnt).init(cnt, extParam);
    }

    public String SendStatLib_sendStat(Context cnt, String action, String q) {
        return SendStatLib(cnt).sendStat(cnt, action, q);
    }

    public String FakeGAID_getOriginalID(Context cnt) {
        return FakeGAID(cnt).getOriginalID(cnt);
    }

    public String FakeGAID_generateGUID(IGoogleAdvertisingIdGetter.GenerateIDType control_parameter, Context cnt) {
        return FakeGAID(cnt).generateGUID(control_parameter, cnt);
    }

    public String FakeGAID_getFakeGaid(Context cnt) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        return FakeGAID(cnt).getFakeGaid(cnt);
    }

    public List<String> FakeGAID_getFilePublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, PublisherIDMask mask) {
        return FakeGAID(cnt).getFilePublisherIDs(control_parameter, cnt, mask);
    }

    public String FakeGAID_getInnerPublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, String key) {
        return FakeGAID(cnt).getInnerPublisherIDs(control_parameter, cnt, key);
    }

    public void FakeGAID_setGAID(Context cnt, String id) {
        FakeGAID(cnt).setGAID(cnt, id);
    }

    public String FakeGAID_getGAID(Context cnt, String callDestination) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        return FakeGAID(cnt).getGAID(cnt, callDestination);
    }


}
