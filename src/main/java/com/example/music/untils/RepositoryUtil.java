package com.example.music.untils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class RepositoryUtil {

  public static <T> boolean getIsActiveByValue(T value) {
    if(value == null)
      return false;
    if (value instanceof Boolean)
      return (Boolean) value;
    else if (value instanceof Long) {
      if(((Long) value).intValue() == 1)
        return true;
      else
        return false;
    }
    else if (value instanceof BigInteger) {
      if(((BigInteger) value).intValue() == 1)
        return true;
      else
        return false;
    }
    else {
      if((Integer) value == 1)
        return true;
      else
        return false;
    }
  }
  public static <T> Integer getIntegerByValue(T value) {
    if(value == null)
      return null;
    return Integer.parseInt(String.valueOf(value));
  }
  public static <T> String getStringByValue(T value) {
    if(value == null)
      return null;
    return String.valueOf(value);
  }
  public static <T> String getTimestampToString(T value, DateFormat dateFormat) {
    if(value == null)
      return null;
    Timestamp time = (Timestamp) value;
    return dateFormat.format(time);
  }
  public static <T> Date getTimestamp(T value) {
    if (value == null)
      return null;
    Timestamp time = (Timestamp) value;
    return new Date(time.getTime());
  }

  public static <T> Long getLongFromBigInteger(T value) {
    if (value == null)
      return null;
    return Long.parseLong(String.valueOf(value));
  }

  public static Date getDateFromTimeStr(String value) {
    if (StringUtils.isEmpty(value))
      return null;

    long time = Long.parseLong(value);
    return new Date(time);
  }
}
