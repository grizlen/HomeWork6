package work6.db;

import java.util.List;

public abstract class DataMapper<T extends Entity> {
    public abstract List<T> findAll();
    public abstract T findById(Long id);
    public abstract void insert(T entity);
    public abstract void update(T entity);
    public abstract void delete(Long id);
}
