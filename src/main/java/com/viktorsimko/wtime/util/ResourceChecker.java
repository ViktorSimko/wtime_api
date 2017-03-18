package com.viktorsimko.wtime.util;

import com.viktorsimko.wtime.exception.ResourceNotFoundException;

/**
 * Created by simkoviktor on 2017. 03. 18.
 *
 * Utility for checking if a resource is valid
 */
public class ResourceChecker {

  /**
   * Checks if the resource is valid, and if it is then returns it,
   * else a RuntimeException is thrown
   *
   * @param resource The resource to check
   * @param <ResourceType> The type of the resource
   * @return The resource, if its valid
   */

  public static <ResourceType> ResourceType checkResource(ResourceType resource) {

    if (resource == null) {
      throw new ResourceNotFoundException();
    }

    return resource;
  }

}
