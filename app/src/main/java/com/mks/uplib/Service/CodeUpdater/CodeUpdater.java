package com.mks.uplib.Service.CodeUpdater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.mks.uplib.C;
import com.mks.uplib.Libs.ILib;
import com.mks.uplib.Service.CryptoProvider.CryptoProviderServicer;
import com.mks.uplib.Service.FilesLoader.FilesLoader;
import com.mks.uplib.Service.HttpsConnection.HttpsConnectionServicer;
import com.mks.uplib.Service.HttpsConnection.Proxy;
import com.mks.uplib.Service.Logger.Logger;
import com.mks.uplib.Service.SharedPreferencesServicer.SharedPreferencesServicer;
import com.mks.uplib.Service.Shell_external.Shell_external;
import com.mks.uplib.Shell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeUpdater {
    public static int LOADER_NEW_CODE_VERSION = 100;

    public JSONObject getJsonObj(String json_str)
    {
        if(json_str == null) {return null;}
        boolean isFileCript = false;
        JSONObject rootObj = null;
        try {
            //Если пришёл файл в не закодированном виде, то просто формируем из него JSON
            rootObj = new JSONObject(json_str);
        } catch (JSONException e) {
            //Если мы не смогли сформировать JSON, то скорее всего файл просто зашифрован, расшифровываем его и пробуем снова
            isFileCript = true;
        }

        if (isFileCript){
            try {
                String decode_json_string = new String(CryptoProviderServicer.toByte(CryptoProviderServicer.deCript(json_str)));
                rootObj = new JSONObject(decode_json_string.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
                Logger.log("Ошибка загрузки конфигурационного файла. Ошибка: " + e2.getMessage());
            }
        }

        if (rootObj == null) {//Если ничего не скачалось - выходим
            Logger.log("Не получилось разобрать JSON конфигурационного файла");
            return null;
        }
        return rootObj;
    }

    private void downloadConfFile(Context cnt, String downloadID)
    {
        String url_to_conf_file = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SESSION_PATH_TO_CONF_FILE, C.SPF_KEY_PATH_TO_CONF_FILE, null);
        String path = (url_to_conf_file == null) ? C.URL_TO_CONFIG_FILE : url_to_conf_file;
        Logger.log("Грузим файл из:" + path);
        FilesLoader.saveFile(cnt, path, true, "Download conf file", downloadID, C.ConfigFilePathZip(cnt), C.getBasePath(cnt), C.CONFIG_FILE_NAME_ZIP);
    }

    private void checkConfFile(Context cnt, String downloadID, LoaderManager lm)
    {
        String json_str = loadJSONFromAsset(cnt, C.ConfigFilePath(cnt));
        JSONObject rootObj = getJsonObj(json_str);
        if (rootObj == null) {
            return;
        }
        try {
            String path_to_conf_file = rootObj.getString(C.JSON_KEY_PATH_TO_CONF_FILE);
            //String path_to_conf_file_last = cnt.getSharedPreferences(GlobalParameters.SPF_SESSION_PATH_TO_CONF_FILE, Context.MODE_PRIVATE).getString(GlobalParameters.SPF_KEY_PATH_TO_CONF_FILE, null);
            String path_to_conf_file_last = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SESSION_PATH_TO_CONF_FILE, C.SPF_KEY_PATH_TO_CONF_FILE, null);
            if (!path_to_conf_file.equals(path_to_conf_file_last)) {
                Logger.log("Надо загрузить конфигурационный файл из другого места:" + path_to_conf_file);
                Logger.log("Вместо:" + path_to_conf_file_last);
                //cnt.getSharedPreferences(GlobalParameters.SPF_SESSION_PATH_TO_CONF_FILE, Context.MODE_PRIVATE).edit().putString(GlobalParameters.SPF_KEY_PATH_TO_CONF_FILE, path_to_conf_file).apply();
                SharedPreferencesServicer.setPreferences(cnt, C.SPF_SESSION_PATH_TO_CONF_FILE, C.SPF_KEY_PATH_TO_CONF_FILE, path_to_conf_file);
                new CodeUpdater().updateCode(cnt, lm);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.log("Ошибка загрузки конфигурационного файла" + e.getMessage());
        }

        //Когда скачали нужную версию конфигурационного файла, то можем по ней искать нужную нам версию
        ILib[] libs = C.getLibs(cnt);
        for (int i = 0; i<libs.length; i++ ) {
            String url = getUrlToLoadDexFile(cnt, libs[i]);
            if (url != null) {
                String full_url = getUrlToDownloadFile(cnt, url, "Download conf file", downloadID);
                FilesLoader.downloadDexFile(cnt, full_url, libs[i].getName());
                libs[i].clearDexClassLoader();
            }
        }
        saveAdditionalConfigOptions(cnt);
    }

    public void updateCode(final Context cnt, final LoaderManager lm)
    {
        final String downloadID = UUID.randomUUID().toString();
        if (lm != null) {
            lm.restartLoader(LOADER_NEW_CODE_VERSION, null, new LoaderManager.LoaderCallbacks<String>() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public Loader<String> onCreateLoader(int i, Bundle bundle) {
                    return new AsyncTaskLoader<String>(cnt) {
                        public String loadInBackground() {
                            String res = null;
                            downloadConfFile(cnt, downloadID);
                            return res;
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<String> loader, String result) {
                    checkConfFile(cnt, downloadID, lm);
                }

                @Override
                public void onLoaderReset(Loader<String> loader) {
                }
            }).forceLoad();
        }
        else
        {
            new updateLoaders().setCnt(cnt).setDownloadID(downloadID).execute();
        }
    }

    public String getUrlToLoadDexFile(Context cnt, ILib LibName)
    {
        String current_version = LibName.getVersion(cnt);
        current_version = (current_version == null) ? "1.0.0" : current_version;

        String device_id = Settings.Secure.getString(cnt.getContentResolver(), Settings.Secure.ANDROID_ID);

        String json_str = loadJSONFromAsset(cnt, C.ConfigFilePath(cnt));
        String res = null;
        JSONObject obj = null;
        JSONObject dexObj = null;
        try {
            JSONObject rootObj = getJsonObj(json_str);

            dexObj = rootObj.getJSONObject(C.JSON_KEY_DEXS);

            JSONObject obj_lib = dexObj.getJSONObject(LibName.getName());
            obj = obj_lib.getJSONObject(C.JSON_KEY_VERSIONS);
            Logger.log("[" + LibName.getName() + "] Текущая версия " + current_version);
            if (obj != null)
            {
                String key = null;
                Iterator<?> keys = obj.keys();
                while( keys.hasNext() ) {
                    key = (String)keys.next();
                    Logger.log("Проверяем версию " + key + "----->");
                    if (compareCodeVersion(current_version, key) > 0)
                    {
                        boolean isDeviceIDApprove   = false;
                        boolean isApkPackageApprove = true;
                        boolean isVersion           = true;
                        if ( obj.get(key) instanceof JSONObject ) {
                            JSONObject versionObj = (JSONObject) obj.get(key);

                            //Проверяем устройства
                            //--------------->
                            JSONArray arr = versionObj.getJSONArray(C.JSON_KEY_DEVICE_ID);
                            Logger.log("Current device_id = " + device_id);
                            for(int i = 0; i <arr.length(); i++) {
                                Logger.log("debug device = " + arr.getString(i));
                                if (device_id.equals(arr.getString(i)))
                                {
                                    isDeviceIDApprove = true;
                                    break;
                                }
                            }
                            if (arr.length() == 0){ isDeviceIDApprove = true; }
                            //<---------------


                            //Проверяем что с данной версии можно обновиться
                            //--------------->
                            arr = versionObj.getJSONArray(C.JSON_KEY_FORBIDDEN_VERSION);
                            for(int i = 0; i <arr.length(); i++) {
                                String selectJsonObj = arr.getString(i).replaceAll("\\s+","");
                                Logger.log("forbidden version = " + selectJsonObj);
                                String mask = "\\d*.\\d*.\\d*-\\d*.\\d*.\\d*";
                                Pattern p = Pattern.compile(mask, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
                                Matcher m = p.matcher(selectJsonObj);
                                if (m.matches()) {//Если задан диапазон ("1.7.6 - 1.8.2")
                                    String[] Arr = selectJsonObj.split("-");
                                    if(Arr.length == 2) {
                                        //Если текущая версия в диапазоне, тогда не обновляем
                                        isVersion = !(((compareCodeVersion(current_version, Arr[0]) <= 0) && (compareCodeVersion(current_version, Arr[1]) >= 0)));
                                    }
                                    else
                                    {
                                        isVersion = false;
                                    }
                                    if (!isVersion) {break;}
                                }
                                else {//Если задано просто значение
                                    if (current_version.equals(selectJsonObj)) {
                                        isVersion = false;
                                        break;
                                    }
                                }
                            }
                            //<---------------

                            //Проверяем что с данного приложения можно обновиться
                            //--------------->
                            arr = versionObj.getJSONArray(C.JSON_KEY_FORBIDDEN_APK_PACKAGE);
                            String packageName = cnt.getPackageName();
                            Logger.log("Current apk_package = " + packageName);
                            for(int i = 0; i <arr.length(); i++) {
                                Logger.log("forbidden apk package = " + arr.getString(i));
                                if (packageName.equals(arr.getString(i)))
                                {
                                    isApkPackageApprove = false;
                                    break;
                                }
                            }
                            //<---------------
                            if(isDeviceIDApprove && isApkPackageApprove && isVersion)
                            {
                                Logger.log("Загружаем версию " + key);
                                res = versionObj.getString(C.JSON_KEY_PATH);
                                //Сохраняем HESH-код скачиваемого DEX-файла
                                String hash_code = versionObj.getString(C.JSON_KEY_DEX_HASH);
                                SharedPreferencesServicer.setPreferences(cnt, C.SPF_SESSION_BASE_DEX_HASH + LibName.getName(), C.SPF_KEY_BASE_DEX_HASH + LibName.getName(), hash_code);

                                //Сохраняем имя пакета для запуска методов библиотеки из DEX-а
                                String external_package_name = versionObj.getString(C.JSON_KEY_EXT_PACKAGE_NAME);
                                SharedPreferencesServicer.setSimplePreferences(cnt, C.SPF_SESSION_LIB_DEX_EXT_PACKAGE + LibName.getName(), C.SPF_KEY_LIB_DEX_EXT_PACKAGE + LibName.getName(), external_package_name);
                                break;
                            }
                        }
                    }
                    Logger.log("<-----" + key + "");
                }
            }
        } catch (JSONException e) {
            Logger.log("Ошибка при загрузке конфигурационного файла: " + e.getMessage());
            e.printStackTrace();
        }

        if (res == null){
            Logger.log("Текущая версия актуальна");}
        return res;
    }

    private void saveAdditionalConfigOptions(Context cnt)
    {
        String json_str = loadJSONFromAsset(cnt, C.ConfigFilePath(cnt));


        try {
            JSONObject rootObj = getJsonObj(json_str);
            //Считываем настройки прокси
            JSONArray proxys;
            proxys = rootObj.getJSONArray(C.JSON_KEY_PROXYS);
            if(proxys != null){
                for(int i = 0; i <proxys.length(); i++) {
                    JSONObject proxy = proxys.getJSONObject(i);

                    String host  = proxy.getString(C.JSON_KEY_PROXY_HOST);
                    String port  = proxy.getString(C.JSON_KEY_PROXY_PORT);
                    String login = proxy.getString(C.JSON_KEY_PROXY_LOGIN);
                    String pass  = proxy.getString(C.JSON_KEY_PROXY_PASSWORD);
                    int timeout  = proxy.getInt(C.JSON_KEY_PROXY_TIMEOUT);
                    Logger.log("Добавляем Proxy: " + host + ":" + port);
                    if (HttpsConnectionServicer.AdditionalProxys == null) { HttpsConnectionServicer.AdditionalProxys = new ArrayList<>();}
                    HttpsConnectionServicer.AdditionalProxys.clear();
                    HttpsConnectionServicer.AdditionalProxys.add(new Proxy().setHost(host).setPort(port).setUsername(login).setPassword(pass).setTimeout(timeout));
                }
            }

            //Считываем настройки автообновления
            JSONObject period = rootObj.getJSONObject("period");
            int h = period.getInt("hour");
            int m = period.getInt("minute");
            int s = period.getInt("second");

            String delta = "" + (h*60*60*1000 + m*60*1000 + s*1000);

            if(!delta.equals(SharedPreferencesServicer.getSimplePreferences(cnt, C.SPF_SESSION_PERIOD, C.SPF_KEY_PERIOD, "")))
            {
                SharedPreferencesServicer.setSimplePreferences(cnt, C.SPF_SESSION_PERIOD, C.SPF_KEY_PERIOD, delta);
            }
        } catch (JSONException e) {
            Logger.log("Ошибка при загрузке конфигурационного файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Сравнивает версии в формате 1.1.1
    // Если текущая версия меньше новой - вернет 1
    // Если текущая версия равна новой  - вернет 0
    // Если текущая версия больше новой - вернет -1
    // Если формат не разобран          - вернет -2
    private int compareCodeVersion(String cur_v, String new_v)
    {
        if(cur_v.equals(new_v)) {return 0;} //Если равны

        String[] cur_v_arr = cur_v.split("\\.");
        String[] new_v_arr = new_v.split("\\.");

        if((cur_v_arr.length != 3) || (new_v_arr.length != 3)){return -2;}

        for (int i = 0; i < 3; i++) {
            if (Integer.parseInt(cur_v_arr[i]) < Integer.parseInt(new_v_arr[i])) {return  1;}
            if (Integer.parseInt(cur_v_arr[i]) > Integer.parseInt(new_v_arr[i])) {return -1;}
        }
        return 0;
    }

    public String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            File file = new File(fileName.toString());
            FileInputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


//    public void sendLog(final Context cnt, final LoaderManager lm, final String downloadID, final String comment)
//    {
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                GoogleAdvertisingIdGetter g = new GoogleAdvertisingIdGetter();
//                IRestServicer rs = RestServicer.getRestServicer(RestServicer.TypeRestServicer.SIMPLE);
//                rs.sendLog(cnt, lm, "", "test", "1111", downloadID, comment);
//            }
//        });
//    }
//
    public String getUrlToDownloadFile(Context cnt, String path, String comment, String deviceID)
    {
        return path;
//        return RestServicer.getUrlToDownloadFile(cnt, path, comment, deviceID);
    }




    /**********************************************************************************************/
    private class updateLoaders extends AsyncTask<Void, Void, Void> {
        Context cnt;
        String downloadID;
        String device_id;
        updateLoaders setCnt(Context _cnt){
            cnt = _cnt;
            return this;
        }
        updateLoaders setDownloadID(String _downloadID){
            downloadID = _downloadID;
            return this;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            downloadConfFile(cnt, downloadID);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            checkConfFile(cnt, downloadID, null);
        }
    }
}
