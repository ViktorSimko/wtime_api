package com.viktorsimko.wtime.integration_test;

import com.viktorsimko.wtime.configuration.WTimeConfiguration;
import com.viktorsimko.wtime.configuration.WTimeInitializer;
import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.security.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Created by simkoviktor on 2017. 04. 23..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
  WTimeConfiguration.class,
  WTimeInitializer.class,
  AuthorizationServerConfiguration.class,
  CORSEnabledAuthorizationServerSecurityConfiguration.class,
  OAuth2SecurityConfiguration.class,
  ResourceServerConfiguration.class,
  TestDataSourceConfiguration.class
})
@Transactional()
@WebAppConfiguration
public class ProjectControllerIT {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private OAuthHelper oauthHelper;

  @Autowired
  private SessionFactory sessionFactory;

  private MockMvc mockMvc = null;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                             .apply(springSecurity()).build();
  }

  @Test
  public void test__web_app_context__contains_the_project_controller() {
    assertNotNull(webApplicationContext.getBean("projectController"));
  }

  @Test
  public void test__POST_projects__adds_the_posted_project_to_the_database_and_returns_it() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    Project project = new Project();
    project.setUser("test_user");
    project.setTitle("Project one");
    project.setDescription("The first project description...");
    project.setHourlyWage(1000);

    ObjectMapper mapper = new ObjectMapper();

    String jsonProject = mapper.writeValueAsString(project);

    mockMvc.perform(post("/projects").with(bearerToken).contentType("application/json").content(jsonProject))
           .andDo(print())
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.title", is(project.getTitle())))
           .andExpect(jsonPath("$.description", is(project.getDescription())))
           .andExpect(jsonPath("$.hourlyWage", is(project.getHourlyWage())));

    List<Project> projectList = sessionFactory.getCurrentSession().createQuery("from Project").getResultList();

    assertEquals(1, projectList.size());

    Project firstProject = projectList.get(0);

    assertEquals(project.getTitle(), firstProject.getTitle());
    assertEquals(project.getDescription(), firstProject.getDescription());
    assertEquals(project.getHourlyWage(), firstProject.getHourlyWage());
  }

  @Test
  public void test__GET_projects__returns_two_projects__when_there_are_two_projects() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    Project project1 = new Project();
    project1.setTitle("Project one");
    project1.setUser("test_user");
    project1.setDescription("project 1 description");
    project1.setHourlyWage(1000);
    sessionFactory.getCurrentSession().save(project1);

    Project project2 = new Project();
    project2.setTitle("Project two");
    project2.setUser("test_user");
    project2.setDescription("project 2 description");
    project2.setHourlyWage(1500);
    sessionFactory.getCurrentSession().save(project2);

    mockMvc.perform(get("/projects").with(bearerToken))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isArray())
           .andExpect(jsonPath("$", hasSize(2)))
           .andExpect(jsonPath("$[0].title", is(project1.getTitle())))
           .andExpect(jsonPath("$[0].description", is(project1.getDescription())))
           .andExpect(jsonPath("$[0].hourlyWage", is(project1.getHourlyWage())))
           .andExpect(jsonPath("$[1].title", is(project2.getTitle())))
           .andExpect(jsonPath("$[1].description", is(project2.getDescription())))
           .andExpect(jsonPath("$[1].hourlyWage", is(project2.getHourlyWage())));
  }

  @Test
  public void test__GET_projects__returns_an_empty_array__when_there_are_no_projects() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    mockMvc.perform(get("/projects").with(bearerToken))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isArray())
           .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  public void test__GET_projects_id__returns_the_project_with_the_id__when_it_is_found() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    Project project1 = new Project();
    project1.setTitle("Project one");
    project1.setUser("test_user");
    project1.setDescription("project 1 description");
    project1.setHourlyWage(1000);
    sessionFactory.getCurrentSession().save(project1);

    mockMvc.perform(get("/projects/{projectId}", project1.getId()).with(bearerToken))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.title", is(project1.getTitle())))
           .andExpect(jsonPath("$.description", is(project1.getDescription())))
           .andExpect(jsonPath("$.hourlyWage", is(project1.getHourlyWage())));
  }

  @Test
  public void test__GET_projects_id__returns_404__when_it_is_not_found() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    mockMvc.perform(get("/projects/{projectId}", "1").with(bearerToken))
           .andDo(print())
           .andExpect(status().isNotFound());
  }

  @Test
  public void test__PATCH_projects_id__updates_the_project_with_the_correct_information__when_the_id_is_found() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    Project project1 = new Project();
    project1.setTitle("Project one");
    project1.setUser("test_user");
    project1.setDescription("project 1 description");
    project1.setHourlyWage(1000);
    sessionFactory.getCurrentSession().save(project1);

    Project patch = new Project();
    patch.setTitle("Updated title");
    patch.setDescription("Updated description");
    patch.setHourlyWage(1100);

    String jsonPatch = new ObjectMapper().writeValueAsString(patch);

    mockMvc.perform(patch("/projects/{projectId}", project1.getId()).with(bearerToken).contentType("application/json").content(jsonPatch))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.title", is(patch.getTitle())))
           .andExpect(jsonPath("$.description", is(patch.getDescription())))
           .andExpect(jsonPath("$.hourlyWage", is(patch.getHourlyWage())));

    List<Project> projectList = sessionFactory.getCurrentSession().createQuery("from Project").getResultList();

    assertEquals(1, projectList.size());

    Project firstProject = projectList.get(0);

    assertEquals(patch.getTitle(), firstProject.getTitle());
    assertEquals(patch.getDescription(), firstProject.getDescription());
    assertEquals(patch.getHourlyWage(), firstProject.getHourlyWage());
  }

  @Test
  public void test__PATCH_projects_id__returns_404__when_the_id_is_not_found() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    Project patch = new Project();
    patch.setTitle("Updated title");
    patch.setDescription("Updated description");
    patch.setHourlyWage(1100);

    String jsonPatch = new ObjectMapper().writeValueAsString(patch);

    mockMvc.perform(patch("/projects/{projectId}", 1).with(bearerToken).contentType("application/json").content(jsonPatch))
           .andDo(print())
           .andExpect(status().isNotFound());
  }

  @Test
  public void test__DELETE_projects_id__deletes_the_project_and_returns_it__when_the_id_is_found() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    Project project1 = new Project();
    project1.setTitle("Project one");
    project1.setUser("test_user");
    project1.setDescription("project 1 description");
    project1.setHourlyWage(1000);
    sessionFactory.getCurrentSession().save(project1);

    mockMvc.perform(delete("/projects/{projectId}", project1.getId()).with(bearerToken))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.title", is(project1.getTitle())));

    List<Project> projectList = sessionFactory.getCurrentSession().createQuery("from Project").getResultList();

    assertEquals(0, projectList.size());
  }

  @Test
  public void test__DELETE_projects_id__returns_400__when_the_id_is_not_found() throws Exception {
    RequestPostProcessor bearerToken = oauthHelper.bearerToken("wtime-client");

    mockMvc.perform(delete("/projects/{projectId}", 1).with(bearerToken))
           .andDo(print())
           .andExpect(status().isNotFound());
  }

}
