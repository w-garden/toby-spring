package springbook.learningtest.spring.ioc.autowiring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AutowiringTest {
    @Test
    public void autowiringDI_ByName(){
        ApplicationContext ctx = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc/autowiring/autowiring_byName.xml");
        Hello hello = ctx.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));
        assertThat(hello.sayHello(), is("Hello Spring"));

        Printer printer = ctx.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));
    }
    @Test
    public void autowiringDI_ByType(){
        ApplicationContext ctx = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc/autowiring/autowiring_byType.xml");
        Hello hello = ctx.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));

        Printer printer = ctx.getBean("mainPrinter", Printer.class);
        hello.print();
        assertThat(printer.toString(),is("Hello Spring"));
        assertThat(printer, is(notNullValue()));
    }
}
