package com.vaadin.importexport.api;

import java.util.Collection;
import java.util.stream.Stream;

public interface Tabular<CID, RID> {
    int rows();
    int columns();

    Object dataAt( CID column, RID row );

    <T> T dataAt( CID column, RID row, Class<T> clazz );

    Stream<? extends Row<CID>> rowStream();
    
    Collection<CID> columnIds();
}
