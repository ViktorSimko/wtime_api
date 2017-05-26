package com.viktorsimko.wtime.test;

import com.viktorsimko.wtime.model.Resource;

import java.lang.reflect.Field;

public class TestUtil {

  public static void setIdOnResource(Resource resource, int id) throws Exception {
    Field idField = Resource.class.getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(resource, id);
  }

}
