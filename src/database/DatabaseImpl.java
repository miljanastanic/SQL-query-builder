package database;

import database.repositories.Repository;
import resource.DBNode;
import resource.data.Row;

import java.util.List;

public class DatabaseImpl implements Database{

    private Repository repository;

    public DatabaseImpl(Repository repository) {
        this.repository = repository;
    }


    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }
}
