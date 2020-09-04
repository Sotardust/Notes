package com.dht.notes;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * created by dht on 2019/5/21 10:23
 */
public class ExampleUnitTest1 {

    private static final String TAG = "dht";

    @Test
    public void testDat() {
        String endDate = "2019-5-20";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = format.parse(endDate);
            long result = date.getTime() - (long) 30 * 24 * 60 * 60 * 1000;
            Date date1 = new Date(result);

            String value = format.format(date1);
            System.out.println("dht+ " + "onInitData: value = " + value + ",date = " + date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDate() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        SimpleDateFormat format1 = new SimpleDateFormat("mm月dd日 HH:mm", Locale.CHINA);
        try {
            Date date = format.parse("2019-5-20 12:05:33");
            format1.format(date);
            System.out.println("ExampleUnitTest1.testDate = " + format1.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("format1 = afterTextChanged: s.toString().contains = " + "123.56".contains("\\.") + ", =" + "123.56".contains("."));
    }

    @Test
    public void testThreadPoolExecutor() {

        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5,
                10,
                5000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(1000));


        for (int i = 0; i < 100; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    Thread thread = Thread.currentThread();
                    System.out.println("execute Thread_name = " + thread.getName() + " Thread_activeCount = " + Thread.activeCount() + " getCorePoolSize: " + executor.getCorePoolSize() + " queue.size =" + executor.getQueue().size());

                }
            });
        }

        for (int i = 0; i < 100; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {

                    Thread thread = Thread.currentThread();
                    System.out.println("submit Thread_name = " + thread.getName() + " Thread_activeCount = " + Thread.activeCount() + " getCorePoolSize: " + executor.getCorePoolSize() + " queue.size =" + executor.getQueue().size());

                }
            });
        }

    }
}
