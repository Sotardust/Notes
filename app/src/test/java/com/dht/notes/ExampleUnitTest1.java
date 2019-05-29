package com.dht.notes;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * created by dht on 2019/5/21 10:23
 */
public class ExampleUnitTest1 {

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
}
