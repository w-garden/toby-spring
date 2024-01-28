package springbook.learningtest.spring.ioc.annotation.resource;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Hello {
    @Value("Spring")
    String name;
@Resource
    Printer printer;

//    public void setName(String name) {
//        this.name = name;
//    }
//    public void setPrinter(Printer printer) {
//        this.printer = printer;
//    }

    public String sayHello(){
        return "Hello " + name;
    }
    public void print(){
        this.printer.print(sayHello());
    }

}
