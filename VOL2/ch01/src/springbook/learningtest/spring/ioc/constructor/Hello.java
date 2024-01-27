package springbook.learningtest.spring.ioc.constructor;



public class Hello {
    String name;
    Printer printer;
    public Hello(String name, Printer printer) {
        this.name = name;
        this.printer = printer;
    }

    public String sayHello(){
        return "Hello " + name;
    }
    public void print(){
        this.printer.print(sayHello());
    }

}
