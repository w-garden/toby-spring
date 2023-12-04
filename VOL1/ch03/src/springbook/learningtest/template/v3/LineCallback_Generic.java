package springbook.learningtest.template.v3;

public interface LineCallback_Generic<T> {
    T doSomethingWithLine(String line, T value);
}
