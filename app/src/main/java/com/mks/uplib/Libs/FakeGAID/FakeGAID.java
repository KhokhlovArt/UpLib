package com.mks.uplib.Libs.FakeGAID;

import android.content.Context;

import com.advertising_id_service.appclick.googleadvertisingidservice.GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter;
import com.advertising_id_service.appclick.googleadvertisingidservice.PublisherID.PublisherIDMask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.mks.uplib.Libs.BaseLib;
import com.mks.uplib.Libs.ILib;
import com.mks.uplib.Service.CodeUpdater.ExternalLibServicer;
import com.mks.uplib.Shell;

import java.io.IOException;
import java.util.List;

public class FakeGAID extends BaseLib implements IFakeGAID, ILib {

    public static final String LIB_NAME = "FakeGAID";  //Имя библиотеки
    public static String EXTERNAL_PACKAGE_NAME  = "com.adid_service.external_lib.external_code_lib"; //Имя пакета для вызова из внешней библиотеки

    public FakeGAID(Context c){
        this.cnt = c;
        libServicer = new ExternalLibServicer(c, LIB_NAME);
    }

    @Override
    public String getVersion(Context cnt) {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new FakeGAID_External(cnt, libServicer,this).getVersion(cnt) : new FakeGAID_Default().getVersion(cnt);
        return res;
    }

    @Override
    public String getOriginalID(Context cnt) {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new FakeGAID_External(cnt, libServicer,this).getOriginalID(cnt) : new FakeGAID_Default().getOriginalID(cnt);
        return res;
    }

    @Override
    public String generateGUID(IGoogleAdvertisingIdGetter.GenerateIDType control_parameter, Context cnt) {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new FakeGAID_External(cnt, libServicer,this).generateGUID(control_parameter, cnt) : new FakeGAID_Default().generateGUID(control_parameter, cnt);
        return res;
    }

    @Override
    public String getFakeGaid(Context cnt) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new FakeGAID_External(cnt, libServicer,this).getFakeGaid(cnt) : new FakeGAID_Default().getFakeGaid(cnt);
        return res;
    }

    @Override
    public List<String> getFilePublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, PublisherIDMask mask) {
        List<String> res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new FakeGAID_External(cnt, libServicer,this).getFilePublisherIDs(control_parameter, cnt, mask) : new FakeGAID_Default().getFilePublisherIDs(control_parameter, cnt, mask);
        return res;
    }

    @Override
    public List<String> getFilePublisherIDs( Context cnt, PublisherIDMask mask) {
        List<String> res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new FakeGAID_External(cnt, libServicer,this).getFilePublisherIDs(cnt, mask) : new FakeGAID_Default().getFilePublisherIDs(cnt, mask);
        return res;
    }

    @Override
    public String getInnerPublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, String key) {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new FakeGAID_External(cnt, libServicer,this).getInnerPublisherIDs(control_parameter, cnt, key) : new FakeGAID_Default().getInnerPublisherIDs(control_parameter, cnt, key);
        return res;
    }

    @Override
    public void setGAID(Context cnt, String id) {
        if (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) { new FakeGAID_External(cnt, libServicer,this).setGAID(cnt, id);} else { new FakeGAID_Default().setGAID(cnt, id);}
    }

    @Override
    public String getGAID(Context cnt, String callDestination) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        String res = null;
        res = (libServicer.isExternalLibAccessible(cnt, LIB_NAME)) ? new FakeGAID_External(cnt, libServicer,this).getGAID(cnt, callDestination) : new FakeGAID_Default().getGAID(cnt, callDestination);
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
        Shell.clearFakeGAID();
    }

    protected String getDefaultExtPackageName() {
        return EXTERNAL_PACKAGE_NAME;
    }
}
