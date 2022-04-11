package work6.db;

import lombok.extern.slf4j.Slf4j;
import work6.domein.Product;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class DataSource {
    static {
        try {
            Class.forName("org.h2.Driver");
            log.info("Init org.h2.Driver");
        } catch (Exception e) {
            log.error("Init: ", e);
            System.exit(0);
        }
    }
    private static final String URL_PREFIX = "jdbc:h2:file:./";

    private final String url;
    private final String user;
    private final String password;
    private final HashMap<String, DbTable> tables;

    public DataSource(String url, String user, String password) {
        this.url = URL_PREFIX + url;
        this.user = user;
        this.password = password;
        tables = new HashMap<String, DbTable>();
        log.info("Created: " + this.url);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void execute(String sql) {
        log.info("execute: {}", sql);
        try (Connection connection = getConnection()) {
            executeStatement(connection, sql);
        } catch (SQLException e) {
            log.error("execute", e);
        }
    }

    private void executeStatement(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("execute statement", e);
            throw new SQLException(e);
        }
    }

    public <T extends Entity> List<T> select(String sql, DataMapper.RecordConverter<List<T>> converter) {
        log.info("execute: {}", sql);
        try (Connection connection = getConnection()) {
            return executeSelectStatement(connection, sql, converter);
        } catch (SQLException e) {
            log.error("execute select", e);
            return Collections.emptyList();
        }
    }

    private <T extends Entity> List<T> executeSelectStatement(Connection connection, String sql, DataMapper.RecordConverter<List<T>> converter) {
        try (Statement statement = connection.createStatement()) {
            return converter.convert(statement.executeQuery(sql));
        } catch (SQLException e) {
            log.error("execute select statement", e);
            return Collections.emptyList();
        }
    }

    public Long executeInsert(String sql) {
        log.info("execute: {}", sql);
        try (Connection connection = getConnection()) {
            return executeInsertStatement(connection, sql);
        } catch (SQLException e) {
            log.error("execute select", e);
            return -1L;
        }
    }

    private Long executeInsertStatement(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, new String[] {"id"});
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            log.error("execute select statement", e);
        }
        return -1L;
    }

    public void addTable(DbTable table) {
        tables.put(table.tableName(), table);
        log.info("append table: '{}' for entity: {}", table.tableName(), table.getEntityClass().getSimpleName());
    }

    public void clear() {
        tables.values().forEach(table -> table.drop());
    }

    public void init() {
        tables.values().forEach(table -> table.create());
        tables.values().forEach(table -> table.init());
    }

    public DataMapper getMapper(Class entityClass) {
        return tables.values().stream()
                .filter(table -> table.getEntityClass() == entityClass)
                .map(table -> table.getDataMapper())
                .findAny().orElse(null);
    }
}
