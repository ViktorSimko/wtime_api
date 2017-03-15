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
 * Created by simkoviktor on 2017. 03. 15..
 */

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.viktorsimko.wtime")
public class WTimeConfiguration {

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

  @Bean
  public LocalSessionFactoryBean sessionFactory() throws Exception {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.viktorsimko.wtime.model");
    sessionFactory.setHibernateProperties(hibernateProperties());
    return sessionFactory;
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory);
    return transactionManager;
  }

  Properties hibernateProperties() {
    return new Properties() {
      {
       setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
       setProperty("hibernate.show_sql", "true");
      }
    };
  }
}
