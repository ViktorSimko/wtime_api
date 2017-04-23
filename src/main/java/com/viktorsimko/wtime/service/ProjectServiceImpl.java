package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.dao.ResourceDAO;
import com.viktorsimko.wtime.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A service for managing project resources.
 */
@Service
public class ProjectServiceImpl extends ResourceServiceImpl<Project> implements ProjectService {

}
