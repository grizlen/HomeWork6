package work6.domein;

import lombok.extern.slf4j.Slf4j;
import work6.db.DataMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
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
            return Collections.emptyList();
        }
        return dbTable.select().execute(listRecordConverter);
    }

    @Override
    public Optional<Product> findById(Long id) {
        if (dbTable == null) {
            return Optional.empty();
        }
        List<Product> list = dbTable.select()
                .whereId(id)
                .execute(listRecordConverter);
        if (list.size() == 1) {
            return Optional.ofNullable(list.get(0));
        }
        log.error("find by id returns not a single value.");
        return Optional.empty();
    }

    @Override
    public Product insert(Product entity) {
        if (dbTable == null) {
            return null;
        }
        String sql = "INSERT INTO " + dbTable.tableName()
                + " (title)"
                + " VALUES "
                + "('" + entity.getTitle() + "')"
                + ";";
        Long id = dbTable.insert(sql);
        if (id != null && id != -1) {
            entity.setId(id);
            return entity;
        }
        log.error("insert returns null.");
        return null;
    }

    @Override
    public void update(Product entity) {
        if (dbTable == null
                || entity == null
                || entity.getId() == null) {
            return;
        }
        String sql = "UPDATE " + dbTable.tableName()
                + " SET title = '" + entity.getTitle() + "'"
                + " WHERE id = " + entity.getId()
                + ";";
        dbTable.execute(sql);
    }

    @Override
    public void delete(Long id) {
        if (dbTable == null || id == null) {
            return;
        }
        String sql = "DELETE FROM " + dbTable.tableName()
                + " WHERE id = " + id + ";";
        dbTable.execute(sql);
    }
}
