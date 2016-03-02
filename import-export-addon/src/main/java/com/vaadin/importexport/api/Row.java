package com.vaadin.importexport.api;

public interface Row<CID> {
    int columns();

    Object dataAt(CID cid);
}
