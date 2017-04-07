package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 *
 *
 */
@Repository
public class ProjectDAOImpl implements ProjectDAO {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Project> getProjects(String userName) {
    Session session = sessionFactory.getCurrentSession();

    Query allProjectsQuery = session.createQuery("from Project where user = :userName");
    allProjectsQuery.setParameter("userName", userName);

    List<Project> allProjects = allProjectsQuery.getResultList();

    return allProjects;
  }

  @Override
  public void addProject(Project project) {
    Session session = sessionFactory.getCurrentSession();

    session.save(project);
  }

  @Override
  public Project getProject(String userName, int projectId) {
    Session session = sessionFactory.getCurrentSession();

    Query projectQuery = session.createQuery("from Project where user = :userName AND id = :id");
    projectQuery.setParameter("userName", userName);
    projectQuery.setParameter("id", projectId);

    List<Project> projects = projectQuery.getResultList();

    if (projects.size() == 0) {
      return null;
    }

    return projects.get(0);
  }
}
