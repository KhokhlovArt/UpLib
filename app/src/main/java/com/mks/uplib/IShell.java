package com.mks.uplib;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.mks.uplib.Libs.FakeGAID.FakeGAID;
import com.mks.uplib.Libs.FakeGAID.IFakeGAID;
import com.mks.uplib.Libs.PushLib.IPushLib;
import com.mks.uplib.Libs.PushLib.PushLib;
import com.mks.uplib.Libs.SendStatLib.ISendStatLib;
import com.mks.uplib.Libs.SendStatLib.SendStatLib;

public interface IShell{
    String getVersion(Context cnt);
    void updateLibs(Context cnt, LoaderManager lm);
    void initialize(final Context cnt);
}
