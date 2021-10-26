package utils.models.df;

import java.util.List;

public interface UtilsCSV<T> {

    static String listToCSV(List<String> list) {
        String csv = "";
        for (String param: list)
            csv = csv.concat(param).concat(",");
        csv = csv.substring(0, csv.length() - 1);
        return csv;
    }

    String toCSV(T obj);
    T fromCSV(String csv);
}
