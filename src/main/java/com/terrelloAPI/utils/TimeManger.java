package com.terrelloAPI.utils;

public class TimeManger {

        public static String getTimestamp() {
            return new java.text.SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new java.util.Date());
        }
        public static String getSimpleTimestamp() {
            return Long.toString(System.currentTimeMillis());

        }


}
