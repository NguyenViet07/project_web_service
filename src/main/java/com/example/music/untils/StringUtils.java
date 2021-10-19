package com.example.music.untils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


  public static boolean isEmpty(String input) {
    if (input == null || input.isEmpty()) {
      return true;
    }
    return false;
  }

  public static String extractMimeType(final String encoded) {
    final Pattern mime = Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*");
    final Matcher matcher = mime.matcher(encoded);
    if (!matcher.find())
      return "";
    return matcher.group(1).toLowerCase();
  }

  public static boolean checkPassword(String password) {
    return password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,32})");
  }

  public static boolean isImageFile(String encoded) {
    String mimeStr = extractMimeType(encoded);
    if (isEmpty(mimeStr))
      return false;
    if (mimeStr.indexOf("image/") > -1)
      return true;
    return false;
  }
}
