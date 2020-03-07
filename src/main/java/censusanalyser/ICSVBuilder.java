package censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder {
    public <E>Iterator<E> getCSVIterator(Reader reader, Class csvClass) throws CSVBuilderException;
    public List getCSVList(Reader reader, Class csvClass) throws CSVBuilderException;
}
