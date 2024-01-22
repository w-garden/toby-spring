package springbook.learningtest.spring;

import org.springframework.beans.factory.config.BeanDefinition;

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
