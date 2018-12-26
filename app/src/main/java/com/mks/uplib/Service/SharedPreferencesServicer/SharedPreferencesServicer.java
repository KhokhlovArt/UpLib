package com.mks.uplib.Service.SharedPreferencesServicer;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.mks.uplib.Service.CryptoProvider.CryptoProviderServicer;
import com.mks.uplib.Service.CryptoProvider.SimpleBLOWFISHCryptoProvider;
import com.mks.uplib.Service.Logger.Logger;

//Класс отвечающий за работу с SharedPreferences.
//Имеет методы "обертки" которые сохраняют/читают из SharedPreferences
//getSimplePreferences и setSimplePreferences записывают/считывают без кодирования
//getPreferences и setPreferences при записи/считывании кодируют/раскодируют строку (спомощью алгоритма из скрытого в ndk/определенным алгоритмом).
//Если мы хотим скрыть информацию от пользователя то используем методы с кодированием.
public class SharedPreferencesServicer {

    //Метод сохранения в SharedPreferences с кодированием строки
    public static void setPreferences(Context cnt, String session, String key, String value)
    {
        if (value == null){return;}
//        SimpleBLOWFISHCryptoProvider cryptoProvider = new SimpleBLOWFISHCryptoProvider();
//        try {
//            Signature[] sigs = cnt.getPackageManager().getPackageInfo(cnt.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
//            if ((sigs != null)&&(sigs.length > 0))
//            {
//                cryptoProvider.setSeed(String.valueOf(sigs[0].hashCode()));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Logger.log("" + e.getMessage());
//        }
//
//        String codeText = cryptoProvider.cript(value);
        String codeText = CryptoProviderServicer.cript(value);
        cnt.getSharedPreferences(session, Context.MODE_PRIVATE).edit().putString(key, codeText).apply();
    }

    //Метод чтения из SharedPreferences с раскодированием строки
    public static String getPreferences(Context cnt, String session, String key, String default_res)
    {
        String res = cnt.getSharedPreferences(session, Context.MODE_PRIVATE).getString(key, default_res);
        if (res == null){return null;}
//        String decodeText = null;
//        SimpleBLOWFISHCryptoProvider cryptoProvider = new SimpleBLOWFISHCryptoProvider();
//        try {
//            Signature[] sigs = cnt.getPackageManager().getPackageInfo(cnt.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
//            if ((sigs != null) && (sigs.length > 0))
//            {
//                cryptoProvider.setSeed(String.valueOf(sigs[0].hashCode()));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Logger.log("" + e.getMessage());
//        }
//        decodeText = cryptoProvider.deCript(res);
//        return decodeText;
        String decodeText = CryptoProviderServicer.deCript(res);
        return decodeText;
    }

    //Метод сохранения в SharedPreferences без кодированием строки
    public static void setSimplePreferences(Context cnt, String session, String key, String value)
    {
        if (value == null){return;}
        cnt.getSharedPreferences(session, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    //Метод чтения из SharedPreferences без раскодированием строки
    public static String getSimplePreferences(Context cnt, String session, String key, String default_res)
    {
        String res = cnt.getSharedPreferences(session, Context.MODE_PRIVATE).getString(key, default_res);
        if (res == null){return null;}
        return res;
    }
}
