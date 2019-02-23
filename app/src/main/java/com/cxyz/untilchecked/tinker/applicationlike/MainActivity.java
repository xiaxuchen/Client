package com.cxyz.untilchecked.tinker.applicationlike;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.cxyz.untilchecked.R;


/**
 * Created by Administrator on 2019/1/24.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
    }

}
