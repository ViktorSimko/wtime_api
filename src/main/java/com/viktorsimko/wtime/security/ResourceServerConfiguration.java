package com.viktorsimko.wtime.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * Created by simkoviktor on 2017. 03. 16..
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  private static final String RESOURCE_ID = "wtime_api";

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(RESOURCE_ID).stateless(true);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
      .anonymous().disable()
      .requestMatchers().antMatchers("/projects/**", "/tasks/**", "/work_intervals/**")
      .and().authorizeRequests()
      .antMatchers("/projects/**", "/tasks/**", "/work_intervals/**").access("hasRole('USER')")
      .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
  }
}
