package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import springbook.learningtest.spring.ioc.bean.*;


import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationContextTest {
    private String baasPath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass())) + "/";;

    @Test
    public void registerBean() {
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

    @Test
    public void genericApplicationContext(){
        GenericApplicationContext ac = new GenericApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
        reader.loadBeanDefinitions(baasPath +"GenericApplicationContext.xml");
        ac.refresh(); //모든 메타정보가 등록되었으니 애플리케이션 컨테이너를 초기화하라는 명령이다

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }
    @Test
    public void genericXmlApplicationContext(){
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext(baasPath + "GenericApplicationContext.xml");

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }
    @Test
    public void contextHierachy(){
        ApplicationContext parent = new GenericXmlApplicationContext(baasPath + "parentContext.xml");
        GenericApplicationContext child = new GenericApplicationContext(parent);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions(baasPath + "childContext.xml");
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));

        Hello hello = child.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));

        hello.print();

        assertThat(printer.toString(),is("Hello Child"));

    }

    @Test
    public void simpleBeanScanning(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext("springbook.learningtest.spring.ioc.bean");
        AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
        assertThat(hello, is(notNullValue()));

    }
    @Test
    public void autoBeanScanning(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
        AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
        assertThat(hello,is(notNullValue()));

        AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);
        assertThat(config, is(notNullValue()));

        assertThat(config.annotatedHello(), is(sameInstance(hello)));
    }

}
