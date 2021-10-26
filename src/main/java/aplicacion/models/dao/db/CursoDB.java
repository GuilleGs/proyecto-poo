package aplicacion.models.dao.db;

import aplicacion.models.Alumno;
import aplicacion.models.Curso;
import aplicacion.models.Profesor;
import aplicacion.models.dao.PersonaDAO;
import aplicacion.models.id.IDCurso;
import aplicacion.models.dao.DAO;
import utils.models.Criteria;
import utils.models.db.MySQLConnection;
import utils.models.db.SQLSentences;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class CursoDB implements DAO<Curso, IDCurso, ArrayList<Curso>> {

    private final PersonaDAO<Profesor> profesorData = new ProfesorDB();
    private final PersonaDAO<Alumno> alumnoData = new AlumnoDB();

    @Override
    public Curso get(IDCurso id) {
        if (MySQLConnection.isConnected()) {
            Curso curso;
            try {
                ResultSet resultados = MySQLConnection.getQuery(String.format(SQLSentences.,
                        id.nivel, id.paralelo));
                if (resultados == null) return null;
                if (resultados.next()) {
                    curso = new Curso(resultados.getShort("nivel"), resultados.getString("paralelo").charAt(0),
                            profesorData.get(resultados.getString("rut_profesor")));
                    curso.setAlumnos(alumnoData.getAll().get());
                    return curso;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }

    @Override
    public Optional<ArrayList<Curso>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<ArrayList<Curso>> getAll(Criteria[] params) {
        return Optional.empty();
    }

    @Override
    public void insert(Curso obj) {

    }

    @Override
    public void update(Curso obj, HashMap<String, String> params) {

    }

    @Override
    public void delete(Curso obj) {

    }
}
