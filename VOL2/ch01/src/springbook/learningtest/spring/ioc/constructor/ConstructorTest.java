package springbook.learningtest.spring.ioc.constructor;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ConstructorTest {
    @Test
    public void constructorDI_Type(){
        ApplicationContext ctx = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc/constructor/constructor_type.xml");
        Hello hello = ctx.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));
        assertThat(hello.sayHello(), is("Hello Spring"));

        Printer printer = ctx.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));

    }
    @Test
    public void constructorDI_Name(){
        ApplicationContext ctx = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc/constructor/constructor_name.xml");
        Hello hello = ctx.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));
        assertThat(hello.sayHello(), is("Hello Spring"));

        Printer printer = ctx.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));

    }
}
