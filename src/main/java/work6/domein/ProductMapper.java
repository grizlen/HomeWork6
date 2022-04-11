package work6.domein;

import work6.db.DataMapper;

import java.util.Collections;
import java.util.List;

public class ProductMapper extends DataMapper<Product> {
    @Override
    public List<Product> findAll() {
        if (dbTable == null) {
            Collections.emptyList();
        }
        return dbTable.select().execute();
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public void insert(Product entity) {

    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
