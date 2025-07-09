package com.yyld.conair.biz.entity;

import com.alibaba.fastjson.JSON;
import org.assertj.core.util.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 大屏日期函数
 */
public final class BladeDateUtils {

    //获取前一天的日期
    public static String getpreviousDay() {

        LocalDate nowDate = LocalDate.now();

        return nowDate.minusDays(1).toString();
    }

    //获取前N天的日期
    public static String getpreviousDay(int beforeDay) {

        LocalDate nowDate = LocalDate.now();

        return nowDate.minusDays(beforeDay).toString();
    }

    //当前月往前取月份
    public static List<Integer> getMonths(int num) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM");

        List<Integer> list = Lists.newArrayList();
        Calendar c = Calendar.getInstance();

        for (int i = 0; i < num; i++) {

            if (i == 0) {
                c.add(Calendar.MONTH, -0);
            } else {
                c.add(Calendar.MONTH, -1);
            }
            list.add(Integer.parseInt(sdf.format(c.getTime())));
        }
        Collections.reverse(list);
        return list;
    }

    public static List<String> getYearMonths(int num, int gs) throws ParseException {

        List<String> list = Lists.newArrayList();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -(num + (gs)));
        StringBuilder sb = new StringBuilder();
        sb.append(c.get(Calendar.YEAR)).append("-").append(c.get(Calendar.MONTH));
        String before_six = sb.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(sdf.parse(before_six));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(sdf.parse(sdf.format(new Date())));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        while (curr.before(max)) {
            list.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return list;
    }

    /**
     * 获取上一个月
     *
     * @return
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 描述:获取下一个月.
     *
     * @return
     */
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }

    /**
     * 描述:根据参数获取年月.
     *
     * @return
     */
    public static String getPreMonth(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, month);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }

    public static Date getFirstDay(int year, int month) {
        // 获取Calendar类的实例
        Calendar c = Calendar.getInstance();
        // 设置年份
        c.set(Calendar.YEAR, year);
        // 设置月份，因为月份从0开始，所以用month - 1
        c.set(Calendar.MONTH, month - 1);
        // 设置日期
        c.set(Calendar.DAY_OF_MONTH, 1);

        return c.getTime();
    }

    public static Date getLastDay(int year, int month) {
        // 获取Calendar类的实例
        Calendar c = Calendar.getInstance();
        // 设置年份
        c.set(Calendar.YEAR, year);
        // 设置月份，因为月份从0开始，所以用month - 1
        c.set(Calendar.MONTH, month - 1);
        // 获取当前时间下，该月的最大日期的数字
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 将获取的最大日期数设置为Calendar实例的日期数
        c.set(Calendar.DAY_OF_MONTH, lastDay);

        return c.getTime();
    }

    //取截止到昨天的前30天 日期列表
    public static List<String> getNearly30days() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            LocalDate date = now.minusDays(i + 1);
            String formattedDate = date.format(formatter);
            dateList.add(formattedDate);
        }
//        System.out.println(dateList);
        return dateList;
    }

    /**
     * 获取固定间隔时刻集合
     *
     * @param start    开始时间
     * @param end      结束时间
     * @param interval 时间间隔(单位：分钟)
     * @return
     */
    public static List<String> getIntervalTimeList(String start, String end, int interval) {
        Date startDate = convertString2Date("HH:mm:ss", start);
        Date endDate = convertString2Date("HH:mm:ss", end);
        List<String> list = new ArrayList<>();
        while (startDate.getTime() <= endDate.getTime()) {
            list.add(convertDate2String("HH:mm:ss", startDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MINUTE, interval);
            if (calendar.getTime().getTime() > endDate.getTime()) {
                if (!startDate.equals(endDate)) {
                    list.add(convertDate2String("HH:mm:ss", endDate));
                }
                startDate = calendar.getTime();
            } else {
                startDate = calendar.getTime();
            }

        }
        return list;
    }

    public static Date convertString2Date(String format, String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDate2String(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    // 获取当前月的第一天
    public static String fristMonthDay(){
        LocalDate date = LocalDate.now();
        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth()); // 获取当前月的第一天
        return firstDay.toString();
    }

    // 获取当前月的最后一天
    public static String lastMonthDay(){
        LocalDate date = LocalDate.now();
        LocalDate firstDay = date.with(TemporalAdjusters.lastDayOfMonth()); // 获取当前月的最后一天
        return firstDay.toString();
    }
    public static void main(String[] args) throws Exception {

//        String nowDate = "10:00:01";//LocalDate.now().toString();
//        String beforDate = "23:59:59";//LocalDate.now().plusDays(1).toString();
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//        Date startDate = convertString2Date("HH:mm:ss", nowDate);
//        Date e = convertString2Date("HH:mm:ss", beforDate);
//
//        System.out.println(simpleDateFormat.parse(simpleDateFormat.format(e)).getTime());
//        System.out.println(simpleDateFormat.parse(simpleDateFormat.format(startDate)).getTime());
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        System.out.println(dateFormat.format(dateFormat.parse("2024-03-10 00:00:00")));
//        System.out.println(simpleDateFormat.format(dateFormat.parse("2024-03-10 00:00:00").getTime()));
////        System.out.println(simpleDateFormat.parse(dateFormat.format(dateFormat.parse("2024-03-10 00:00:00"))));
//
//        System.out.println(getIntervalTimeList(nowDate, beforDate, 40));
//
//        System.out.println(fristMonthDay());
//        System.out.println(lastMonthDay());

        System.out.println(getPreMonth(-2));
        System.out.println(getPreMonth(-1));
        System.out.println(getPreMonth(0));
        List<Integer> months = BladeDateUtils.getMonths(3);
        List<String> monthList = Lists.newArrayList();
        months.forEach(m -> {
            monthList.add(m + "月");
        });
        System.out.println(JSON.toJSONString(monthList));

    }
}



