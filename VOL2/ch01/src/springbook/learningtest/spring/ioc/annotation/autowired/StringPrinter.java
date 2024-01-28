package springbook.learningtest.spring.ioc.annotation.autowired;


public class StringPrinter implements Printer {
    private StringBuffer buffer = new StringBuffer();
    public void print(String message) {
        this.buffer.append(message);
    }

    @Override
    public String toString() {
        return this.buffer.toString();
    }
}
