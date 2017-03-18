package com.viktorsimko.wtime.test;

import com.viktorsimko.wtime.configuration.WTimeConfiguration;
import com.viktorsimko.wtime.configuration.WTimeInitializer;
import com.viktorsimko.wtime.security.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by simkoviktor on 2017. 03. 18..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {
  WTimeConfiguration.class,
  AuthorizationServerConfiguration.class,
  MethodSecurityConfig.class,
  OAuth2SecurityConfiguration.class,
  ResourceServerConfiguration.class
})
public class ProjectsControllerTest {
  @Autowired
  WebApplicationContext webApplicationContext;

  @Autowired
  MockHttpSession session;

  @Autowired
  MockHttpServletRequest request;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @WithMockUser
  public void getRoot() throws Exception {
    mockMvc
      .perform(get("/projects")
      .session(session)
      .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
  }
}
