package database.repositories;

import resource.DBNode;
import resource.data.Row;

import java.util.List;

public interface Repository {

    DBNode getSchema();

    List<Row> get(String from);
}
