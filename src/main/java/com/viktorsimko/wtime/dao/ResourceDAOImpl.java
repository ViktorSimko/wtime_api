package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional
public abstract class ResourceDAOImpl<R extends Resource> implements ResourceDAO<R> {

  @Autowired
  protected SessionFactory sessionFactory;

  String getResourceClassName() {
    String fullResourceClassName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
    String[] fullResourceClassNameComponents = fullResourceClassName.split("\\.");
    String resourceClassName = fullResourceClassNameComponents[fullResourceClassNameComponents.length - 1];
    return resourceClassName;
  }

  @Override
  public R addResource(R resource) {
    Session session = sessionFactory.getCurrentSession();

    session.save(resource);

    return resource;
  }

  @Override
  public List<R> getResources(String userName) {
    Session session = sessionFactory.getCurrentSession();

    Query allResourcesQuery = session.createQuery("from " + getResourceClassName() + " where user = :userName");
    allResourcesQuery.setParameter("userName", userName);

    List<R> allResources = allResourcesQuery.getResultList();

    return allResources;
  }

  @Override
  public R getResource(String userName, int resourceId) {
    Session session = sessionFactory.getCurrentSession();

    Query resourceQuery = session.createQuery("from " + getResourceClassName() + " where user = :userName and id = :resourceId");
    resourceQuery.setParameter("userName", userName);
    resourceQuery.setParameter("resourceId", resourceId);

    List<R> resourceList = resourceQuery.getResultList();

    if (resourceList.size() == 0) {
      return null;
    }

    return resourceList.get(0);
  }

  @Override
  public R deleteResource(String userName, int resourceId) {
    R resource = getResource(userName, resourceId);

    sessionFactory.getCurrentSession().delete(resource);

    return resource;
  }
}
