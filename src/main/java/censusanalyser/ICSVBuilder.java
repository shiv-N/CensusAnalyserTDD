package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    public <E> int getCount(Iterator<E> CSVIterator);
    public <E>Iterator<E> getCSVIterator(Reader reader, Class csvClass) throws CensusAnalyserException;
}
