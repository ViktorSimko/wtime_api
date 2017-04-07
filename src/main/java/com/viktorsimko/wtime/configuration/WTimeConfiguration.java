package com.viktorsimko.wtime.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;

/**
 * The configuration class for the project.
 * It sets up the necessary beans.
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.viktorsimko.wtime")
public class WTimeConfiguration {

  /**
   * This method sets up and returns a pooled data source for hibernate.
   *
   * @return the configured data source
   * @throws Exception if the connection properties are not correct
   */
  @Bean(destroyMethod = "close")
  public ComboPooledDataSource dataSource() throws Exception {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setDriverClass("com.mysql.jdbc.Driver");
    dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/wtime?useSSL=false");
    dataSource.setUser("wtime_admin");
    dataSource.setPassword("wtime_admin");
    dataSource.setMinPoolSize(3);
    dataSource.setMaxPoolSize(20);
    dataSource.setMaxIdleTime(30000);
    return dataSource;
  }

  /**
   * This method sets up and returns a configured session factory for hibernate.
   *
   * @return the configured session factory
   * @throws Exception if the creation of the data source {@code Bean} throws
   */
  @Bean
  public LocalSessionFactoryBean sessionFactory() throws Exception {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.viktorsimko.wtime.model");
    sessionFactory.setHibernateProperties(hibernateProperties());
    return sessionFactory;
  }

  /**
   * This method sets up and returns a configured {@code HibernateTransactionManager} for hibernate.
   *
   * @param sessionFactory the session factory to be used by the transaction manager
   * @return the configured {@code HibernateTransactionManager}
   */
  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory);
    return transactionManager;
  }

  /**
   * Returns a properties object which contains the necessary settings for hibernate.
   *
   * @return the properties object
   */
  Properties hibernateProperties() {
    return new Properties() {
      {
       setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
       setProperty("hibernate.show_sql", "true");
       setProperty("hibernate.hbm2ddl.auto", "update");
      }
    };
  }
}
