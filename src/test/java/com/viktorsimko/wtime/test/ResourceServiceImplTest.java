package com.viktorsimko.wtime.test;

import com.viktorsimko.wtime.dao.ResourceDAO;
import com.viktorsimko.wtime.model.Resource;
import com.viktorsimko.wtime.service.ResourceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceImplTest {

  @InjectMocks
  private ResourceServiceImpl resourceService = new ResourceServiceImpl();

  @Mock
  private ResourceDAO resourceDAO;

  private String userName = "johndoe";
  private int resourceId = 0;

  @Test
  public void test__getResources__returns_what_ResourceDAO_getResources_with_the_same_username_as_argument__returns() {
    Resource resource1 = new Resource();
    resource1.setUser("test_user1");
    Resource resource2 = new Resource();
    resource2.setUser("test_user2");

    when(resourceDAO.getResources(userName)).thenReturn(Arrays.asList(resource1, resource2));

    Collection<Resource> resources = resourceService.getResources(userName);

    assertThat(resources, hasSize(2));
    assertThat(resources, contains(resource1, resource2));
  }

  @Test
  public void test__addResource__returns_what_resourceDAO_addResource_returns_with_the_same_resource_as_argument() {
    Resource resource = new Resource();
    resource.setUser("test_user");

    when(resourceDAO.addResource(resource)).thenReturn(resource);
    Resource returnedResource = resourceService.addResource(resource);

    assertTrue(returnedResource == resource);
  }

  @Test
  public void test__getResource__returns_what_resourceDAO_getResource_with_the_same_username_and_resource_id_returns() {
    Resource resource = new Resource();
    resource.setUser("test_user");

    when(resourceDAO.getResource(userName, resourceId)).thenReturn(resource);

    Resource returnedResource = resourceService.getResource(userName, resourceId);

    assertTrue(returnedResource == resource);
  }

  @Test
  public void test__updateResource__returns_what_resourceDAO_updateResource_with_the_same_username_resource_id_and_updatedResource_returns() {
    Resource updatedResource = new Resource();
    updatedResource.setUser("test_user");

    when(resourceDAO.updateResource(userName, resourceId, updatedResource)).thenReturn(updatedResource);

    Resource returnedResource = resourceService.updateResource(userName, resourceId, updatedResource);

    assertTrue(returnedResource == updatedResource);
  }

  @Test
  public void test__deleteResource__returns_what_resourceDAO_deleteResource_with_the_same_username_resource_id_returns() {
    Resource resource = new Resource();
    resource.setUser("test_user");

    when(resourceDAO.deleteResource(userName, resourceId)).thenReturn(resource);

    Resource returnedResource = resourceService.deleteResource(userName, resourceId);

    assertTrue(returnedResource == resource);
  }
}
