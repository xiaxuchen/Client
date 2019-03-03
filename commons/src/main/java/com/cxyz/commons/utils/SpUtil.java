package com.cxyz.commons.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import java.util.Map;
import java.util.Set;

/**
 * Created by 夏旭晨 on 2018/9/20.
 *首次使用需用init方法进行初始化，一般在application中完成初始化工作
 * 可以通过getInstance()方法直接获取实例
 *
 * 复写了SharedPreferences和SharedPreferences.Editor的
 * 所有方法，直接通过SpUtils对象进行调用即可，putxxx()
 * 方法无需手动提交，并且可以进行链式调用
 *
 *
 */

public class SpUtil {

    private static final String FILE_NAME = "config";

    private SharedPreferences sp;

    private static SpUtil su;

    private static Context context;

    private SharedPreferences.Editor editor;

    private SpUtil(Context context, String name, int mode)
    {
        sp = context.getSharedPreferences(name, mode);
    }

    public static void init(Context context)
    {
        SpUtil.context = context;
    }

    public synchronized static SpUtil getInstance() {
        if(context==null)
            return null;
        if (su == null) {
            su = new SpUtil(context, FILE_NAME, context.MODE_PRIVATE);
        }
        return su;
    }

    /**
     * 单例获取edit
     * @return
     */
    private Editor edit()
    {
        if(editor == null)
        {
            synchronized (SpUtil.class)
            {
                if(editor == null)
                    editor = sp.edit();
            }
        }
        return editor;
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValues) {
        return sp.getStringSet(key, defValues);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public boolean contains(String key) {
        return sp.contains(key);
    }

    public SpUtil registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sp.registerOnSharedPreferenceChangeListener(listener);
        return this;
    }

    public SpUtil unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sp.unregisterOnSharedPreferenceChangeListener(listener);
        return this;
    }

    public SpUtil putString(String key, String value) {
        edit().putString(key,value).commit();
        return this;
    }

    public SpUtil putStringSet(String key, Set<String> values) {
        edit().putStringSet(key,values).commit();
        return this;
    }

    public SpUtil putInt(String key, int value) {
        edit().putInt(key,value).commit();
        return this;
    }

    public SpUtil putLong(String key, long value) {
        edit().putLong(key,value).commit();
        return this;
    }

    public SpUtil putFloat(String key, float value) {
        edit().putFloat(key,value).commit();
        return this;
    }

    public SpUtil putBoolean(String key, boolean value) {
        edit().putBoolean(key,value).commit();
        return this;
    }

    public synchronized SpUtil remove(String key) {
        synchronized (SpUtil.class)
        {
            edit().remove(key).commit();
        }
        return this;
    }

    public synchronized SpUtil clear() {
        synchronized (SpUtil.class)
        {
            edit().clear().commit();
        }
        return this;
    }

}

