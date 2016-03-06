package com.vaadin.importexport.api;

import com.vaadin.importexport.fp.PartialFunction;

import java.util.Map;

public class Conversion<T> extends PartialFunction<T, Converter> {

    private Map<T, Converter> converters;

    public Conversion(Map<T, Converter> converters) {
        this.converters = converters;
    }
    
    @Override
    public boolean isDefinedAt(T t) {
        return converters.containsKey(t);
    }

    @Override
    public Converter apply(T t) {
        return converters.get(t);
    }
}
