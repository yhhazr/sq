package com.sz7road.web.common.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {
    public static String substring(String s, int length) {
        String returnString = "";
        try {
            byte[] bytes = s.getBytes("Unicode");
            int n = 0; //表示当前的字节数
            int i = 2; //要截取的字节数，从第3个字节开始
            for (; i < bytes.length && n < length; i++) {
                //奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
                if (i % 2 == 1) {
                    n++; // 在UCS2第二个字节时n加1
                } else {
                    // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                    if (bytes[i] != 0) {
                        n++;
                    }
                }
            }
            //如果i为奇数时，处理成偶数
            if (i % 2 == 1) {
                //该UCS2字符是汉字时，去掉这个截一半的汉字
                if (bytes[i - 1] != 0) {
                    i = i - 1;
                } else {
                    i = i + 1;//该UCS2字符是字母或数字，则保留该字符
                }
            }
            returnString = new String(bytes, 0, i, "Unicode");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public static int getStringByteLength(String s) throws Exception {
        int length = 0;
        byte[] bytes = s.getBytes("Unicode");
        length = bytes.length;
        return length;
    }

    //为json转义特殊字符
    public static String escapeCodeForJson(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
            case '\"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '/':
                sb.append("\\/");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }

    //修改url字符串为http://开头
    public static String editUrlString(String url) {
        if (url == null) {
            return null;
        }
        String urlPrefix = url.length() < 7 ? "" : url.substring(0, 7).toLowerCase();

        return "http://".equals(urlPrefix) ? url : "http://" + url;
    }

}
