package com.mju.exercise.OpenMatch;

import com.mju.exercise.StatusEnum.Status;

public interface OpenMatchFilter {

    void setFilter(Status.FilterTypeJoin filterTypeJoin, Status.FilterTypeDistance filterTypeDistance, Status.FilterTypeDay filterTypeDay);
    void setDistanceDifference(Status.DistanceDiff diff);
    void setFavDay(Status.FavDayType favDayType);
}
