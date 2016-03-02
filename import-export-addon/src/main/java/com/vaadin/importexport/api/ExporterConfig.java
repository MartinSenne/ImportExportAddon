package com.vaadin.importexport.api;

public interface ExporterConfig<CID> {
    Converter converterFor(CID pid);
}
