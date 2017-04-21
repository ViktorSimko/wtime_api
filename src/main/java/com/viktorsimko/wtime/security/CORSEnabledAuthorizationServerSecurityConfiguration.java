package com.viktorsimko.wtime.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSEnabledAuthorizationServerSecurityConfiguration extends AuthorizationServerSecurityConfiguration {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.addFilterBefore(new CorsFilter(corsConfigurationSource()), ChannelProcessingFilter.class);
  }

  private CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("POST");
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
