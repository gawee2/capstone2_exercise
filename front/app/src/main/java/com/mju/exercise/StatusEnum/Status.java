package com.mju.exercise.StatusEnum;

public class Status {

    public static enum Request{
        GET,
        POST;
    }

    public static enum FilterType{
        JOIN_CAN,
        JOIN_ALL,

        DISTANCE_NEAR,
        DISTANCE_DIFFERENCE,

        DAY_NEAR,
        DAY_FAV,
        DAY_PICK
    }

    public static enum DayType{
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN
    }
}
