package work6.db;

public class DbTable<T extends Entity> {
    private final DataMapper<T> dataMapper;

    public DbTable(DataMapper<T> dataMapper) {
        this.dataMapper = dataMapper;
    }
}
