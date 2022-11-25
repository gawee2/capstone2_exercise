package com.mju.exercise.OpenMatch;

import com.mju.exercise.StatusEnum.Status;

public interface Filtering {

    void filterJoin(Status.FilterTypeJoin filterTypeJoin);
    void filterDistance(Status.FilterTypeDistance filterTypeDistance);
    void filterDay(Status.FilterTypeDay filterTypeDay);

}
