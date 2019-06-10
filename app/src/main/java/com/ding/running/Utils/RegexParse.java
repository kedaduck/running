package com.ding.running.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Leoren
 * @Date 2019/1/13 12:58
 *
 * 字符串格式正则匹配
 */
public class RegexParse {

    public static boolean isTeleNumber(String tel){
        String pattern = "0?(13|14|15|18|17)[0-9]{9}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    public static boolean isIdCard(String idNum){
        String pattern = "\\d{17}[\\d|x]|\\d{15}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(idNum);
        return m.matches();
    }

}
