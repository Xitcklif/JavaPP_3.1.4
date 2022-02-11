package ru.kata.spring.boot_security.demo.configs;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@ComponentScan(value = "ru.kata.spring.boot_security.demo")
public class AppConfig {

   private final Environment env;

   public AppConfig(Environment env) {
      this.env = env;
   }

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();

      dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
      dataSource.setUrl(env.getProperty("spring.datasource.url"));
      dataSource.setUsername(env.getProperty("spring.datasource.username"));
      dataSource.setPassword(env.getProperty("spring.datasource.password"));

      return dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
      LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
      Properties properties = new Properties();

      properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));

      emf.setDataSource(getDataSource());
      emf.setPackagesToScan(("ru.kata.spring.boot_security.demo"));
      emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      emf.setJpaProperties(properties);
      emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
      emf.afterPropertiesSet();

      return  emf;
   }

   @Bean
   public PlatformTransactionManager getTransactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getObject());
      return transactionManager;
   }
}