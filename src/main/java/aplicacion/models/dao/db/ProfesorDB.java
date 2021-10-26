package aplicacion.models.dao.db;

import aplicacion.models.Profesor;
import aplicacion.models.dao.PersonaDAO;
import utils.models.Criteria;

import java.util.HashMap;
import java.util.Optional;

public class ProfesorDB implements PersonaDAO<Profesor> {

    @Override
    public Profesor get(String rut) {
        return null;
    }

    @Override
    public Optional<HashMap<String, Profesor>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<HashMap<String, Profesor>> getAll(Criteria[] params) {
        return Optional.empty();
    }

    @Override
    public void insert(Profesor obj) {

    }

    @Override
    public void update(Profesor obj, HashMap<String, String> params) {

    }

    @Override
    public void delete(Profesor obj) {

    }
}
