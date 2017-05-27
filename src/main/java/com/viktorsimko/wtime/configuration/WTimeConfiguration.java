package com.viktorsimko.wtime.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * The configuration class for the project.
 * It sets up the necessary beans.
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.viktorsimko.wtime")
public class WTimeConfiguration extends WebMvcConfigurerAdapter {

  private Logger logger = LoggerFactory.getLogger(WTimeConfiguration.class);

  /**
   * This method sets up and returns a pooled data source for hibernate.
   *
   * @return the configured data source
   * @throws Exception if the connection properties are not correct
   */
  @Bean(destroyMethod = "close")
  public ComboPooledDataSource dataSource() throws Exception {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    Properties dbProperties = databaseProperties();
    dataSource.setDriverClass(dbProperties.getProperty("database.driver"));
    dataSource.setJdbcUrl(dbProperties.getProperty("database.url"));
    dataSource.setUser(dbProperties.getProperty("database.username"));
    dataSource.setPassword(dbProperties.getProperty("database.password"));
    dataSource.setMinPoolSize(3);
    dataSource.setMaxPoolSize(20);
    dataSource.setMaxIdleTime(30000);
    Connection connection = dataSource.getConnection();
    Statement statement = connection.createStatement();

    String createUsers = "CREATE TABLE IF NOT EXISTS users (\n" +
        "    username varchar(50),\n" +
        "    password varchar(50),\n" +
        "    enabled boolean,\n" +
        "    PRIMARY KEY (username)\n" +
        ")";

    String createAuthorities = "CREATE TABLE IF NOT EXISTS authorities(\n" +
        "    username varchar(50),\n" +
        "    authority varchar(50),\n" +
        "    FOREIGN KEY (username) REFERENCES users(username)\n" +
        ")";

    statement.execute(createUsers);
    statement.execute(createAuthorities);
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
  private Properties hibernateProperties() throws IOException {
    InputStream inputStream = WTimeConfiguration.class.getClassLoader().getResourceAsStream("hibernate.properties");

    if (inputStream == null) {
      logger.error("Hibernate properties not found!");
    }

    return new Properties() {
      {
        load(inputStream);
      }
    };
  }

  private Properties databaseProperties() throws IOException {
    InputStream inputStream = WTimeConfiguration.class.getClassLoader().getResourceAsStream("database.properties");

    if (inputStream == null) {
      logger.error("Database properties not found!");
    }

    return new Properties() {
      {
        load(inputStream);
      }
    };
  }
}
