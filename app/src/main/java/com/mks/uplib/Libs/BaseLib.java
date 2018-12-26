package com.mks.uplib.Libs;

import android.content.Context;

import com.mks.uplib.C;
import com.mks.uplib.Service.CodeUpdater.ExternalLibServicer;
import com.mks.uplib.Service.SharedPreferencesServicer.SharedPreferencesServicer;

public abstract class BaseLib implements ILib {
    protected Context cnt;
    protected ExternalLibServicer libServicer;

    @Override
    public void clearDexClassLoader() {
        libServicer.clearDexClassLoader();
    }

    public String getExtPackageName(Context cnt) {
        return SharedPreferencesServicer.getSimplePreferences(cnt,C.SPF_SESSION_LIB_DEX_EXT_PACKAGE + getName(),C.SPF_KEY_LIB_DEX_EXT_PACKAGE + getName(), getDefaultExtPackageName());
    }

    protected abstract String getDefaultExtPackageName();
    public abstract String getVersion(Context cnt);
    public abstract String getName();

}
