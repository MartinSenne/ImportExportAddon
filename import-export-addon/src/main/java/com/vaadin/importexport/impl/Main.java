package com.vaadin.importexport.impl;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.importexport.api.Conversion;
import com.vaadin.importexport.api.Converter;
import com.vaadin.importexport.api.Exporter;
import com.vaadin.importexport.api.Row;
import com.vaadin.importexport.impl.exporters.CsvExporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static final String S = "s";
    public static final String I = "i";
    public static final String D = "d";

    public static void main(String[] args) {
        IndexedContainer simpleContainer = createSimpleContainer();

        // capture the container
        DataSourceTabular tabular = new DataSourceTabular(simpleContainer);
        
        // export it
        try {
            
            Map<Object, Converter> map = new HashMap<>();
            map.put(S, new Converter() {
                        @Override
                        public Object convert(Object input) {
                            return ((String)input) + "_exported";
                        }
                    });
            
            Conversion<Object> columnConversion = new Conversion<>( map );

            Conversion<Class<?>> typeConversion = new Conversion<>( new HashMap<>() );
            
            
            CsvExporter csvExporter = new CsvExporter();
            csvExporter.export(tabular, columnConversion, typeConversion, new FileWriter("/home/martin/test.csv"));
        } catch (IOException e) {
            e.printStackTrace();
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
