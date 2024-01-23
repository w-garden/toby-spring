package springbook.learningtest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;
import springbook.learningtest.spring.Hello;
import springbook.learningtest.spring.StringPrinter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class HelloBeanTest {

    @Test
    public void staticApplicationContext() {
        StaticApplicationContext ac = new StaticApplicationContext(); //IoC 컨테이너 생성. 생성과 동시에 컨테이너로 동작
        ac.registerSingleton("hello1", Hello.class); //Hello 클래스를 hello이라는 이름의 싱글톤 빈으로 컨테이너에 등록
        //registerSingleton : 디폴트 메타정보를 사용해서 싱글톤 빈을 등록한다.
        Hello hello1 = ac.getBean("hello1", Hello.class);
        assertThat(hello1, is(notNullValue()));

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class); //Bean메타정보를 담은 오브젝트를 만든다.
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        ac.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = ac.getBean("hello2", Hello.class);
        assertThat(hello2.sayHello(), is("Hello Spring"));

        assertThat(hello1, is(not(hello2)));
        assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
    }

    @Test
    public void registerBeanWithDependency(){
        StaticApplicationContext ac = new StaticApplicationContext();

        ac.registerBeanDefinition("printer"
                , new RootBeanDefinition(StringPrinter.class)); //StringPrinter 클래스를 printer이름으로 등록
        
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));

        ac.registerBeanDefinition("hello", helloDef); //helloDef 오브젝트를 hello라는 이름으로 등록

        Hello hello = ac.getBean("hello", Hello.class); //컨테이너에서 hello라는 이름의 빈을 가져옴
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }


}


