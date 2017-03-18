package com.viktorsimko.wtime.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by simkoviktor on 2017. 03. 15..
 */
@RestController
public class RootController {

  @GetMapping("/")
  public String getRoot() {
    return "WTime";
  }

}
