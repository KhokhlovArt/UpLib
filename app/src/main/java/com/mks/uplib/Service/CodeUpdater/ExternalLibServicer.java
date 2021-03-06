package com.mks.uplib.Service.CodeUpdater;

import android.content.Context;
import android.util.Log;

import com.mks.uplib.C;
import com.mks.uplib.Service.Logger.Logger;
import com.mks.uplib.Service.SharedPreferencesServicer.SharedPreferencesServicer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dalvik.system.DexClassLoader;

public class ExternalLibServicer {
    DexClassLoader dexClassLoader;
    String libName;

    public ExternalLibServicer(Context cnt, String libName){
        this.libName = libName;
        String filePath = getDexFilePath(cnt);
        File dexFile = new File(filePath);
        if( dexFile.length() == 0){ // Если самого файла нет - ничего не загружаем
            dexClassLoader = null;
            return;
        }

        String dexHashCode = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SESSION_BASE_DEX_HASH + this.libName, C.SPF_KEY_BASE_DEX_HASH + this.libName, null);

        //Проверяем что наш DEX не подменили и он имеет тот же хэш-код что и при скачивании
        //String realDexHash = String.valueOf(dexFile.hashCode());
        String realDexHash = null;
        try {
            realDexHash = sha1Code(filePath);
        } catch (IOException e) {
            Logger.log(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Logger.log(e.getMessage());
        }

        Logger.log("dex_code = " + dexHashCode);
        Logger.log("dex_f_code = " + realDexHash + " size: " + dexFile.length());

        if((dexHashCode!= null)&&(dexHashCode.equals(realDexHash))) {
            File codeCacheDir = new File(cnt.getCacheDir() + File.separator + "codeCache");
            codeCacheDir.mkdirs();
            dexClassLoader = dexFile.length() == 0 ? null : new DexClassLoader( dexFile.getAbsolutePath(), codeCacheDir.getAbsolutePath(), null, cnt.getClassLoader());
        }
        else
        {
            Logger.log("Ошибка загрузки dex файла. Не совпадают hash суммы.");
            dexClassLoader = null;
        }
    }

//    public static synchronized ExternalLibServicer getServicer(Context cnt, String libName)
//    {
//        if (instance == null){
//            instance = new ExternalLibServicer(cnt, libName);
//            if(dexClassLoader == null) //Если лоадер не смог подгрузить библиотеку - считаем что класс не должен быть создан
//            {
//                instance = null;
//            }
//        }
//        return instance;
//    }

    protected String sha1Code(String filePath) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
        byte[] bytes = new byte[1024];
        while (digestInputStream.read(bytes) > 0);

        byte[] resultByteArry = digest.digest();
        return bytesToHexString(resultByteArry);
    }

    protected static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int value = b & 0xFF;
            if (value < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(value).toUpperCase());
        }
        return sb.toString();
    }

    public void clearDexClassLoader()
    {
        //instance = null;
        dexClassLoader = null;
    }

    public boolean isExternalLibAccessible(Context cnt, String name)
    {
//        getServicer(cnt, name);
        return (dexClassLoader != null);
    }

    protected String getDexFilePath(Context cnt)
    {
        return "" + C.DexFilePath(cnt, libName);
    }

    protected  String getDexFilePathZip(Context cnt)
    {
        return "" + C.DexFilePathZip(cnt, libName);
    }

    public Class getExternalClass(Context cnt, String className)
    {
        if (dexClassLoader != null) {
            try {
                return dexClassLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    //*********************************************************************************************
    //*********************************************************************************************
    //*********************** Методы запуска кода из DEX-а через рефлексию ************************
    //*********************************************************************************************
    //*********************************************************************************************

    public void printClassMethods(Class c)
    {
        try {
            Method[] m = c.getDeclaredMethods();
            System.out.println("----- class " + c + " methods -----");
            for (int i = 0; i < m.length; i++)
                System.out.println(m[i].toString());
        } catch (Throwable e) {
            System.err.println(e);
        }
    }

    public Object getInstance(Class clazz, Object[] arg, Class[] argClass )
    {
        Object objOfClass = null;
        Constructor<?> constructor = null;
        if (clazz != null) {
            try {
                if (arg.length > 0) {
                    constructor = clazz.getDeclaredConstructor(argClass);
                    objOfClass = constructor == null ? null : constructor.newInstance(arg);
                } else {
                    constructor = clazz.getDeclaredConstructor();
                    objOfClass = constructor == null ? null : constructor.newInstance();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return objOfClass;
    }

    public <T> T callMethod(Class clazz, Object objOfClass, String methodName, Object[] arg, Class[] argClass )
    {
        if ((clazz != null) && (objOfClass != null)) {
            try {
                Method exists = clazz.getMethod(methodName, argClass);
                exists.setAccessible(true);
                return (T) exists.invoke(objOfClass, arg);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> T callStaticMethod(Class clazz, String methodName, Object[] arg, Class[] argClass )
    {
        if (clazz != null) {
            try {
                Method exists = clazz.getMethod(methodName, argClass);
                exists.setAccessible(true);
                return (T) exists.invoke(null, arg);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> T getAttribute(Class clazz, Object objOfClass, String fildName)
    {
        Field field = null;
        if ((clazz != null) && (objOfClass != null)) {
            try {
                field = clazz.getDeclaredField(fildName);
                field.setAccessible(true);
                return (T) field.get(objOfClass);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean setAttribute(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
    /*
    Метод возвращает параметр из Enum-а из внешнего dex-а
    @params clazz - Класс Enum-а из которого
    @params i     - Номер по порядку какой параметр вернуть (при "Enum Color {RED, GREEN, BLUE}" 0 - вернеет RED, 1 - вернеет GREEN ...)
    Пример использования:
        Class IApi_RestReadType = loader.getExternalClass(getApplicationContext(), "com.advertising_id_service.appclick.googleadvertisingidservice.REST.IApi$RestReadType");
        loader.getEnumValue(IApi_RestReadType, 0)
    */
    public <T> T getEnumValue(Class clazz, int i)
    {
        if ((clazz != null) && (clazz.isEnum())) {
            return (T) clazz.getEnumConstants()[i];
        }
        return null;
    }
    //*********************************************************************************************
}
