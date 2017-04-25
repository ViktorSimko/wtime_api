package com.viktorsimko.wtime.integration_test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

/**
 * Created by simkoviktor on 2017. 04. 25..
 */
@Configuration
public class TestDataSourceConfiguration {

  @Primary
  @Bean(destroyMethod = "close")
  public ComboPooledDataSource dataSource() throws Exception {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setDriverClass("com.mysql.jdbc.Driver");
    dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/wtime_test?useSSL=false");
    dataSource.setUser("wtime_admin");
    dataSource.setPassword("wtime_admin");
    dataSource.setMinPoolSize(3);
    dataSource.setMaxPoolSize(20);
    dataSource.setMaxIdleTime(30000);
    return dataSource;
  }

  @Primary
  @Bean
  public Properties hibernateProperties() {
    return new Properties() {
      {
        setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        setProperty("hibernate.show_sql", "true");
        setProperty("hibernate.hbm2ddl.auto", "create-drop");
      }
    };
  }
}
