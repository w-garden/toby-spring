package springbook.learningtest.spring.ioc.property;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PropertyTest {
    @Test
    public void propertyDI(){
        ApplicationContext ctx = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc/property/property.xml");
        Hello hello = ctx.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));
        assertThat(hello.sayHello(), is("Hello Spring"));

    }
}
