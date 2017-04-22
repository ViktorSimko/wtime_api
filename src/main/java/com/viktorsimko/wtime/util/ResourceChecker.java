package com.viktorsimko.wtime.util;

import com.viktorsimko.wtime.model.Resource;
import org.springframework.http.ResponseEntity;

import java.net.URI;

/**
 * Utility for checking if a resource is valid.
 */
public class ResourceChecker {

  /**
   * Checks if a resource is created, and if it is then returns it in a 200 response,
   * else returns a 404 response.
   *
   * @param resource The resource to check
   * @param <ResourceType> The type of the resource
   * @return The resource, if its valid
   */
  public static <ResourceType extends Resource> ResponseEntity<ResourceType> checkResource(ResourceType resource) {

    if (resource == null) {
      ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(resource);
  }

  /**
   * Checks if a resource is created, and if it is then returns it in a 201 response,
   * else returns a 404 response.
   *
   * @param resource The resource to check
   * @param <ResourceType> The type of the resource
   * @return The resource, if its valid
   */
  public static <ResourceType extends Resource> ResponseEntity<ResourceType> checkResourceCreated(ResourceType resource) {

    if (resource == null) {
      ResponseEntity.badRequest().build();
    }

    return ResponseEntity.created(URI.create("/projects/" + resource.getId())).body(resource);
  }
}
