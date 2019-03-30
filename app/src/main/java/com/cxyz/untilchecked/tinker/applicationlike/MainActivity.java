package com.cxyz.untilchecked.tinker.applicationlike;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.SpUtil;
import com.cxyz.untilchecked.R;


/**
 * Created by Administrator on 2019/1/24.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        SpUtil edit = SpUtil.getInstance();

        edit.putString("string","hello");
        edit.putInt("int",10);
        edit.putBoolean("boolean",true);
        edit.putFloat("float",1.14f);
        print(edit.getInt("int",0));
        print(edit.getFloat("float",0));
        print(edit.getBoolean("boolean",false));
        print(edit.getString("string","无"));
    }

    void print(Object o)
    {
        LogUtil.e(o);
    }

}
