package work6.domein;

import lombok.Getter;
import lombok.Setter;
import work6.db.Entity;

@Getter
@Setter
public class Product extends Entity {
    private String title;
}
