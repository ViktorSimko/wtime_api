package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.WorkInterval;

import java.util.Collection;

/**
 * Created by simkoviktor on 2017. 04. 23..
 */
public interface WorkIntervalDAO extends ResourceDAO<WorkInterval> {

  Collection<WorkInterval> getWorkIntervals(String userName, int taskId);

}
