package com.eror.server.generic;

import java.util.List;

public interface GenericService<T, V extends Object> {

    V save(T entity);

    V update(T entity);

    void delete(T entity);

    void delete(Long id);

    List<V> deleteInBatch(List<V> entities);

    V find(Long id);

    List<V> findAll();
}
