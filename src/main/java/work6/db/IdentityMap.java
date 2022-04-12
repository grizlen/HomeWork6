package work6.db;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class IdentityMap<T extends Entity> {
    private DataMapper<T> mapper;
    private final HashMap<Long, T> map = new HashMap<>();

    public void setMapper(DataMapper<T> mapper) {
        this.mapper = mapper;
    }

    public List<T> findAll() {
        List<T> result = mapper.findAll();
        result.forEach(entity -> map.put(entity.getId(), entity));
        return result;
    }

    public Optional<T> findById(long id) {
        T result = map.get(id);
        if (result != null) {
            return Optional.of(result);
        }
        Optional<T> optional = mapper.findById(id);
        optional.ifPresent(entity -> map.put(entity.getId(), entity));
        return optional;
    }

    public T save(T entity) {
        if (entity == null) {
            return null;
        }
        T result = entity;
        if (entity.getId() == null) {
            result = mapper.insert(entity);
        } else {
            mapper.update(entity);
        }
        map.put(result.getId(), result);
        return result;
    }

    public void delete(long id) {
        mapper.delete(id);
        map.remove(id);
    }
}
