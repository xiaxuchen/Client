package com.cxyz.vac;

import android.content.Context;
import android.test.mock.MockContext;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.commons.date.DateTime;
import com.cxyz.commons.utils.HttpUtil.CommonOkHttpClient;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.logiccommons.typevalue.VacateType;
import com.cxyz.vac.constant.RequestCenter;
import com.cxyz.vac.dto.VacateDto;
import com.cxyz.vac.imodel.IUploadVacateModel;
import com.cxyz.vac.imodel.impl.IUploadVacateModelImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDateTime()
    {

        DateTime d1 = new DateTime(0,0,0,0,0,0);
        System.out.println(d1.getDate()+d1.getTime());
        System.out.println(new DateTime().getDate()+new DateTime().getTime());
        assertEquals(true,d1.equals(new DateTime()));
    }

    @Test
    public void iUploadModel()
    {
        Context context = Mockito.spy(Context.class);
        CommonOkHttpClient.init(context);
        LogUtil.e("hjhh");
//        IUploadVacateModel iUploadVacateModel = new IUploadVacateModelImpl();
//        iUploadVacateModel.uploadVacate(null, null, new IBaseModel.ModelListener<String, String>() {
//            @Override
//            public void onSuccess(String data) {
//                LogUtil.e("");
//            }
//
//            @Override
//            public void onFail(String s) {
//
//            }
//        });
    }

    @Test
    public void testSubString()
    {
        File file = new File("F:\\program files\\apache-tomcat-8.5.34-windows-x64\\apache-tomcat-8.5.34\\webapps\\Server_Check_war\\WEB-INF\\d5511440a8434b2c8eb9f53d96cf4644.png");
        if(!file.exists())
            System.out.println("caonima");
//        String path = "4/7/daskljdf.jpg";
//        System.out.println(new StringBuilder(path.substring(0,path.lastIndexOf('.')))
//                .append('/').append(path.substring(path.lastIndexOf('.')+1)).toString());
    }
}