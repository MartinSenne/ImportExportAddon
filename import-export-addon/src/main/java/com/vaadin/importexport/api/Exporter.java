package com.vaadin.importexport.api;

import com.vaadin.importexport.fp.PartialFunction;

import java.io.Writer;

public interface Exporter<CID, RID> {
    void export(Tabular<CID, RID> tabular,
                PartialFunction<CID, Converter<Object, String>> columnConversion,
                PartialFunction<Class<?>, Converter<Object, String>> typeConversion,
                Writer writer );
}
