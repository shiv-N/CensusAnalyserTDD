package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "StateName", required = true)
    public String StateName;

    @CsvBindByName(column = "StateCode", required = true)
    public String StateCode;

    @CsvBindByName(column = "SrNo", required = true)
    public int SrNo;

    @CsvBindByName(column = "TIN", required = true)
    public int TIN;

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "SrNo='" + SrNo + '\'' +
                "StateName='" + StateName + '\'' +
                ", TIN='" + TIN + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
