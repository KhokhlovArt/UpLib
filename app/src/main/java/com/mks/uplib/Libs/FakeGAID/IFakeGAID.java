package com.mks.uplib.Libs.FakeGAID;

import android.content.Context;

import com.advertising_id_service.appclick.googleadvertisingidservice.GoogleAdvertisingIdGetterRealisation.IGoogleAdvertisingIdGetter;
import com.advertising_id_service.appclick.googleadvertisingidservice.PublisherID.PublisherIDMask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.List;

public interface IFakeGAID{
    String getVersion(Context c);
    String getOriginalID(final Context cnt);
    String generateGUID(IGoogleAdvertisingIdGetter.GenerateIDType control_parameter, Context cnt);

    String getFakeGaid(final Context cnt) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException;
    List<String> getFilePublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, PublisherIDMask mask);
    String getInnerPublisherIDs(IGoogleAdvertisingIdGetter.PublusherIDType control_parameter, Context cnt, String key);
    void setGAID(final Context cnt, String id);
    String getGAID(final Context cnt, String callDestination) throws GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException;
}
