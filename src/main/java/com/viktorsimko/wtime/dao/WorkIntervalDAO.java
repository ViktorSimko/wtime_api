package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.WorkInterval;

import java.util.List;

/**
 * Created by simkoviktor on 2017. 04. 23..
 */
public interface WorkIntervalDAO extends ResourceDAO<WorkInterval> {

  List<WorkInterval> getWorkIntervals(String userName, int taskId);

}
