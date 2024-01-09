package springbook.learningtest.spring.factoryBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.spring.pointcut.Target;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void sample() throws NoSuchMethodException {
        System.out.println(Target.class.getMethod("minus", int.class, int.class));
    }

    @Test
    public void getMessageFactoryBean() {
        Object message = context.getBean("message");
        System.out.println(message.getClass());
        assertThat(message, is(Message.class));
        assertThat(((Message) message).getText(), is("Factory Bean"));

    }

    @Test
    public void getFactoryBean() {
        assertThat(context.getBean("message"), is(Object.class));
        assertThat(context.getBean("&message"), is(MessageFactoryBean.class)); //&를 이름 앞에 붙여주면 팩토리 빈 자체를 돌려준다

    }
}
