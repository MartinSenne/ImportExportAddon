package com.vaadin.importexport.api;

public interface Exporter<CID, RID> {
    void export( Tabular<CID, RID> tabular, ExporterConfig<CID> config );
}
