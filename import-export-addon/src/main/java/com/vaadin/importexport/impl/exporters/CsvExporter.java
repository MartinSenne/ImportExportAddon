package com.vaadin.importexport.impl.exporters;

import com.opencsv.CSVWriter;
import com.vaadin.importexport.api.*;
import com.vaadin.importexport.fp.MapAsPartialFunction;
import com.vaadin.importexport.fp.PartialFunction;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class CsvExporter implements Exporter<Object, Object> {

    private CsvConfig csvConfig;

    public CsvExporter() {
        this.csvConfig = CsvConfig.createDefaultConfig();
    }
    
    public CsvExporter(CsvConfig csvConfig) {
        this.csvConfig = csvConfig;    
    }

    private static Converter<Object, String> fallbackConverter = new ToStringConverter();

    @Override
    public void export(Tabular<Object, Object> tabular,
                       Map<Object, Converter<Object, String>> columnConversion,
                       Map<Class<?>, Converter<Object, String>> typeConversion,
                       Writer writer) {
        CSVWriter csvWriter = new CSVWriter(writer, csvConfig.getSeparator(), csvConfig.getQuoteChar(), csvConfig.getEscapeCharacter(), csvConfig.getLineEnd());

        Collection<Object> cids = tabular.columnIds();

        PartialFunction<Object, Converter<Object, String>> columnConversionFnc = new MapAsPartialFunction<>(columnConversion);
        PartialFunction<Class<?>, Converter<Object, String>> typeConversionFnc = new MapAsPartialFunction<>(typeConversion);

        Stream<? extends Row<Object>> rowStream = tabular.rowStream();

        rowStream.forEach( row -> {
            String[] convertedValues = cids.stream()
                    .map(cid -> {
                        Object data = row.dataAt(cid);
                        return resolveConverter(data, cid, columnConversionFnc, typeConversionFnc).convert(data);
                    })
                    .toArray(String[]::new);

            csvWriter.writeNext(convertedValues);
        });

        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not close writer properly");
        }
    }

    private Converter<Object, String> resolveConverter(Object data, Object cid,
                                                       PartialFunction<Object, Converter<Object, String>> columnConversion,
                                                       PartialFunction<Class<?>, Converter<Object, String>> typeConversion) {
        Converter<Object, String> converter = null;
        if (columnConversion.isDefinedAt(cid)) {
            converter = columnConversion.apply(cid);
        } else {
            if (typeConversion.isDefinedAt(data.getClass())) {
                converter = typeConversion.apply(data.getClass());
            } else {
                converter = fallbackConverter;
            }
        }
        return converter;
    }

    static class ToStringConverter implements Converter {

        @Override
        public Object convert(Object input) {
            return input.toString();
        }
    }


    // THIS IS ALL SO INCREDIBLY PAINFUL in JAVA :(

    class PF<CID> extends PartialFunction<CID, Converter> {

        private Row<CID> row;
        private Conversion<Class<?>> typeConversion;

        public PF(Row<CID> row, Conversion<Class<?>> typeConversion) {
            this.row = row;
            this.typeConversion = typeConversion;
        }

        @Override
        public boolean isDefinedAt(CID cid) {
            return typeConversion.isDefinedAt( row.dataAt(cid).getClass() );
        }

        @Override
        public Converter apply(CID cid) {
            return typeConversion.apply( row.dataAt(cid).getClass() );
        }
    }
    

    /**
     * CSV export configuration (e.g. separator, char for quote, etc.).
     */
    public static class CsvConfig {
        private final char escapeCharacter;
        private final char separator;
        private final char quoteChar;
        private final String lineEnd;

        private CsvConfig(char separator, char quoteChar, char escapeCharacter, String lineEnd) {
            this.escapeCharacter = escapeCharacter;
            this.separator = separator;
            this.quoteChar = quoteChar;
            this.lineEnd = lineEnd;
        }

        public char getEscapeCharacter() {
            return escapeCharacter;
        }

        public char getSeparator() {
            return separator;
        }

        public char getQuoteChar() {
            return quoteChar;
        }

        public String getLineEnd() {
            return lineEnd;
        }

        public static CsvConfig createDefaultConfig() {
            return new CsvConfig( CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END );   
        }

        /**
         * @param separator  the delimiter to use for separating entries
         * @param quotechar  the character to use for quoted elements
         * @param escapechar the character to use for escaping quotechars or escapechars
         * @param lineEnd    the line feed terminator to use
         */
        public static CsvConfig createConfig(char separator, char quotechar, char escapechar, String lineEnd) {
            return new CsvConfig(separator, quotechar, escapechar, lineEnd);
        }
    }
}


