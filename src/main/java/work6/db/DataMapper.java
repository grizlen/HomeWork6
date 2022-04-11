package work6.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class DataMapper<T extends Entity> {

    protected DbTable dbTable;
    protected RecordConverter<T> recordConverter;
    protected RecordConverter<List<T>> listRecordConverter;

    public DataMapper() {
        recordConverter = createRecordConverter();
        listRecordConverter = (rs) -> {
            try {
                ArrayList<T> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(recordConverter.convert(rs));
                }
                return list;
            } catch (SQLException e) {
                log.error("convert db record", e);
                return Collections.emptyList();
            }
        };
    }

    protected abstract RecordConverter<T> createRecordConverter();

    public void setDbTable(DbTable dbTable) {
        this.dbTable = dbTable;
    }

    public abstract List<T> findAll();
    public abstract T findById(Long id);
    public abstract void insert(T entity);
    public abstract void update(T entity);
    public abstract void delete(Long id);


    public interface RecordConverter<T> {
        T convert(ResultSet resultSet) throws SQLException;
    }
}
