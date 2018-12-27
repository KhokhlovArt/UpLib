package com.mks.uplib.Service.Shell_external;

import android.content.Context;

import com.mks.uplib.Libs.ILib;
import com.mks.uplib.Shell;

public class UpLib implements ILib {

    /*Это класс-заглушка нужен что бы загрузить DEX для самой себя*/

    @Override
    public String getVersion(Context cnt) {
        return new Shell().getVersion(cnt);
    }

    @Override
    public String getName() {
        return Shell_external.LIB_NAME;
    }

    @Override
    public void clearDexClassLoader() {
        Shell.clearUpLib();
        Shell_external.clear();
    }
}
