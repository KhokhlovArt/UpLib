package com.mks.uplib.Service.CryptoProvider;

import android.util.Base64;

import com.advertising_id_service.appclick.googleadvertisingidservice.CryptoProvider.BaseCryptoProvider;
import com.advertising_id_service.appclick.googleadvertisingidservice.CryptoProvider.ICryptoProvider;

public class SimpleBase64CryptoProvider extends BaseCryptoProvider implements ICryptoProvider {
    @Override
    public String cript(String src) {
        return src == null ? null : Base64.encodeToString( src.getBytes(), Base64.DEFAULT );
    }

    @Override
    public String deCript(String src) {
        if( src != null) {
            return new String(Base64.decode(src, Base64.DEFAULT));
        }
        return null;
    }
}
