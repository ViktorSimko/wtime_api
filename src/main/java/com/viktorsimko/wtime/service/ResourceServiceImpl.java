package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.ResourceDAO;
import com.viktorsimko.wtime.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ResourceServiceImpl<R extends Resource> implements ResourceService<R> {

  @Autowired
  protected ResourceDAO<R> resourceDAO;

  @Override
  public List<R> getResources(String userName) {
    return resourceDAO.getResources(userName);
  }

  @Override
  public R addResource(R resource) {
    return resourceDAO.addResource(resource);
  }

  @Override
  public R getResource(String userName, int resourceId) {
    return resourceDAO.getResource(userName, resourceId);
  }

  @Override
  public R updateResource(String userName, int resourceId, R updatedResource) {
    return resourceDAO.updateResource(userName, resourceId, updatedResource);
  }

  @Override
  public R deleteResource(String userName, int resourceId) {
    return resourceDAO.deleteResource(userName, resourceId);
  }

}
