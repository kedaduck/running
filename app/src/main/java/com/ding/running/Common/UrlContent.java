package com.ding.running.Common;

/**
 * @ClassName UrlContent
 * @Author Leoren
 * @Date 2019/5/6 9:13
 * Description :
 * @Version v1.0
 */
public class UrlContent {


    private static final String BASE_MODEL = "http://192.168.137.1:8080/play_war";


    private static final String HOTEL_MODEL = "/hotel/";
    public static final String FIND_ALL_HOTEL = BASE_MODEL + HOTEL_MODEL + "find_all.do";

    private static final String GOOD_MODEL = "/store/";
    public static final String FIND_ALL_GOOD = BASE_MODEL + GOOD_MODEL + "find_all.do";

    private static final String RESTAURANT_MODEL = "/restaurant/";
    public static final String FIND_ALL_RESTAURANT = BASE_MODEL + RESTAURANT_MODEL + "find_all.do";

    private static final String ATTRACTION_MODEL = "/attraction/";
    public static final String FIND_ALL_ATTRACTION = BASE_MODEL + ATTRACTION_MODEL + "find_all.do";

    private static final String FIND_PEOPLE_MODEL = "/find_people/";
    public static final String FIND_ALL_FINDING = BASE_MODEL + FIND_PEOPLE_MODEL + "find_all.do";
    public static final String CREATE_FINDING_PUBLISH = BASE_MODEL + FIND_PEOPLE_MODEL + "create_publish.do";

    private static final String USER_MODEL = "/user/";
    public static final String USER_REGISTER = BASE_MODEL + USER_MODEL + "register.do";
    public static final String USER_LOGIN = BASE_MODEL + USER_MODEL + "login.do";
    public static final String USER_CHANGE_USERNAME = BASE_MODEL + USER_MODEL + "change_username.do";
    public static final String USER_CHANGE_PASSWORD = BASE_MODEL + USER_MODEL + "change_password.do";
}
