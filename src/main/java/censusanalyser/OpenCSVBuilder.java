package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class OpenCSVBuilder implements ICSVBuilder {

    @Override
    public <E>Iterator<E> getCSVIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }
        catch (IllegalStateException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
