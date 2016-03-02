package com.vaadin.importexport.impl;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.importexport.api.Row;

import java.util.Date;
import java.util.Iterator;

public class Main {

    public static final String S = "s";
    public static final String I = "i";
    public static final String D = "d";

    public static void main(String[] args) {
        IndexedContainer simpleContainer = createSimpleContainer();

        DataSourceTabular tabular = new DataSourceTabular(simpleContainer);
        Iterator<? extends Row<Object>> iterator = tabular.rowIterator();
        while (iterator.hasNext()) {
            System.out.println( iterator.next() );
        }
    }

    private static IndexedContainer createSimpleContainer() {
        IndexedContainer c = new IndexedContainer();

        c.addContainerProperty(S, String.class, null);
        c.addContainerProperty(I, Integer.class, null);
        c.addContainerProperty(D, Date.class, null);

        Item item1 = c.addItem(1);
        item1.getItemProperty(S).setValue("asdf");
        item1.getItemProperty(I).setValue(3);
        item1.getItemProperty(D).setValue(new Date());

        Item item2 = c.addItem(2);
        item2.getItemProperty(S).setValue("xxxx");
        item2.getItemProperty(I).setValue(4);
        item2.getItemProperty(D).setValue(new Date());

        return c;
    }
}
