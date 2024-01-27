package springbook.learningtest.spring.ioc.resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ResourceTest {
    @Test
    public void resource(){
        ApplicationContext ctx = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc/resource/resource.xml");
        Hello hello = ctx.getBean("hello", Hello.class);

        assertThat(hello, is(notNullValue()));

        Printer printer = ctx.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));
        hello.print();
        assertThat(printer.toString(),is("Hello Spring"));
    }

}
