package com.vaadin.importexport.api;

@FunctionalInterface
public interface Converter {
    Object convert(Object input);
}
