package com.vaadin.importexport.api;

import java.io.Writer;

public interface Exporter<CID, RID> {
    void export(Tabular<CID, RID> tabular, Conversion<CID> columnConversion, Conversion<Class<?>> typeConversion, Writer writer );
}
