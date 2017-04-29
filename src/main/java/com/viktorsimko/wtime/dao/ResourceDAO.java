package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Resource;

import java.util.Collection;

/**
 * Interface for accessing and manipulating resources in the database.
 */
public interface ResourceDAO<R extends Resource> {

  /**
   * Adds the given {@code Resource} to the database.
   *
   * @param resource the {@code Resource} to save
   * @return the saved {@code Resource} if the save was successful, else null
   */
  R addResource(R resource);

  /**
   * Returns the resources for the given user from the database.
   *
   * @param userName the owner of the resources
   * @return a list of the resources of the user
   */
  Collection<R> getResources(String userName);

  /**
   * Get the {@code Resource} for a given id.
   *
   * @param userName the owner of the {@code Resource}
   * @param resourceId the {@code id} of the {@code Resource}
   * @return the {@code Resource} associated with {@code userName} and {@code resourceId},
   *         or null if a {@code Resource} with the given {@code resourceId} and {@code userName} not exists
   */
  R getResource(String userName, int resourceId);

  /**
   * Update the {@code Resource} with the given {@code id} using the given information.
   *
   * @param userName the owner of the {@code Resource}
   * @param resourceId the {@code id} of the {@code Resource}
   * @param updatedResource the information to update the {@code Resource} with
   * @return the updated {@code Resource}, if the update was successful, else null
   */
  R updateResource(String userName, int resourceId, R updatedResource);

  /**
   * Deletes the given {@code Resource}.
   *
   * @param userName the owner of the {@code Resource}
   * @param resourceId the {@code id} of the {@code Resource}
   * @return the deleted {@code Resource} if the deletion was successful, else null
   */
  R deleteResource(String userName, int resourceId);

}
