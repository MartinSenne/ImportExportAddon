package com.vaadin.importexport.api;

import java.util.Collection;
import java.util.Iterator;

public interface Tabular<CID, RID> {
    int rows();
    int columns();

    Object dataAt( CID column, RID row );

    <T> T dataAt( CID column, RID row, Class<T> clazz );

    Iterator<? extends Row<CID>> rowIterator();
    
    Collection<CID> columnIds();
}
