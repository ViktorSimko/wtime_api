package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by simkoviktor on 2017. 03. 15..
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
}
