package com.decom.chunks.java8DateTime;

import junit.framework.TestCase;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/30.
 *
 * java 8 包含了一套全新的日期时间类，主要有：
 * 1、Instant——代表时间戳
 * 2、LocalDate——不包含具体时间（指HH:mm:ss）的日期
 * 3、LocalTime——不包含日期（指yyyy-MM-dd）的时间
 * 4、LocalDateTime——包含日期和时间，但不包含时区
 * 5、ZonedDateTime——包含完整时区的日期、时间
 */
public class DataTimeUtil extends TestCase {

    //LocalDate
    @Test
    public void testLocalDateTest() {
        LocalDate today = LocalDate.now();
        System.out.println("今天日期是：" + today);
        System.out.println("今天的年份是：" + today.getYear());
        System.out.println("今天的月份是：" + today.getMonth().getValue());
        System.out.println("今天是所在月份的几号：" + today.getDayOfMonth());
        System.out.println("今天是星期几：" + today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
        System.out.println("今天是一年中的第多少天：" + today.getDayOfYear());

        LocalDateTime localDateTime = today.atTime(LocalTime.of(18, 20, 30));
        System.out.println("在指定日期后加上时间后的日期时间为：" + localDateTime);

        //日期计算
        LocalDate previousDate = LocalDate.of(2016, 8, 19);
//        today = today.minus(previousDate);

        LocalDate minusYears = today.minus(1000, ChronoUnit.YEARS);  //第2个参数是要减云的时间的单位，为枚举值
        System.out.println("当前日期减去1000年后的日期为：" + minusYears);

        LocalDate minusDays = today.minusDays(10);
        System.out.println("当前日期减去10天后的日期为：" + minusDays);

        LocalDate plusDays = today.plusDays(10);
        System.out.println("当前加10天后的日期为：" + plusDays);

    }

    //测试构造指定日期
    @Test
    public void testCreateLocalDate() {
        LocalDate parseLocalDate = LocalDate.of(2000, 5, 10);
        System.out.println("构造的日期为：" + parseLocalDate);
        parseLocalDate = LocalDate.of(2000, Month.APRIL, 8);
        System.out.println("月份采用枚举构造日期：" + parseLocalDate);
        parseLocalDate = LocalDate.parse("2001-01-19");
        System.out.println("1位月份数字前补0日期：" +  parseLocalDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd");
        parseLocalDate = LocalDate.parse("2000,10,10", formatter);              //如果日期参数为2000-10-10，会报错
        System.out.println("指定日期字符串格式，构造的日期为：" + parseLocalDate);
    }

    //测试日期比较
    @Test
    public void testCompareLocalDate() {
        LocalDate previousLocalDate = LocalDate.of(2008, 9, 10);
        LocalDate now = LocalDate.now();
        if(previousLocalDate.isEqual(now)) {
            System.out.println("指定日期：" + previousLocalDate + "和当前日期" + now + "相等");
        } else {
            System.out.println("指定日期：" + previousLocalDate + "和当前日期" + now + "不相等");
        }
        if(previousLocalDate.isLeapYear()) {//是否是闰年
            System.out.println("指定日期：" + previousLocalDate + "是闰年");
        } else {
            System.out.println("指定日期：" + previousLocalDate + "不是闰年");
        }
        if(previousLocalDate.isBefore(now)) {
            System.out.println("指定日期：" + previousLocalDate + "早于当前日期" + now);
        } else {
            System.out.println("指定日期：" + previousLocalDate + "晚于当前日期" + now);
        }
    }

    //重复事件检查
    @Test
    public void testCheckBirthday() {
        LocalDate dateOfBirth = LocalDate.of(1990, 8, 10);      //出生日期
        MonthDay monthDay = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());//生日

        LocalDate now = LocalDate.now();
        MonthDay todayMonthDay = MonthDay.from(now);

        if(monthDay.equals(todayMonthDay)) {
            System.out.println("今天是您的生日");
        } else {
            System.out.println("今天不是您的生日");
        }
    }

    //LocalTime，当前时间，不包含日期
    @Test
    public void testLocalTime() {
        LocalTime localTime = LocalTime.now();
        System.out.println("当前时间是：" + localTime);

        LocalDateTime localDateTime = localTime.atDate(LocalDate.of(2000, 10, 10));
        System.out.println("在localtime基础上加上指定日期后的时期时间为：" + localDateTime);
    }

    //clock 时钟，获取当前瞬时时间、日期（和时区有关），取代System.currentTimelnMillis()和TimeZone.getDefault()
    @Test
    public void testClock() {
        Clock nowMillis = Clock.systemUTC();
        System.out.println("当前毫秒是：" + nowMillis.millis());

        Clock nowZone = Clock.systemDefaultZone();
//        System.out.println("当前时区是：" + nowZone.getZone().getDisplayName(TextStyle.FULL, Locale.CHINA));
        System.out.println("当前时区是：" + nowZone.getZone());

//        Clock nowTickMinutes = Clock.tickMinutes(ZoneId.of("Asia/Shanghai"));
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        Clock nowTickMinutes = Clock.tickMinutes(ZoneId.systemDefault());

        System.out.println("当前分钟是：" + nowTickMinutes.toString());

    }

}
