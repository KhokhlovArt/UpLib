package com.mks.uplib.Libs.PushLib;

import android.content.Context;

import com.mks.pushlib.NotificationParams.NotificationParams;
import com.mks.pushlib.NotificationViewer;

public class PushLib_Default  implements IPushLib {
    @Override
    public String getVersion(Context c) {
        return new NotificationViewer().getVersion(c);
    }

    @Override
    public boolean showNotification(Context cnt, String message) {
        return new NotificationViewer().showNotification(cnt, message);
    }

    @Override
    public void subscribeToTopic(Context cnt, String topic) {
        new NotificationViewer().subscribeToTopic(cnt, topic);
    }

    @Override
    public void unsubscribeFromTopic(Context cnt, String topic) {
        new NotificationViewer().unsubscribeFromTopic(cnt, topic);
    }

    @Override
    public void init(Context cnt) {
        new NotificationViewer().init(cnt);
    }

    @Override
    public void send(Context cnt, NotificationParams param) {
        new NotificationViewer().send(cnt, param);
    }
}
