package com.ding.running.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Leoren
 * @Date 2018/6/29 8:28
 * 正则匹配工具
 */
public class RegexUtil {

    public static final String TYPE_STRING_USERNAME = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+"  ;
    public static final String TYPE_STRING_PASSWORD = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+"  ;
    public static final String TYPE_STRING_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}"  ;
    public static final String TYPE_STRING_PHONE = "0?(13|14|15|18)[0-9]{9}"  ;
    public static final String TYPE_STRING_IDCARD = "\\d{17}[\\d|x]|\\d{15}";

    public static boolean isUsername(String str){
        Pattern pattern = Pattern.compile(TYPE_STRING_USERNAME);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isPassword(String str){
        Pattern pattern = Pattern.compile(TYPE_STRING_PASSWORD);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static boolean isEmail(String str){
        Pattern pattern = Pattern.compile(TYPE_STRING_EMAIL);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static boolean isPhone(String str){
        Pattern pattern = Pattern.compile(TYPE_STRING_PHONE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isIDNumber(String idCardNumber) {
        Pattern r = Pattern.compile(TYPE_STRING_IDCARD);
        Matcher m = r.matcher(idCardNumber);
        return m.matches();
    }
}
