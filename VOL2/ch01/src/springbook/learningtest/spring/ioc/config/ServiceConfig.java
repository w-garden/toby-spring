package springbook.learningtest.spring.ioc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class ServiceConfig {
    /*
    <bean id="dataSource" class="org.springframework.jdbc.datasource.SimpleDriverDataSource">
        <property name="driverClass" value="org.h2.Driver"/>
        <property name="url" value="jdbc:h2:tcp://localhost/~/spring"/>
        <property name="username" value="sa"/>
        <property name="password" value=""/>
    </bean>
     */
    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/spring");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
