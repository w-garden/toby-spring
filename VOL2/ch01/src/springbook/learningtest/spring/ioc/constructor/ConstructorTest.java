package springbook.learningtest.spring.ioc.constructor;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.learningtest.spring.ioc.constructor.Hello;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ConstructorTest {
    @Test
    public void constructorDI(){
        ApplicationContext ctx = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc/constructor/constructor.xml");
        Hello hello = ctx.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));
        assertThat(hello.sayHello(), is("Hello Spring"));

    }
}
