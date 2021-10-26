package aplicacion.models.dao.db;

import aplicacion.models.Apoderado;
import aplicacion.models.dao.PersonaDAO;
import utils.models.Criteria;
import utils.models.db.MySQLConnection;
import utils.models.db.SQLSentences;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Optional;

public class ApoderadoDB implements PersonaDAO<Apoderado> {

    @Override
    public Apoderado get(String rut) {
        if (MySQLConnection.isConnected())
            try {
                ResultSet resultados = MySQLConnection.getQuery(String.format(SQLSentences.GET_APODERADO.toString(), rut));
                if (resultados == null) return null;
                if (resultados.next()) {
                    return new Apoderado(
                            resultados.getString("rut"),
                            resultados.getString("nombres"),
                            resultados.getString("apellido_paterno"),
                            resultados.getString("apellido_materno"),
                            resultados.getInt("telefono"),
                            resultados.getString("email"));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        return null;
    }

    @Override
    public Optional<HashMap<String, Apoderado>> getAll() {
        if (MySQLConnection.isConnected()) {
            HashMap<String, Apoderado> apoderados = new HashMap<>();
            try {
                ResultSet resultados = MySQLConnection.getQuery(SQLSentences.GET_APODERADOS.toString());
                if (resultados == null) return Optional.empty();
                while (resultados.next()) {
                    apoderados.put(resultados.getString("rut"), new Apoderado(
                            resultados.getString("rut"),
                            resultados.getString("nombres"),
                            resultados.getString("apellido_paterno"),
                            resultados.getString("apellido_materno"),
                            resultados.getInt("telefono"),
                            resultados.getString("email")));
                }
                return Optional.of(apoderados);
            } catch (Exception e) {
                System.out.println(e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<HashMap<String, Apoderado>> getAll(Criteria[] params) {
        return Optional.empty();
    }

    @Override
    public void insert(Apoderado apoderado) {
        if (MySQLConnection.isConnected())
            MySQLConnection.updateQuery(String.format(SQLSentences.INSERT_APODERADO.toString(),
                    apoderado.getRut(),
                    apoderado.getNombres(),
                    apoderado.getApPaterno(),
                    apoderado.getApMaterno(),
                    apoderado.getTelefono(),
                    apoderado.getEmail()));
    }

    @Override
    public void update(Apoderado apoderado, HashMap<String, String> params) {
        if (MySQLConnection.isConnected())
            MySQLConnection.updateQuery(String.format(SQLSentences.UPDATE_APODERADO.toString(),
                    apoderado.getRut(),
                    apoderado.getNombres(),
                    apoderado.getApPaterno(),
                    apoderado.getApMaterno(),
                    apoderado.getTelefono(),
                    apoderado.getEmail(),
                    apoderado.getRut()));
    }

    @Override
    public void delete(Apoderado apoderado) {
        MySQLConnection.updateQuery(String.format(SQLSentences.DELETE_APODERADO.toString(), apoderado.getRut()));
    }

}
