package com.example.orm_ttps.clases_dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T> {
    public T update(T entity);
    public void delete(T entity);
    public T delete(Long id);
    public boolean exists(Long id);
    public T save(T entity);
    public T get(Serializable id);
    public List<T> getAll(String column);
}
