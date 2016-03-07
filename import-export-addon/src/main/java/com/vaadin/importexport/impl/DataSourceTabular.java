package com.vaadin.importexport.impl;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.importexport.api.Row;
import com.vaadin.importexport.api.Tabular;

import java.util.Collection;
import java.util.stream.Stream;

public class DataSourceTabular implements Tabular<Object, Object> {
    private Container container;

    public DataSourceTabular(Container container) {
        this.container = container;
    }

    @Override
    public int rows() {
        return container.size();
    }

    @Override
    public int columns() {
        return container.getContainerPropertyIds().size();
    }

    @Override
    public Object dataAt(Object column, Object row) {
        return container.getContainerProperty(row, column).getValue();
    }

    @Override
    public <T> T dataAt(Object column, Object row, Class<T> clazz) {
        return (T)(container.getContainerProperty(row, column).getValue());
    }

    @Override
    public Stream<? extends Row<Object>> rowStream() {

        Stream<RowImpl> stream = ((Collection<Object>) container.getItemIds())
                .stream()
                .map(itemId -> new RowImpl(container.getItem(itemId)));
                // .iterator();
        return stream;
    }

    @Override
    public Collection<Object> columnIds() {
        return (Collection<Object>) container.getContainerPropertyIds();
    }

    static class RowImpl implements Row<Object> {

        private Item item;

        public RowImpl(Item item) {
            this.item = item;
        }

        @Override
        public int columns() {
            return item.getItemPropertyIds().size();
        }

        @Override
        public Object dataAt(Object pid) {
            return item.getItemProperty(pid).getValue();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("RowImpl{");

            item.getItemPropertyIds().forEach( pid -> {
                sb.append( pid + " -> " + dataAt(pid) + ", ");
            });

            sb.append("}");
            return sb.toString();
        }
    }
}
