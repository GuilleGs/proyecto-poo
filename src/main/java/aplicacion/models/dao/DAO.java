package aplicacion.models.dao;

import utils.models.Criteria;

import java.util.HashMap;
import java.util.Optional;

public interface DAO<T, IDT, CT> {

    T get(IDT id);
    Optional<CT> getAll();
    Optional<CT> getAll(Criteria[] params);
    void insert(T obj);
    void update(T obj, HashMap<String, String> params);
    void delete(T obj);

}
