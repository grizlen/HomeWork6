package work6.domein;

import work6.db.DataMapper;
import work6.db.DataSource;
import work6.db.DbTable;
import work6.db.IdentityMap;

public class ProductTable extends DbTable<Product> {

    public ProductTable(DataSource dataSource, DataMapper<Product> dataMapper, IdentityMap<Product> identityMap) {
        super(dataSource, dataMapper, identityMap, Product.class);
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
