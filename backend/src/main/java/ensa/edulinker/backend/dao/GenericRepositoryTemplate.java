package ensa.edulinker.backend.dao;

import java.util.List;

public interface GenericRepositoryTemplate<T, ID> {

    public T save(T e);
    public T update(T e);
    public List<T> findAll();
    public T getById(ID id);
    public void delete(ID id);
}
