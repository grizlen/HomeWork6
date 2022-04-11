package work6.domein;

import work6.db.DataMapper;

import java.util.Collections;
import java.util.List;

public class ProductMapper extends DataMapper<Product> {

    @Override
    protected DataMapper.RecordConverter<Product> createRecordConverter() {
        return (rs) -> {
            Product product = new Product();
            product.setId(rs.getLong(rs.findColumn("id")));
            product.setTitle(rs.getString(rs.findColumn("title")));
            return product;
        };
    }

    @Override
    public List<Product> findAll() {
        if (dbTable == null) {
            Collections.emptyList();
        }
        return dbTable.select().execute(listRecordConverter);
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
