package work6;

import work6.db.DataSource;
import work6.domein.Product;
import work6.domein.ProductMapper;
import work6.domein.ProductTable;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new DataSource("db/work6", "sa", "");
        dataSource.addTable(new ProductTable(dataSource, new ProductMapper()));
        dataSource.clear();
        dataSource.init();

        ProductMapper productMapper = (ProductMapper) dataSource.getMapper(Product.class);
        if (productMapper != null) {
            productMapper.findAll().forEach(System.out::println);
        }
    }
}
