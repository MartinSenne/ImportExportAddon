package com.vaadin.importexport.api;

import java.io.Writer;
import java.util.Map;

public interface Exporter<CID, RID> {
    void export(Tabular<CID, RID> tabular,
                Map<CID, Converter<Object, String>> columnConversion,
                Map<Class<?>, Converter<Object, String>> typeConversion,
                Writer writer );
}
