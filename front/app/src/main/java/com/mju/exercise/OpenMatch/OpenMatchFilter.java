package com.mju.exercise.OpenMatch;

import com.mju.exercise.StatusEnum.Status;

import java.time.LocalDateTime;

public interface OpenMatchFilter {

    void setFilter(Status.FilterTypeJoin filterTypeJoin,
                   Status.FilterTypeDistance filterTypeDistance,
                   Status.FilterTypeDay filterTypeDay,
                   Status.DistanceDiff distanceDiff,
                   Status.FavDayType favDayType,
                   LocalDateTime localDateTime);
}
