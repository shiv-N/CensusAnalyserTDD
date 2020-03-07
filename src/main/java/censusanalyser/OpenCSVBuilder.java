package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class OpenCSVBuilder implements ICSVBuilder {

    @Override
    public <E>Iterator<E> getCSVIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        return (Iterator<E>) this.getCSVBean(reader,csvClass).iterator();
    }

    @Override
    public List getCSVList(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader,csvClass).parse();
    }

    private <E>CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        }
        catch (IllegalStateException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
