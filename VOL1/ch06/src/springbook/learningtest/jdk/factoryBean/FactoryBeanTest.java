package springbook.learningtest.jdk.factoryBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFactoryBean() {
        Object message = context.getBean("message");
        System.out.println(message.getClass());
        assertThat(message,is(Message.class));
        assertThat(((Message) message).getText(), is("Factory Bean"));

    }
    @Test
    public void getFactoryBean() {
        assertThat(context.getBean("message"), is(Object.class));
        assertThat(context.getBean("&message"), is(MessageFactoryBean.class)); //&를 이름 앞에 붙여주면 팩토리 빈 자체를 돌려준다

    }
}
