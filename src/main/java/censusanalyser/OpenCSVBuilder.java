package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class OpenCSVBuilder implements ICSVBuilder {

    @Override
    public <E> int getCount(Iterator<E> CSVIterator) {
        Iterable<E> csvIterable =() -> CSVIterator;
        int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return namOfEateries;
    }

    @Override
    public <E>Iterator<E> getCSVIterator(Reader reader, Class csvClass) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }
        catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
