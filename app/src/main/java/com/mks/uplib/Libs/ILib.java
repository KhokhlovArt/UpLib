package com.mks.uplib.Libs;

import android.content.Context;

public interface ILib {
    public String getVersion(Context cnt);
    public String getName();
    public void clearDexClassLoader();
}
