package work6.db;

import work6.domein.Product;

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

    public Long insert(String sql) {
        return dataSource.executeInsert(sql);
    }

    public void execute(String sql) {
        dataSource.execute(sql);
    }

    public static class SelectQuery {
        private DbTable dbTable;
        private final String table;
        private String fields = "*";
        private String where = "";

        public SelectQuery(DbTable DbTable) {
            table = DbTable.tableName();
            dbTable = DbTable;
        }

        private String build() {
            StringBuilder sb = new StringBuilder("SELECT ")
                    .append(fields)
                    .append(" FROM ").append(table);
            if (!where.isBlank()) {
                sb.append(" WHERE ").append(where);
            }
            sb.append(";");
            return sb.toString();
        }

        public SelectQuery whereId(Long id) {
            where = "id = " + id;
            return this;
        }

        public <T extends Entity> List<T> execute(DataMapper.RecordConverter<List<T>> converter) {
            return dbTable.dataSource.select(build(), converter);
        }
    }
}
