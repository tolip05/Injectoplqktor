package panda.repository;

import java.util.List;

public interface GenericRepository<T,ID> {
    T save(T entity);

    List<T> findAll();

    T findById (ID id);


    Long size();
}
