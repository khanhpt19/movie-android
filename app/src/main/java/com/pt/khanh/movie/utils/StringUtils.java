package com.pt.khanh.movie.utils;

public class StringUtils {
    public static String appendToResponse() {
        return String.format("%s%s%s", Constants.APPEND_CREDITS,
                Constants.COMMA, Constants.APPEND_VIDEOS);
    }

    public static String getUrlImage(String posterPath) {
        return String.format("%s%s", Constants.BASE_URL_IMAGE, posterPath);
    }

    public static String getQuery(String... args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.trim().length() == 0;
    }
}
