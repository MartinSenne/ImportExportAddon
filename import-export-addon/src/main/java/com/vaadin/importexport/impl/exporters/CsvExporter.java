package com.vaadin.importexport.impl.exporters;

import com.vaadin.importexport.api.Exporter;
import com.vaadin.importexport.api.ExporterConfig;
import com.vaadin.importexport.api.Row;
import com.vaadin.importexport.api.Tabular;

import java.io.Writer;
import java.util.Iterator;

public class CsvExporter implements Exporter<Object, Object> {

    private Writer writer;

    public CsvExporter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void export(Tabular<Object, Object> tabular, ExporterConfig<Object> config) {
        Iterator<? extends Row<Object>> it = tabular.rowIterator();
        while (it.hasNext()) {
            Row<Object> next = it.next();
        }
    }
}
