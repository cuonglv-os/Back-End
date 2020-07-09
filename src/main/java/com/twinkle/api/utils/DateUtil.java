package com.twinkle.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Strings;

import lombok.extern.log4j.Log4j2;

/**
 * @author cuonglv
 *
 */
@Log4j2
public class DateUtil {
    public static Date stringToDate(String dateString) {
        if (Strings.isNullOrEmpty(dateString))
            return null;

        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
        	log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public static Date stringToDateWithSeconds(String dateString) {
        if (Strings.isNullOrEmpty(dateString))
            return null;

        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
        	log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public static String dateToString(Date date) {
        if (date == null)
            return null;

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String dateToString(Date date, String format) {
        if (date == null)
            return null;

        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToString(Long millis) {
        Date date = new Date(millis);

        return dateToString(date);
    }

    public static float diffBetweenTwoDate(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null)
            return -1;

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        float diff = (float)diffInMillies/(1000*60*60*24);

        return diff;
    }

}
