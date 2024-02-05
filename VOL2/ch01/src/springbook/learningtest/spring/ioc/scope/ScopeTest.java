package springbook.learningtest.spring.ioc.scope;

import org.exolab.castor.mapping.xml.PropertyType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScopeTest {
    @Test
    public void singletonScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, SingletonClient.class);
        Set<SingletonBean> bean = new HashSet<>();
        bean.add(ac.getBean(SingletonBean.class));
        bean.add(ac.getBean(SingletonBean.class));
        assertThat(bean.size(), is(1));

        bean.add(ac.getBean(SingletonClient.class).bean1);
        bean.add(ac.getBean(SingletonClient.class).bean2);
        assertThat(bean.size(), is(1));

    }

    @Test
    public void prototypeScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(PropertyTypeBean.class, PropertyTypeBeanClient.class);
        Set<PropertyTypeBean> bean = new HashSet<>();
        bean.add(ac.getBean(PropertyTypeBean.class));
        assertThat(bean.size(), is(1));

        bean.add(ac.getBean(PropertyTypeBean.class));
        assertThat(bean.size(), is(2));

        bean.add(ac.getBean(PropertyTypeBeanClient.class).bean1);
        assertThat(bean.size(), is(3));

        bean.add(ac.getBean(PropertyTypeBeanClient.class).bean2);
        assertThat(bean.size(), is(4));
    }

    static class SingletonBean {
    }

    static class SingletonClient {
        @Autowired
        private SingletonBean bean2;
        @Autowired
        private SingletonBean bean1;
    }

    @Scope("prototype")
    static class PropertyTypeBean {

    }

    static class PropertyTypeBeanClient {
        @Autowired
        private PropertyTypeBean bean1;
        @Autowired
        private PropertyTypeBean bean2;
    }
}
