package com.viktorsimko.wtime.controller;

import org.springframework.web.bind.annotation.*;

/**
 * A controller to respond with some information to requests at the root endpoint.
 */
@RestController
public class RootController {

  /**
   * Returns a response with information about the API.
   *
   * @return information about the web service
   */
  @GetMapping("/")
  public String getRoot() {
    return "WTime";
  }

}
