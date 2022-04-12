package work6;

import work6.db.DataSource;
import work6.db.IdentityMap;
import work6.domein.Product;
import work6.domein.ProductMapper;
import work6.domein.ProductTable;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new DataSource("db/work6", "sa", "");
        dataSource.addTable(new ProductTable(dataSource, new ProductMapper(), new IdentityMap<>()));
        dataSource.clear();
        dataSource.init();

        IdentityMap<Product> identityMap = dataSource.getIdentityMap(Product.class);
        if (identityMap != null) {
            identityMap.findAll().forEach(System.out::println);
            Product product = new Product();
            product.setTitle("product 4");
            System.out.println(identityMap.save(product));
            product.setTitle("New product");
            identityMap.save(product);
            System.out.println(identityMap.findById(4L));
            identityMap.delete(2L);
            identityMap.findAll().forEach(System.out::println);
        }
    }
}
