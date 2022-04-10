package work6.domein;

import work6.db.DataMapper;
import work6.db.DbTable;

public class ProductTable extends DbTable<Product> {
    public ProductTable(DataMapper<Product> dataMapper) {
        super(dataMapper);
    }

    @Override
    public String tableName() {
        return "products";
    }
}
