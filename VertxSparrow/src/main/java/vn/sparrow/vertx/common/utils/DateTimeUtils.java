package vn.sparrow.vertx.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/** Created by thuyenpt Date: 2019-11-21 */
public class DateTimeUtils {

  public static final String yyyyMMddHHmmssSSSFormat = "yyyy-MM-dd HH:mm:ss.SSS";
  public static final String yyyyMMddHHmmssFormat = "yyyy-MM-dd HH:mm:ss";
  public static final String yyyyMMddDateFormat = "yyyyMMdd";
  public static final String yyMMddDateFormat = "yyMMdd";
  public static final TimeZone GMT7TimeZone = TimeZone.getTimeZone("GMT+7");

  public static String convertTimeStamp2Date(long timeStamp, String format) {
    DateFormat dateFormat;
    if (format == null) {
      dateFormat = new SimpleDateFormat(yyyyMMddHHmmssSSSFormat);
    } else {
      dateFormat = new SimpleDateFormat(format);
    }
    return dateFormat.format(new Date(timeStamp));
  }

  public static Timestamp convertLong2Timestamp(long time) {
    return Timestamp.from(Instant.ofEpochMilli(time));
  }

  public static Date convertYYYYMMDDtoDate(String yyyyMMdd) {
    try {
      if (yyyyMMdd != null && yyyyMMdd.length() == 8) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddDateFormat);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(yyyyMMdd);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static long getCurrentMillisecond() {
    return Calendar.getInstance(GMT7TimeZone).getTimeInMillis();
  }

  public static String getCurrentTimestamp() {
    return convertTimeStamp2Date(getCurrentMillisecond(), yyyyMMddHHmmssFormat);
  }

  public static String getCurrentYYMMDD() {
    return convertTimeStamp2Date(getCurrentMillisecond(), yyMMddDateFormat);
  }

  public static long convertToMillisecondSafe(String dateStr, String format) {
    try {
      DateFormat dateFormat = new SimpleDateFormat(format);
      return dateFormat.parse(dateStr).getTime();
    } catch (Exception ex) {
      return 0;
    }
  }

  public static long convertTimestampToMillisecond(Instant instant) {
    return instant.atZone(GMT7TimeZone.toZoneId()).toInstant().toEpochMilli();
  }
}
