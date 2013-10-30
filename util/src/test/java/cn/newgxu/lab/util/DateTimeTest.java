/*
 * The MIT License (MIT)
 * Copyright (c) newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static cn.newgxu.lab.util.DateTime.getRelativeTimeString;
import static java.util.Calendar.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-9
 * @Mail im.longkai@gmail.com
 */
public class DateTimeTest {

    private static Date date;
    private static Calendar c;

    @BeforeClass
    public static void init() {
        c = getInstance();
        c.set(YEAR, 2013);
        c.set(MONTH, (int) (Math.random() * 12));
        c.set(DATE, (int) (Math.random() * 29));
        c.set(HOUR_OF_DAY, (int) (Math.random() * 24));
        c.set(MINUTE, (int) (Math.random() * 60));
        c.set(SECOND, (int) (Math.random() * 60));
        date = c.getTime();
    }

    @Test
    public void testFormat() throws Exception {
        String s = DateTime.format(date);
        System.out.println(s);
        Date d = DateTime.parse(s);
        org.junit.Assert.assertTrue("after format, the string to date offset must <= 1000ms",
                d.getTime() - date.getTime() <= 1000);
    }

    @Test
    public void testFormatWithCustomPattern() throws Exception {
        String s = DateTime.format(date, DateTime.ISO_DATE);

        int month = c.get(MONTH) + 1;
        String _month = month < 10 ? "0" + month : "" + month;
        int date = c.get(DATE);
        String _date = date < 10 ? "0" + date : "" + date;

        String s2 = c.get(YEAR) + "-" + _month + "-" + _date;
        org.junit.Assert.assertEquals("format string must be same", s2, s);
    }

    @Test
    public void testParse() throws Exception {
//        same way
        testFormat();
    }

    @Test
    public void testGetRelativeTimeString() throws Exception {
        Calendar _now = getInstance();

        Calendar seconds = getInstance();
        seconds.setTimeInMillis(_now.getTimeInMillis() - 20000);

        Calendar minutes = getInstance();
        minutes.set(_now.get(YEAR),
                _now.get(MONTH), _now.get(DATE),
                _now.get(HOUR_OF_DAY), _now.get(MINUTE) - 23, _now.get(SECOND));

        Calendar hours = getInstance();
        hours.set(_now.get(YEAR),
                _now.get(MONTH), _now.get(DATE),
                _now.get(HOUR_OF_DAY) - 13, _now.get(MINUTE), _now.get(SECOND));

        Calendar days = getInstance();
        days.set(_now.get(YEAR),
                _now.get(MONTH), _now.get(DATE) - 3,
                _now.get(HOUR_OF_DAY) - 13, _now.get(MINUTE), _now.get(SECOND));

        Calendar months =  getInstance();
        months.set(_now.get(YEAR),
                _now.get(MONTH) - 7, _now.get(DATE),
                _now.get(HOUR_OF_DAY), _now.get(MINUTE), _now.get(SECOND));

        Calendar years = getInstance();
        years.set(_now.get(YEAR) - 43,
                _now.get(MONTH), _now.get(DATE),
                _now.get(HOUR_OF_DAY) - 13, _now.get(MINUTE), _now.get(SECOND));

        assertThat(getRelativeTimeString(_now.getTimeInMillis()).contains("刚"), is(true));
        assertThat(getRelativeTimeString(seconds.getTimeInMillis()).contains("秒"), is(true));
        assertThat(getRelativeTimeString(minutes.getTimeInMillis()).contains("分钟"), is(true));
        assertThat(getRelativeTimeString(hours.getTimeInMillis()).contains("小时"), is(true));
        assertThat(getRelativeTimeString(days.getTimeInMillis()).contains("天"), is(true));
        assertThat(getRelativeTimeString(months.getTimeInMillis()).contains("月"), is(true));
        assertThat(getRelativeTimeString(years.getTimeInMillis()).contains("年"), is(true));
    }
}
