package utils.models.df;

import java.util.List;

public class DataFileConnection {

    private static final DataFile2 alumnoData = new DataFile2("alumno");
    private static final DataFile2 apoderadoData = new DataFile2("apoderado");
    private static final DataFile2 cursoData = new DataFile2("curso");
    private static final DataFile2 profesorData = new DataFile2("profesor");

    public static List<String> getData(String tipo) {
        switch (tipo) {
            case "alumnos":
                return alumnoData.getData();
            case "apoderados":
                return apoderadoData.getData();
            case "cursos":
                return cursoData.getData();
            case "profesores":
                return profesorData.getData();
            default:
                return null;
        }
    }

    public static void addData(String tipo, String csv) {
        switch (tipo) {
            case "alumnos":
                 alumnoData.insertLine(csv);
            case "apoderados":
                 apoderadoData.insertLine(csv);
            case "cursos":
                 cursoData.insertLine(csv);
            case "profesores":
                 profesorData.insertLine(csv);
        }
    }

    public static void updateData(String tipo, String oldCSV, String newCSV) {
        switch (tipo) {
            case "alumnos":
                alumnoData.updateLine(oldCSV, newCSV);
            case "apoderados":
                apoderadoData.updateLine(oldCSV, newCSV);
            case "cursos":
                cursoData.updateLine(oldCSV, newCSV);
            case "profesores":
                profesorData.updateLine(oldCSV, newCSV);
        }
    }

    public static void deleteData(String tipo, String csv) {
        switch (tipo) {
            case "alumnos":
                alumnoData.deleteLine(csv);
            case "apoderados":
                apoderadoData.deleteLine(csv);
            case "cursos":
                cursoData.deleteLine(csv);
            case "profesores":
                profesorData.deleteLine(csv);
        }
    }

}
