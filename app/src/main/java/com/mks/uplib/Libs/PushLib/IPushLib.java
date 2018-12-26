package com.mks.uplib.Libs.PushLib;

import android.content.Context;

import com.mks.pushlib.NotificationParams.NotificationParams;

public interface IPushLib {
    String getVersion(Context c);
    boolean showNotification(Context cnt, String message);
    void subscribeToTopic(Context cnt, String topic);
    void unsubscribeFromTopic(Context cnt, String topic);
    void init(Context cnt);
    void send(final Context cnt, final NotificationParams param);
}
