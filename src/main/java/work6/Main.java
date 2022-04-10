package work6;

import work6.db.DataSource;
import work6.db.DbTable;
import work6.domein.ProductMapper;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new DataSource("db/work6", "sa", "");
        dataSource.addTable(new DbTable());
        ProductMapper productMapper = new ProductMapper();
    }
}
