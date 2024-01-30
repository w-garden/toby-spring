package springbook.learningtest.spring.ioc.autowiredTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AutowiredTest {

    @Test
    public void simpleAutowired(){
        AbstractApplicationContext ac = new AnnotationConfigApplicationContext(BeanA.class, BeanB.class);
        BeanA beanA = ac.getBean(BeanA.class);
        Assert.assertThat(beanA.beanB,is(notNullValue()));
    }
    private static class BeanA{
        @Autowired
        BeanB beanB;
    }
    private static class BeanB {

    }
}
