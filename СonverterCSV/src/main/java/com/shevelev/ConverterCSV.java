package com.shevelev;

import com.univocity.parsers.common.CommonParserSettings;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ConverterCSV  implements ConverterFile{
    private Map<String,Column> substituteSCV;
    private String csvFileNameToResources;


    public ConverterCSV(String csvFileNameToResources) {
        this.csvFileNameToResources = csvFileNameToResources;
        substituteSCV = new HashMap<>();
    }


    @Override
    public void start(CommonParserSettings settings, WriterFile writerFile) throws IOException {
        // A RowListProcessor stores each parsed row in a List.
        RowListProcessor rowListProcessor = new RowListProcessor();
        settings.setProcessor(rowListProcessor);

        CsvParser parser = new CsvParser((CsvParserSettings) settings);
        parser.parse(getReader(csvFileNameToResources));

        String[] headers = rowListProcessor.getHeaders();

        List<String[]> rows = rowListProcessor.getRows();

        for (String[] currentRow : rows){

            List<Integer> tmpRow = new ArrayList<>();

            for (int i  = 0; i < headers.length; i++){

                String currentHeader = headers[i];

                Column column  = substituteSCV.get(currentHeader);

                if (column == null) {
                    column = new Column(currentHeader);
                    substituteSCV.put(currentHeader, column);
                }


                Integer row = column.getSubstituteRows().get(currentRow[i]);

                if (row == null){
                    if (column.getName().equals("LASTSTATUS")){
                        double count = Double.parseDouble(currentRow[i]);
                        column.getSubstituteRows().put(currentRow[i], (int) count);
                        tmpRow.add((int) count);

                    }else {
                        column.getSubstituteRows().put(currentRow[i], column.getCount());
                        tmpRow.add(column.getCount());
                        column.setCount(column.getCount() + 1);
                    }
                }else {
                    tmpRow.add(row);
                }
            }
            writerFile.write(headers, tmpRow);
        }
    }

    private Reader getReader(String relativePath)  {
        try {
            return new InputStreamReader(Converter.class.getClassLoader().getResourceAsStream(relativePath), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unable to read input", e);
        }
    }
}
