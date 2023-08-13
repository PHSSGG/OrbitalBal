package phss.orbitalbal.database.functions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLFunction<T> {
    T apply(PreparedStatement statement) throws SQLException;
}
