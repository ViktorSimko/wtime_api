package com.viktorsimko.wtime.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by simkoviktor on 2017. 03. 17..
 */

@RestController
@RequestMapping("/user")
public class SecurityController {

  @Autowired
  private JdbcUserDetailsManager userDetailsManager;

  @RequestMapping("/exists/{userName}")
  public boolean userExists(@PathVariable("userName") String userName) {
    UserDetails userDetails = userDetailsManager.loadUserByUsername(userName);
    userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).forEach(System.out::println);
    return userDetailsManager.userExists(userName);
  }

  @RequestMapping("/register/{userName}/{password}")
  public String register(@PathVariable("userName") String userName,
                         @PathVariable("password") String password) {

    GrantedAuthority user = (GrantedAuthority) () -> "ROLE_USER";

    userDetailsManager.createUser(new User(userName, password, Collections.singletonList(user)));

    return "Registration successful";
  }
}
