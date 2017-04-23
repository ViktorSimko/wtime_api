package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.WorkInterval;

/**
 * Created by simkoviktor on 2017. 04. 23..
 */
public class WorkIntervalDAOImpl extends ResourceDAOImpl<WorkInterval> implements WorkIntervalDAO {
  @Override
  public WorkInterval updateResource(String userName, int resourceId, WorkInterval updatedResourceInfo) {
    return null;
  }
}
