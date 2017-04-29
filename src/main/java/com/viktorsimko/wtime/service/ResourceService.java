package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.Resource;

import java.util.Collection;
import java.util.List;

/**
 * A service for managing and getting information about {@code Resource} objects.
 */
public interface ResourceService<R extends Resource> {

  /**
   * Returns the resources associated with the given user.
   *
   * @param userName the name of the current user
   * @return the resources associated with the given {@code userName}
   */
  Collection<R> getResources(String userName);

  /**
   * Returns the {@code resource} for the given {@code resourceId}.
   *
   * @param userName the {@code name} of the current user
   * @param resourceId the id of the {@code Resource} to get
   * @return the resources associated with the given {@code resourceId},
   *         or null if the given {@code id} is not associated with the {@code userName} or it is not exists
   */
  R getResource(String userName, int resourceId);

  /**
   * Saves the given {@code resource} to the database.
   *
   * @param resource the {@code resource} to save
   * @return the {@code Resource} that has been added to the database, or null if there were errors
   */
  R addResource(R resource);

  /**
   * Updates the {@code Resource}.
   *
   * @param userName the {@code name} of the current user
   * @param resourceId the id of the {@code Resource} to update
   * @param updatedResource the information to update the {@code Resource} with
   * @return the updated {@code Resource}, or null if the save was not successful
   */
  R updateResource(String userName, int resourceId, R updatedResource);

  /**
   * Deletes the given {@code Resource}.
   *
   * @param userName the {@code name} of the current user
   * @param resourceId the id of the {@code Resource} to delete
   * @return the deleted {@code Resource}, or null if the deletion was not successful
   */
  R deleteResource(String userName, int resourceId);

}
