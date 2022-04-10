package work6.db;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

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

    public void addTable(DbTable table) {
        tables.put(table.tableName(), table);
    }
}
