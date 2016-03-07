package com.vaadin.importexport.fp;

import java.util.Map;

public class MapAsPartialFunction<T, U> extends PartialFunction<T, U> {

    private Map<T, U> values;

    public MapAsPartialFunction(Map<T, U> values) {
        this.values = values;
    }

    @Override
    public boolean isDefinedAt(T t) {
        return values.containsKey(t);
    }

    @Override
    public U apply(T t) {
        return values.get(t);
    }
}
