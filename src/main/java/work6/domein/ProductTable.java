package work6.domein;

import work6.db.DataMapper;
import work6.db.DataSource;
import work6.db.DbTable;

public class ProductTable extends DbTable<Product> {

    public ProductTable(DataSource dataSource, DataMapper<Product> dataMapper) {
        super(dataSource, dataMapper, Product.class);
    }

    @Override
    public String tableName() {
        return "products";
    }

    @Override
    public void create()  {
        dataSource.execute("CREATE TABLE " + tableName() + " ("
                + "id IDENTITY PRIMARY KEY, "
                + "title VARCHAR NOT NULL"
                + ");");
    }

    @Override
    public void init() {
        dataSource.execute("INSERT INTO " + tableName()
                + " (title)"
                + " VALUES "
                + "('product 1'), ('product 2'), ('product 3')"
                + ";");
    }
}
