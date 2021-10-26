package aplicacion.models.dao.db;

import aplicacion.models.Alumno;
import aplicacion.models.Apoderado;
import aplicacion.models.dao.PersonaDAO;
import utils.models.Criteria;
import utils.models.db.MySQLConnection;
import utils.models.db.SQLSentences;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Optional;

import static utils.models.db.SQLSentences.getCriteria;

public class AlumnoDB implements PersonaDAO<Alumno> {

    private final PersonaDAO<Apoderado> apoderadoData = new ApoderadoDB();

    @Override
    public Alumno get(String rut) {
        Criteria[] criteria = new Criteria[1];
        criteria[0] = new Criteria("rut", rut, "=", true);
        if (MySQLConnection.isConnected())
            try {
                ResultSet resultados = MySQLConnection.getQuery(SQLSentences.select("Alumnos", criteria));
                if (resultados == null) return null;
                if (resultados.next()) {
                    return new Alumno(
                            resultados.getString("rut"),
                            resultados.getString("nombres"),
                            resultados.getString("apellido_paterno"),
                            resultados.getString("apellido_materno"),
                            resultados.getInt("nivel"),
                            resultados.getString("paralelo").charAt(0),
                            apoderadoData.get(resultados.getString("rut_apoderado"))
                    );
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        return null;
    }

    @Override
    public Optional<HashMap<String, Alumno>> getAll() {
        return getAll(null);
    }

    @Override
    public Optional<HashMap<String, Alumno>> getAll(Criteria[] params) {
        if (MySQLConnection.isConnected()) {
            HashMap<String, Alumno> alumnos = new HashMap<>();
            try {
                ResultSet resultados = MySQLConnection.getQuery(SQLSentences.select("Alumnos", params));
                if (resultados == null) return Optional.empty();
                while (resultados.next()) {
                    alumnos.put(resultados.getString("rut"), new Alumno(
                            resultados.getString("rut"),
                            resultados.getString("nombres"),
                            resultados.getString("apellido_paterno"),
                            resultados.getString("apellido_materno"),
                            resultados.getInt("nivel"),
                            resultados.getString("paralelo").charAt(0),
                            apoderadoData.get(resultados.getString("rut_apoderado"))
                    ));
                }
                return Optional.of(alumnos);
            } catch (Exception e) {
                System.out.println(e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public void insert(Alumno alumno) {
        String[] criteriaKeys = SQLSentences<Alumno>.getCriteria("Alumnos");
        Criteria[] criteria = new Criteria[criteriaKeys.length];

        criteria[0].

        MySQLConnection.updateQuery(String.format(SQLSentences.INSERT_ALUMNO.toString(),
                alumno.getNivel(),
                alumno.getParalelo(),
                alumno.getRut(),
                alumno.getNombres(),
                alumno.getApPaterno(),
                alumno.getApMaterno(),
                alumno.getApoderado().getRut()));
    }

    @Override
    public void update(Alumno alumno, HashMap<String, String> params) {
        MySQLConnection.updateQuery(String.format(SQLSentences.UPDATE_ALUMNO.toString(),
                alumno.getNivel(),
                alumno.getParalelo(),
                alumno.getRut(),
                alumno.getNombres(),
                alumno.getApPaterno(),
                alumno.getApMaterno(),
                alumno.getApoderado().getRut(),
                alumno.getRut()));
    }

    @Override
    public void delete(Alumno alumno) {
        MySQLConnection.updateQuery(String.format(SQLSentences.DELETE_ALUMNO.toString(), alumno.getRut()));
    }
}
