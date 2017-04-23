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
 * Class for accessing and manipulating the projects in the database.
 */
@Repository
public class ProjectDAOImpl extends ResourceDAOImpl<Project> implements ProjectDAO {

  @Override
  public Project updateResource(String userName, int projectId, Project updatedProjectInfo) {
    Project projectToUpdate = getResource(userName, projectId);

    if (projectToUpdate == null) {
      return null;
    }

    projectToUpdate.setTitle(updatedProjectInfo.getTitle());

    sessionFactory.getCurrentSession().save(projectToUpdate);

    return projectToUpdate;
  }
}
