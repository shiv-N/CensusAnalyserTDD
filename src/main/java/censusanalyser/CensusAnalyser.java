package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){

            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVIterator(reader,IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()){
                this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }

            return this.censusList.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }

    }
    private <E> int getCount(Iterator<E> CSVIterator) {
        Iterable<E> csvIterable =() -> CSVIterator;
        int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return namOfEateries;
    }
    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){

            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvBuilder.getCSVIterator(reader,IndiaStateCodeCSV.class);
            return this.getCount(censusCSVIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        CheckForNullObject();
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensus = new Gson().toJson(censusList);
        return sortedStateCensus;
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        CheckForNullObject();
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sort(censusComparator);
        return new Gson().toJson(censusList);
    }

    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        CheckForNullObject();
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(censusComparator);
        return new Gson().toJson(censusList);
    }

    private void CheckForNullObject() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for(int i=0;i<censusList.size()-1;i++){
            for(int j=0;j<censusList.size()-i-1;j++){
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j+1);
                if(censusComparator.compare(census1,census2)>0){
                    censusList.set(j,census2);
                    censusList.set(j+1,census1);
                }
            }
        }
    }
}
