package aplicacion.models.dao;

import aplicacion.models.Persona;
import utils.models.Criteria;

import java.util.HashMap;
import java.util.Optional;

public interface PersonaDAO<T extends Persona> extends DAO<T, String, HashMap<String, T>> {

    @Override
    T get(String rut);

    @Override
    Optional<HashMap<String, T>> getAll();

    @Override
    Optional<HashMap<String, T>> getAll(Criteria[] params);

    @Override
    void insert(T obj);

    @Override
    void update(T obj, HashMap<String, String> params);

    @Override
    void delete(T obj);
}
