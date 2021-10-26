package aplicacion.models.dao.df;

import aplicacion.models.Apoderado;
import aplicacion.models.dao.PersonaDAO;
import utils.models.Criteria;
import utils.models.df.DataFile;
import utils.models.df.DataFileConnection;
import utils.models.df.UtilsCSV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ApoderadoDF implements PersonaDAO<Apoderado>, UtilsCSV<Apoderado> {

    @Override
    public Apoderado get(String rut) {
        List<String> data = DataFileConnection.getData("apoderados");
        for (String csv : data) {
            if (csv.split(",")[0].equals(rut))
                return fromCSV(csv);
        }
        return null;
    }

    @Override
    public Optional<HashMap<String, Apoderado>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<HashMap<String, Apoderado>> getAll(Criteria[] params) {
        return Optional.empty();
    }

    @Override
    public void insert(Apoderado apoderado) {
        DataFileConnection.addData("apoderados", toCSV(apoderado));
    }

    @Override
    public void update(Apoderado apoderado, HashMap<String, String> params) {
        String oldCSV = toCSV(get(apoderado.getRut())), newCSV = toCSV(apoderado);
        DataFileConnection.updateData("apoderados", oldCSV, newCSV);
    }

    @Override
    public void delete(Apoderado apoderado) {
        DataFileConnection.deleteData("apoderados", toCSV(apoderado));
    }

    @Override
    public String toCSV(Apoderado apoderado) {
        List<String> dataList = new ArrayList<>();
        dataList.add(apoderado.getRut());
        dataList.add(apoderado.getNombres());
        dataList.add(apoderado.getApPaterno());
        dataList.add(apoderado.getApMaterno());
        dataList.add(Integer.toString(apoderado.getTelefono()));
        dataList.add(apoderado.getEmail());
        return DataFile.listToCSV(dataList);
    }

    @Override
    public Apoderado fromCSV(String csv) {
        String[] parts = csv.split(",");
        return new Apoderado(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5]);
    }
}
