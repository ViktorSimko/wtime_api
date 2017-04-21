package com.viktorsimko.wtime.security;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * The configuration class for OAuth2.
 */
@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private ComboPooledDataSource dataSource;

  @Autowired
  private ClientDetailsService clientDetailsService;

  @Autowired
  private void globalUserDetails(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(jdbcUserDetailsManager());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("oauth/token").permitAll();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * Returns a {@code TokenStore} for storing authorization tokens.
   *
   * @return a {@code TokenStore}
   */
  @Bean
  public TokenStore tokenStore() {
    return new InMemoryTokenStore();
  }

  /**
   * Returns a configured {@code TokenStoreUserApprovalHandler} using the given {@code TokenStore}.
   *
   * @param tokenStore the {@code TokenStore} for the {@code TokenStoreUserApprovalHandler}
   * @return the configured {@code TokenStoreUserApprovalHandler}
   */
  @Bean
  @Autowired
  public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
    TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
    handler.setTokenStore(tokenStore);
    handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
    handler.setClientDetailsService(clientDetailsService);
    return handler;
  }

  /**
   * Configures and returns an {@code ApprovalStore}.
   *
   * @param tokenStore the {@code TokenStore} for the {@code ApprovalStore} to use
   * @return the configured {@code ApprovalStore}
   */
  @Bean
  @Autowired
  public ApprovalStore approvalStore(TokenStore tokenStore) {
    TokenApprovalStore store = new TokenApprovalStore();
    store.setTokenStore(tokenStore);
    return store;
  }

  /**
   * Configures and returns a {@code JdbcUserDetailsManager}.
   *
   * @return the configured {@code JdbcUserDetailsManager}
   */
  @Bean
  @Qualifier("userDetailsServiceBean")
  JdbcUserDetailsManager jdbcUserDetailsManager() {

    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
    userDetailsManager.setDataSource(dataSource);
    return userDetailsManager;
  }
}
