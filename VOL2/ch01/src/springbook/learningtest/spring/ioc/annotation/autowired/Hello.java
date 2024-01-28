package springbook.learningtest.spring.ioc.annotation.autowired;


import javax.annotation.Resource;

public class Hello {
    String name;
    Printer printer;

    public void setName(String name) {
        this.name = name;
    }

    @Resource(name = "printer")
    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public String sayHello(){
        return "Hello " + name;
    }
    public void print(){
        this.printer.print(sayHello());
    }

}
