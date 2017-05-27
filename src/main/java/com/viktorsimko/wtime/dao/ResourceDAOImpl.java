package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A generic resourceDAO implementation.
 *
 * @param <R> the class of the resource
 */
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
  public Collection<R> getResources(String userName) {
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
  public R updateResource(String userName, int resourceId, R updatedResource) {
    R resource = getResource(userName, resourceId);

    Method[] methods = resource.getClass().getMethods();

    List<Method> setters = Arrays.stream(methods).filter(method -> method.getName().matches("^set")).collect(Collectors.toList());

    for (Method setter: setters) {
      String getterName = setter.getName().replace("set", "get");
      try {
        Method getter = resource.getClass().getMethod(getterName);
        Object propertyValue = getter.invoke(updatedResource);

        if (propertyValue != null) {
          setter.invoke(resource, propertyValue);
        }
      } catch (Exception exc) {
          // log out the exception
      }
    }

    sessionFactory.getCurrentSession().save(resource);

    return resource;
  }

  @Override
  public R deleteResource(String userName, int resourceId) {
    R resource = getResource(userName, resourceId);

    if (resource == null) {
      return null;
    }

    sessionFactory.getCurrentSession().delete(resource);

    return resource;
  }
}
