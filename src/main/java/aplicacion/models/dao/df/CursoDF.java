package aplicacion.models.dao.df;

import aplicacion.models.Curso;
import aplicacion.models.id.IDCurso;
import aplicacion.models.dao.DAO;
import utils.models.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class CursoDF implements DAO<Curso, IDCurso, ArrayList<Curso>> {

    @Override
    public Curso get(IDCurso id) {
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
