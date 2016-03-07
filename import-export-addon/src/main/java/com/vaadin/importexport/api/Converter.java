package com.vaadin.importexport.api;

@FunctionalInterface
public interface Converter<I, O> {
    O convert(I input);
}
