//package com.vaadin.importexport.impl.exporters;
//
//import com.vaadin.importexport.api.Converter;
//import com.vaadin.importexport.api.Converters;
//import com.vaadin.importexport.fp.PartialFunction;
//
//import java.util.Map;
//import java.util.function.Function;
//
//public class CsvConverters implements Converters<Object> {
//    
//    private Map<Object, Converter> converters;
//
//    public CsvConverters(Map<Object, Converter> converters) {
//        this.converters = converters;
//    }
//
//    @Override
//    public Converter converterFor(Object pid) {
//        return converters.get(pid);
//    }
//}
//
