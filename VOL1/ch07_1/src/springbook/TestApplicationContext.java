package springbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.mail.MailSender;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.user.UserServiceTest.TestUserService;
import springbook.user.dao.UserDao;
import springbook.user.dao.UserDaoJdbc;
import springbook.user.service.DummyMailSender;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceImpl;
import springbook.user.sqlservice.OxmSqlService;
import springbook.user.sqlservice.SqlRegistry;
import springbook.user.sqlservice.SqlService;
import springbook.user.sqlservice.updatable.EmbeddedDbSqlRegistry;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@EnableTransactionManagement
public class TestApplicationContext {
    /**
     * DB 연결과 트랜잭션
     */
    /*
       <bean id="dataSource" class="org.springframework.jdbc.datasource.SimpleDriverDataSource">
           <property name="driverClass" value="org.h2.Driver"/>
           <property name="url" value="jdbc:h2:tcp://localhost/~/spring"/>
           <property name="username" value="sa"/>
           <property name="password" value=""/>
       </bean>
     */
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/spring");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    /*
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <property name="dataSource" ref="dataSource"/>
       </bean>
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    /**
     * 애플리케이션 로직 & 테스트
     */
    /*
     <bean id="userDao" class="springbook.user.dao.UserDaoJdbc">
        <property name="dataSource" ref="dataSource"/>
        <property name="sqlService" ref="sqlService"/>
     </bean>
    */
    @Autowired
    SqlService sqlService;

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(this.dataSource());
        userDao.setSqlService(this.sqlService);
        return userDao;
    }

    /*
       <bean id="userService" class="springbook.user.service.UserServiceImpl">
           <property name="userDao" ref="userDao"/>
           <property name="mailSender" ref="mailSender"/>
       </bean>
        */
    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(this.userDao());
        userService.setMailSender(this.mailSender());
        return userService;
    }

    /*
       <bean id="testUserService" class="springbook.user.UserServiceTest$TestUserService" parent="userService"/>
     */
    @Bean
    public UserService testUserService() {
        TestUserService testUserService = new TestUserService();
        testUserService.setUserDao(this.userDao());
        testUserService.setMailSender(this.mailSender());
        return testUserService;
    }


    /* <bean id="mailSender" class="springbook.user.service.DummyMailSender"/> */
    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

    /**
     * SQL 서비스
     */
    /*
    <bean id="sqlService" class="springbook.user.sqlservice.OxmSqlService">
        <property name="unmarshaller" ref="unmarshaller"/>
        <property name="sqlmap" value="classpath:springbook/user/dao/sqlmap.xml"/>
        <property name="sqlRegistry" ref="sqlRegistry"/>
    </bean>
     */
    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(this.unmarshaller());
//        sqlService.setSqlmap("classpath:springbook/user/dao/sqlmap.xml");
        sqlService.setSqlRegistry(this.sqlRegistry());
        return sqlService;
    }


    /*
    <bean id="unmarshaller" class="org.springframework.oxm.jaxb.Jaxb2Marshaller">
        <property name="contextPath" value="springbook.user.sqlservice.jaxb"/>
    </bean>
     */
    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setContextPath("springbook.user.sqlservice.jaxb");
        return unmarshaller;
    }

    /*
    <bean id="sqlRegistry" class="springbook.user.sqlservice.updatable.EmbeddedDbSqlRegistry">
        <property name="dataSource" ref="embeddedDatabase"/>
    </bean>
     */
    @Bean
    public SqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(this.embeddedDatabase());
        return sqlRegistry;
    }

    /*
    <jdbc:embedded-database id="embeddedDatabase" type="H2">
        <jdbc:script location="classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql"/>
    </jdbc:embedded-database>
     */
    @Bean
    public DataSource embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setName("embeddedDatabase")
                .setType(H2)
                .addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql")
                .build();
    }
}
