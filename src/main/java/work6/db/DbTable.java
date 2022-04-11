package work6.db;

import java.util.ArrayList;
import java.util.List;

public abstract class DbTable<T extends Entity> {

    protected final DataMapper<T> dataMapper;
    protected final Class<T> entityClass;
    protected final DataSource dataSource;

    public DbTable(DataSource dataSource, DataMapper<T> dataMapper, Class<T> entityClass) {
        this.dataSource = dataSource;
        this.dataMapper = dataMapper;
        this.entityClass = entityClass;
        this.dataMapper.setDbTable(this);
    }

    public DataMapper<T> getDataMapper() {
        return dataMapper;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public abstract String tableName();

    public void drop() {
        dataSource.execute("DROP TABLE " + tableName() + " IF EXISTS;");
    }

    public abstract void create();

    public abstract void init();

    public SelectQuery select() {
        return new SelectQuery(this);
    }

    public static class SelectQuery {
        private final String table;

        public SelectQuery(DbTable DbTable) {
            table = DbTable.tableName();
        }

        public <T extends Entity> List<T> execute() {
            return new ArrayList<>();
        }
    }
}
