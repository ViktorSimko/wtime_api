package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.WorkIntervalDAO;
import com.viktorsimko.wtime.model.WorkInterval;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by simkoviktor on 2017. 04. 23..
 */
@Service
public class WorkIntervalServiceImpl extends ResourceServiceImpl<WorkInterval> implements WorkIntervalService {

  @Override
  public List<WorkInterval> getWorkIntervals(String userName, int taskId) {

    return ((WorkIntervalDAO) resourceDAO).getWorkIntervals(userName, taskId);
  }
}
