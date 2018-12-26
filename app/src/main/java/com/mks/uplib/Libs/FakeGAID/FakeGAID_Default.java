package com.mks.uplib.Libs.FakeGAID;

import android.content.Context;

import com.advertising_id_service.appclick.googleadvertisingidservice.GoogleAdvertisingIdGetter;
import com.advertising_id_service.appclick.googleadvertisingidservice.GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter;
import com.advertising_id_service.appclick.googleadvertisingidservice.PublisherID.PublisherIDMask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.mks.uplib.Service.Logger.Logger;

import java.io.IOException;
import java.util.List;

public class FakeGAID_Default implements IFakeGAID {
    @Override
    public String getVersion(Context c) {
        Logger.log("FakeGAID_Default.getVersion()");
        return new GoogleAdvertisingIdGetter().getVersion(c);
    }

    @Override
    public String getOriginalID(Context cnt) {
        Logger.log("FakeGAID_Default.getOriginalID()");
        try {
            try {
                return new GoogleAdvertisingIdGetter().getOriginalID(cnt);
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String generateGUID(IGoogleAdvertisingIdGetter.GenerateIDType control_parameter, Context cnt) {
        Logger.log("FakeGAID_Default.generateGUID()");
        return new GoogleAdvertisingIdGetter().generateGUID(control_parameter, cnt);
    }

    @Override
    public String getFakeGaid(Context cnt) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        Logger.log("FakeGAID_Default.getFakeGaid()");
        return new GoogleAdvertisingIdGetter().getFakeGaid(cnt);
    }

    @Override
    public List<String> getFilePublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, PublisherIDMask mask) {
        Logger.log("FakeGAID_Default.getFilePublisherIDs()");
        return new GoogleAdvertisingIdGetter().getFilePublisherIDs(control_parameter, cnt, mask);
    }

    @Override
    public String getInnerPublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, String key) {
        Logger.log("FakeGAID_Default.getInnerPublisherIDs()");
        return new GoogleAdvertisingIdGetter().getInnerPublisherIDs(control_parameter, cnt, key);
    }

    @Override
    public void setGAID(Context cnt, String id) {
        Logger.log("FakeGAID_Default.setGAID()");
        new GoogleAdvertisingIdGetter().setGAID(cnt, id);
    }

    @Override
    public String getGAID(Context cnt, String callDestination) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        Logger.log("FakeGAID_Default.getGAID()");
        return new GoogleAdvertisingIdGetter().getGAID(cnt, callDestination);
    }
}
