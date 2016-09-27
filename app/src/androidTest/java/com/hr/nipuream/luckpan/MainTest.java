package com.hr.nipuream.luckpan;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.ImageView;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-08-23 14:03
 * 邮箱：571829491@qq.com
 */
public class MainTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Intent intent;

    public MainTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        intent = new Intent(getActivity(),MainActivity.class);
    }

    @MediumTest
    public void testRotate() throws Throwable {
        getActivity().startActivity(intent);
        final ImageView btn = (ImageView) getActivity().findViewById(R.id.go);
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn.performClick();
            }
        });
    }






}
