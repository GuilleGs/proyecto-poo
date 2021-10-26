package aplicacion.models.dao.df;

import aplicacion.models.Alumno;
import aplicacion.models.dao.PersonaDAO;
import utils.models.Criteria;
import utils.models.df.DataFileConnection;
import utils.models.df.UtilsCSV;

import java.util.*;

public class AlumnoDF implements PersonaDAO<Alumno>, UtilsCSV<Alumno> {

    private final ApoderadoDF apoderadoData = new ApoderadoDF();

    @Override
    public Alumno get(String rut) {
        List<String> dataList = DataFileConnection.getData("alumnos");
        if (dataList != null)
            for (String csv : dataList) {
                if (csv.split(",")[2].equals(rut))
                    return this.fromCSV(csv);
            }
        return null;
    }

    @Override
    public Optional<HashMap<String, Alumno>> getAll() {
        Alumno alumno;
        HashMap<String, Alumno> alumnos = new HashMap<String, Alumno>();
        List<String> dataList = DataFileConnection.getData("alumnos");
        for (String csv : dataList) {
            alumno = fromCSV(csv);
            alumnos.put(alumno.getRut(), alumno);
        }
        return Optional.of(alumnos);
    }

    @Override
    public Optional<HashMap<String, Alumno>> getAll(Criteria[] params) {
        return Optional.empty();
    }

    @Override
    public void insert(Alumno alumno) {
        apoderadoData.insert(alumno.getApoderado());
        DataFileConnection.addData("alumnos", toCSV(alumno));
    }

    @Override
    public void update(Alumno alumno, HashMap<String, String> params) {
        Alumno oldData = get(alumno.getRut());
        DataFileConnection.updateData("alumnos", toCSV(oldData), toCSV(alumno));
    }

    @Override
    public void delete(Alumno alumno) {
        apoderadoData.delete(alumno.getApoderado());
        DataFileConnection.deleteData("alumnos", toCSV(alumno));
    }

    @Override
    public String toCSV(Alumno alumno) {
        List<String> dataList = new ArrayList<>();
        dataList.add(Integer.toString(alumno.getNivel()));
        dataList.add(Character.toString(alumno.getParalelo()));
        dataList.add(alumno.getRut());
        dataList.add(alumno.getNombres());
        dataList.add(alumno.getApPaterno());
        dataList.add(alumno.getApMaterno());
        dataList.add(alumno.getApoderado().getRut());
        for (float[] diasAsistencia: alumno.getAsistencia().getAsistenciaMatriz()) {
            dataList.add(Arrays.toString(diasAsistencia));
        }
        return UtilsCSV.listToCSV(dataList);
    }

    @Override
    public Alumno fromCSV(String csv) {
        // Todo: Leer asistencia desde el csv
        String[] parts = csv.split(",");
        return new Alumno(parts[2],
                parts[3],
                parts[4],
                parts[5],
                Integer.parseInt(parts[0]),
                parts[1].charAt(0),
                apoderadoData.get(parts[6]));
    }
}
