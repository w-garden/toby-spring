package springbook.learningtest.template;

public interface LineCallback_Generic<T> {
    T doSomethingWithLine(String line, T value);
}
