package com.cxyz.commons.widget.loading.indicator;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;
import com.wang.avi.indicators.BallBeatIndicator;

import java.util.WeakHashMap;

/**
 * Created by Administrator on 2019/2/22.
 */

public class Creator {

    private static final WeakHashMap<String,Indicator> INDICATORS = new WeakHashMap<>();



    public static Indicator create(Context context,String type)
    {
        if(type == null || type.isEmpty())
            return null;
        Indicator indicator = INDICATORS.get(type);
        if(indicator!=null)
            return indicator;
        StringBuilder builder = new StringBuilder();
        if(!type.contains("."))
        {
            builder.append(Indicator.class.getPackage().getName())
                    .append(".indicators.");
        }
        builder.append(type);
        try {
            indicator = (Indicator) Class.forName(builder.toString()).newInstance();
            INDICATORS.put(type,indicator);
            return indicator;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
