package com.ding.running.Common;

/**
 * @ClassName Const
 * @Author Leoren
 * @Date 2019/5/6 10:44
 * Description :
 * @Version v1.0
 */
public class Const {

    public interface AttractionType{
        public int UNKNOWN_TYPE = -1;
        public int ATTRACTION_TYPE = 1;
        public int HOTEL_TYPE = 2;
        public int FOOD_TYPE = 3;
        public int GOOD_TYPE = 4;
    }

    public interface MajorID{
        public int ATTRACTION_MAJOR_ID = 1001;
        public int HOTEL_MAJOR_ID = 1002;
        public int FOOD_MAJOR_ID = 1003;
        public int GOOD_MAJOR_ID = 1004;
    }


    public static final String FINDPEOPLEITEM = "FINDPEOPLEITEM";
}
